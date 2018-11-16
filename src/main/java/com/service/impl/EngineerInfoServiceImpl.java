package com.service.impl;

import com.common.Const;
import com.common.ServerResponse;
import com.dao.EngineerInfoMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.pojo.EngineerInfo;
import com.pojo.QuantityInfo;
import com.service.EngineerInfoService;
import com.util.FTPUtil;
import com.util.MailUtil;
import com.util.PropertiesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
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
    public ServerResponse<EngineerInfo> register(EngineerInfo engineerInfo, MultipartFile file, String path) {
        if (file == null)
            return ServerResponse.createByErrorMessage("必须上传附件");

        if (engineerInfo.getEngineerName() == null|| engineerInfo.getPassword() == null || engineerInfo.getPhone() == null || engineerInfo.getEmail() == null)
            return ServerResponse.createByErrorMessage("参数不能为空");

        EngineerInfo dbEngineerInfo = engineerInfoMapper.selectByUserName(engineerInfo.getEngineerName(),null,null);
        if (dbEngineerInfo != null)
            return ServerResponse.createByErrorMessage("该用户名已存在");

        EngineerInfo dbEngineerInfoByEmail = engineerInfoMapper.selectByUserName(null,null,engineerInfo.getEmail());
        if (dbEngineerInfoByEmail != null)
            return ServerResponse.createByErrorMessage("该邮箱已存在");

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
        EngineerInfo newEngineerInfo = new EngineerInfo();
        newEngineerInfo.setEngineerId(engineerInfo.getEngineerId());
        newEngineerInfo.setEngineerState(engineerInfo.getEngineerState());
        newEngineerInfo.setEngineerClassfy(engineerInfo.getEngineerClassfy());
        newEngineerInfo.setEngineerRank(engineerInfo.getEngineerRank());
        newEngineerInfo.setPassword(engineerInfo.getPassword());
        int row = engineerInfoMapper.updateByPrimaryKeySelective(newEngineerInfo);
        if (row > 0)
            return ServerResponse.createBySuccess("审核修改成功");
        return ServerResponse.createByErrorMessage("审核修改失败");
    }

    @Override
    public ServerResponse<EngineerInfo> login(EngineerInfo engineerInfo) {
        if (engineerInfo == null || engineerInfo.getEngineerName() == null || engineerInfo.getPassword() == null)
            return ServerResponse.createByErrorMessage("参数不能为空");
        EngineerInfo dbEngineerInfo = engineerInfoMapper.login(engineerInfo.getEngineerName(),engineerInfo.getPassword());
        if (dbEngineerInfo == null)
            return ServerResponse.createByErrorMessage("您的用户名或者密码错误");
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
        newEngineerInfo.setPhone(engineerInfo.getPhone());
        newEngineerInfo.setEngineerCity(engineerInfo.getEngineerCity());
        newEngineerInfo.setEngineerProv(engineerInfo.getEngineerProv());
        newEngineerInfo.setEngineerId(engineerInfo.getEngineerId());

        if(engineerInfo.getEngineerName() != null){
        EngineerInfo dbEngineerInfo = engineerInfoMapper.selectByUserName(engineerInfo.getEngineerName(),engineerInfo.getEngineerId(),null);
        if (dbEngineerInfo != null)
            return ServerResponse.createByErrorMessage("该用户名已存在");
        }

        if(engineerInfo.getEmail() != null) {
            EngineerInfo dbEngineerInfoByEmail = engineerInfoMapper.selectByUserName(null, engineerInfo.getEngineerId(), engineerInfo.getEmail());
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
        EngineerInfo dbEngineerInfo = engineerInfoMapper.selectByUserName(engineerInfo.getEngineerName(),null,engineerInfo.getEmail());
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
        if (engineerInfo == null && engineerInfo.getEngineerName() == null && engineerInfo.getEmail() == null)
            return ServerResponse.createByErrorMessage("参数不能为空");
        EngineerInfo dbEngineerInfo = engineerInfoMapper.selectByUserName(engineerInfo.getEngineerName(),engineerInfo.getEngineerId(),engineerInfo.getEmail());
       if (dbEngineerInfo != null)
           return ServerResponse.createByErrorMessage("已存在用户名或者邮箱");
       return ServerResponse.createBySuccess("不存在用户名或者邮箱");
    }
}
