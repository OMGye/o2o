package com.service.impl;

import com.common.Const;
import com.common.ServerResponse;
import com.dao.BillInfoMapper;
import com.dao.EngineerInfoMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.pojo.*;
import com.service.EngineerInfoService;
import com.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Timer;
import java.util.UUID;

/**
 * Created by upupgogogo on 2018/11/14.下午4:38
 */
@Service("engineerInfoService")
public class EngineerInfoServiceImpl implements EngineerInfoService {

    private Logger logger = LoggerFactory.getLogger(EngineerInfoServiceImpl.class);

    @Autowired
    private EngineerInfoMapper engineerInfoMapper;


    @Override
    public synchronized ServerResponse<EngineerInfo> register(EngineerInfo engineerInfo, MultipartFile file, String path) {
        if (file == null)
            return ServerResponse.createByErrorMessage("必须上传附件");

        if (engineerInfo.getEngineerName() == null|| engineerInfo.getPassword() == null || engineerInfo.getPhone() == null || engineerInfo.getEmail() == null || engineerInfo.getPersonCode() == null)
            return ServerResponse.createByErrorMessage("参数不能为空");

        EngineerInfo dbEngineerInfo = engineerInfoMapper.selectByUserName(engineerInfo.getEngineerName(),null,null, null, null);
        if (dbEngineerInfo != null)
            return ServerResponse.createByErrorMessage("该用户名已存在");

        EngineerInfo dbEngineerInfoByEmail = engineerInfoMapper.selectByUserName(null,null,engineerInfo.getEmail(), null, null);
        if (dbEngineerInfoByEmail != null)
            return ServerResponse.createByErrorMessage("该邮箱已存在");

        EngineerInfo dbEngineerInfoByPhone = engineerInfoMapper.selectByUserName(null,null,null, engineerInfo.getPhone(), null);
        if (dbEngineerInfoByPhone != null)
            return ServerResponse.createByErrorMessage("该电话已存在");

        EngineerInfo dbEngineerInfoByPersonCode = engineerInfoMapper.selectByUserName(null,null,null, null, engineerInfo.getPersonCode());
        if (dbEngineerInfoByPersonCode != null)
            return ServerResponse.createByErrorMessage("该身份证已存在");

        engineerInfo.setEngineerBalance(new BigDecimal(0));
        engineerInfo.setEngineerQuantity(new BigDecimal(1));
        engineerInfo.setEngineerState(Const.EngineerInfo.UNABLE);

        if (file != null) {
            String fileName = file.getOriginalFilename();
            //扩展名
            //abc.jpg
            String fileExtensionName = fileName.substring(fileName.lastIndexOf(".") + 1);
            String uploadFileName = UUID.randomUUID().toString() + "." + fileExtensionName;
            logger.info("开始上传文件,上传文件的文件名:{},上传的路径:{},新文件名:{}", fileName, path, uploadFileName);

            File fileDir = new File(path);
            if (!fileDir.exists()) {
                fileDir.setWritable(true);
                fileDir.mkdirs();
            }
            File targetFile = new File(path, uploadFileName);


            try {
                file.transferTo(targetFile);
                //文件已经上传成功了

                FTPUtil.uploadFile(Lists.newArrayList(targetFile));
                //已经上传到ftp服务器上

                targetFile.delete();
            } catch (IOException e) {
                logger.error("上传文件异常", e);
                return ServerResponse.createByErrorMessage("上传文件异常");
            }
            engineerInfo.setEngineerFile(PropertiesUtil.getProperty("ftp.server.http.prefix") + targetFile.getName());
        }

        engineerInfo.setOrderCount(0);
        engineerInfo.setAdminCheck(Const.AdminCheck.CHECK);
        int row = engineerInfoMapper.insert(engineerInfo);
        if (row > 0)
            return ServerResponse.createBySuccess("注册成功，请您等待后台审核通过",engineerInfo);
        return ServerResponse.createByErrorMessage("注册失败");
    }

    @Override
    public ServerResponse<PageInfo> list(int pageSize, int pageNum, Integer state) {
        PageHelper.startPage(pageNum,pageSize);
        PageHelper.orderBy("engineer_id desc");
        List<EngineerInfo> list = engineerInfoMapper.list(state);
        PageInfo pageInfo = new PageInfo(list);
        return ServerResponse.createBySuccess(pageInfo);
    }

    @Override
    public ServerResponse<EngineerInfo> getEngineerInfoById(Integer engineerId) {
        if (engineerId == null)
            return ServerResponse.createByErrorMessage("参数不能为空");
        EngineerInfo engineerInfo = engineerInfoMapper.selectByPrimaryKey(engineerId);
        if (engineerInfo == null)
            return ServerResponse.createByErrorMessage("该工程师不存在");

        return ServerResponse.createBySuccess(engineerInfo);
    }

    @Override
    public ServerResponse check(EngineerInfo engineerInfo) {
        if (engineerInfo == null || engineerInfo.getEngineerId() == null)
            return ServerResponse.createByErrorMessage("参数不能为空");
        if (engineerInfo.getEngineerState() != null && engineerInfo.getEngineerState() == Const.EngineerInfo.ABLE) {
            EngineerInfo dbEngineerInfo = engineerInfoMapper.selectByPrimaryKey(engineerInfo.getEngineerId());
            Timer timer = new Timer();
            TimerEmailCaughtOrder caughtOrder = new TimerEmailCaughtOrder(dbEngineerInfo.getEmail(), "您的账户已被激活");
            timer.schedule(caughtOrder, Const.TIMER_FOR_SEND_EMAIL);

        }
        engineerInfo.setOrderCount(null);
        engineerInfo.setEngineerBalance(null);
        int row = engineerInfoMapper.updateByPrimaryKeySelective(engineerInfo);
        if (row > 0)
            return ServerResponse.createBySuccess("审核修改成功");
        return ServerResponse.createByErrorMessage("审核修改失败");
    }

    @Override
    public ServerResponse<EngineerInfo> login(String engineerName, String password) {
        if (engineerName == null || password == null)
            return ServerResponse.createByErrorMessage("参数不能为空");
        EngineerInfo dbEngineerInfo = engineerInfoMapper.login(engineerName,password);
        if (dbEngineerInfo == null){
            dbEngineerInfo = engineerInfoMapper.loginByPhone(engineerName, password);
            if (dbEngineerInfo == null)
                return ServerResponse.createByErrorMessage("您的用户名或者密码错误");
        }
        if (dbEngineerInfo != null){
            if (dbEngineerInfo.getEngineerState() == Const.EngineerInfo.UNABLE)
                return ServerResponse.createByErrorMessage("您的账号正处于审核中");
            if (dbEngineerInfo.getEngineerState() == Const.EngineerInfo.BAN)
                return ServerResponse.createByErrorMessage("您的账号已被停止使用");
        }

        return ServerResponse.createBySuccess(dbEngineerInfo);
    }

    @Override
    public ServerResponse update(EngineerInfo engineerInfo) {
        if (engineerInfo == null || engineerInfo.getEngineerId() == null)
            return ServerResponse.createByErrorMessage("参数不能为空");
        EngineerInfo newEngineerInfo = new EngineerInfo();
        newEngineerInfo.setEngineerName(engineerInfo.getEngineerName());
        newEngineerInfo.setPassword(engineerInfo.getPassword());
        newEngineerInfo.setEmail(engineerInfo.getEmail());
        newEngineerInfo.setEngineerPayCount(engineerInfo.getEngineerPayCount());
        newEngineerInfo.setEngineerQq(engineerInfo.getEngineerQq());
        newEngineerInfo.setEngineerCity(engineerInfo.getEngineerCity());
        newEngineerInfo.setEngineerProv(engineerInfo.getEngineerProv());
        newEngineerInfo.setEngineerId(engineerInfo.getEngineerId());

        if(engineerInfo.getEngineerName() != null){
        EngineerInfo dbEngineerInfo = engineerInfoMapper.selectByUserName(engineerInfo.getEngineerName(),engineerInfo.getEngineerId(),null, null, null);
        if (dbEngineerInfo != null)
            return ServerResponse.createByErrorMessage("该用户名已存在");
        }

        if(engineerInfo.getEmail() != null) {
            EngineerInfo dbEngineerInfoByEmail = engineerInfoMapper.selectByUserName(null, engineerInfo.getEngineerId(), engineerInfo.getEmail(), null, null);
            if (dbEngineerInfoByEmail != null)
                return ServerResponse.createByErrorMessage("该邮箱已存在");
        }

        int row = engineerInfoMapper.updateByPrimaryKeySelective(newEngineerInfo);
        if (row > 0)
            return ServerResponse.createBySuccess("修改成功");
        return ServerResponse.createByErrorMessage("修改失败");
    }

    @Override
    public ServerResponse getPassword(EngineerInfo engineerInfo) {
        if (engineerInfo == null || engineerInfo.getEngineerName() == null || engineerInfo.getEmail() == null)
            return ServerResponse.createByErrorMessage("参数不能为空");
        EngineerInfo dbEngineerInfo = engineerInfoMapper.selectByUserName(engineerInfo.getEngineerName(),null,engineerInfo.getEmail(), null, null);
        if (dbEngineerInfo  == null)
            return ServerResponse.createByErrorMessage("您的用户名和邮箱不匹配");

        try {
            MailUtil.sendMail(dbEngineerInfo.getEmail(),dbEngineerInfo.getPassword());
        }catch (Exception e){
            return ServerResponse.createByErrorMessage("邮件发送失败"+ e);
        }

        return ServerResponse.createBySuccess("密码已发送至您的邮箱");
    }

    @Override
    public ServerResponse comfirmUserNameAndEmail(EngineerInfo engineerInfo) {
        if (engineerInfo == null && engineerInfo.getEngineerName() == null && engineerInfo.getEmail() == null && engineerInfo.getPhone() == null && engineerInfo.getPersonCode() == null)
            return ServerResponse.createByErrorMessage("参数不能为空");
        if(engineerInfo.getEngineerName() != null){
            EngineerInfo dbEngineerInfo = engineerInfoMapper.selectByUserName(engineerInfo.getEngineerName(),null,null, null, null);
            if (dbEngineerInfo != null)
                return ServerResponse.createByErrorMessage("该用户名已存在");
        }

        if(engineerInfo.getEmail() != null){
            EngineerInfo dbEngineerInfo = engineerInfoMapper.selectByUserName(null,null,engineerInfo.getEmail(), null, null);
            if (dbEngineerInfo != null)
                return ServerResponse.createByErrorMessage("该邮箱已存在");
        }

        if(engineerInfo.getPhone() != null){
            EngineerInfo dbEngineerInfo = engineerInfoMapper.selectByUserName(null,null,null, engineerInfo.getPhone(), null);
            if (dbEngineerInfo != null)
                return ServerResponse.createByErrorMessage("该电话已存在");
        }

        if(engineerInfo.getPersonCode() != null){
            EngineerInfo dbEngineerInfo = engineerInfoMapper.selectByUserName(null,null,null, null, engineerInfo.getPersonCode());
            if (dbEngineerInfo != null)
                return ServerResponse.createByErrorMessage("该身份证已存在");
        }
        return ServerResponse.createBySuccess("验证成功");
    }

    @Override
    public ServerResponse<PageInfo> selectByIdLike(int pageNum, int pageSize, Integer engineerId) {
        if (engineerId == null)
            return ServerResponse.createByErrorMessage("参数为空");
        String engineerIdString = "" + engineerId + "%";
        PageHelper.startPage(pageNum,pageSize);
        PageHelper.orderBy("create_time asc");
        List<EngineerInfo> list = engineerInfoMapper.selectByIdLike(engineerIdString);
        PageInfo pageInfo = new PageInfo(list);
        return ServerResponse.createBySuccess(pageInfo);
    }

    @Autowired
    private BillInfoMapper billInfoMapper;

    @Override
    public ServerResponse deductOrAddMoney(Integer type, BigDecimal price, Integer engineerId) {
        if (type == null || price == null || engineerId == null)
            return ServerResponse.createByErrorMessage("参数为空");

        EngineerInfo engineerInfo = engineerInfoMapper.selectByPrimaryKey(engineerId);
        if (engineerInfo == null)
            return ServerResponse.createByErrorMessage("找不到该用户");

        if (type == 0){
            if (engineerInfo.getEngineerBalance().doubleValue() < price.doubleValue())
                return ServerResponse.createByErrorMessage("扣钱金额不能大于工程师余额");

            engineerInfo.setEngineerBalance(BigDecimalUtil.sub(engineerInfo.getEngineerBalance().doubleValue(), price.doubleValue()));
            int row = engineerInfoMapper.updateByPrimaryKeySelective(engineerInfo);
            if (row > 0){
                BillInfo billInfo = new BillInfo();
                billInfo.setUserType(1);
                billInfo.setBillMoney(BigDecimalUtil.sub(price.doubleValue(), BigDecimalUtil.add(price.doubleValue(), price.doubleValue()).doubleValue()));
                billInfo.setUserName(engineerInfo.getEngineerName());
                billInfo.setUserId(engineerId);
                billInfo.setBillDec("后台扣款");
                billInfoMapper.insert(billInfo);
                return ServerResponse.createBySuccess("扣钱成功");
            }
            return ServerResponse.createByErrorMessage("扣钱失败");
        }

        if (type == 1){
            engineerInfo.setEngineerBalance(BigDecimalUtil.add(engineerInfo.getEngineerBalance().doubleValue(), price.doubleValue()));
            int row = engineerInfoMapper.updateByPrimaryKeySelective(engineerInfo);
            if (row > 0){
                BillInfo billInfo = new BillInfo();
                billInfo.setUserType(1);
                billInfo.setBillMoney(price);
                billInfo.setUserName(engineerInfo.getEngineerName());
                billInfo.setUserId(engineerId);
                billInfo.setBillDec("后台加钱" );
                billInfoMapper.insert(billInfo);
                return ServerResponse.createBySuccess("加钱成功");
            }
            return ServerResponse.createByErrorMessage("加钱失败");
        }
        return ServerResponse.createByErrorMessage("操作异常");
    }

    @Override
    public ServerResponse adminCheck(Integer adminCheck, Integer engineerId) {
        if (adminCheck == null || engineerId == null)
            return ServerResponse.createByErrorMessage("参数为空");
        if (adminCheck != Const.AdminCheck.UNCHECK && adminCheck != Const.AdminCheck.CHECK)
            return ServerResponse.createByErrorMessage("参数异常");

        EngineerInfo engineerInfo = engineerInfoMapper.selectByPrimaryKey(engineerId);
        if (engineerInfo == null)
            return ServerResponse.createByErrorMessage("找不到该用户");

        engineerInfo.setAdminCheck(adminCheck);
        int row = engineerInfoMapper.updateByPrimaryKeySelective(engineerInfo);
        if (row > 0)
            return ServerResponse.createBySuccess("更新成功");
        return ServerResponse.createByErrorMessage("更新失败");
    }

}
