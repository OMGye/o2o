package com.service.impl;

import com.common.Const;
import com.common.ServerResponse;
import com.controller.CustomerInfoController;
import com.dao.CustomerInfoMapper;
import com.dao.EngineerInfoMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pojo.BillInfo;
import com.pojo.CustomerInfo;
import com.pojo.EngineerInfo;
import com.service.CustomerInfoService;
import com.util.JsonUtil;
import com.util.MailUtil;
import com.vo.EngineerDefriendJson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by upupgogogo on 2018/11/17.下午1:48
 */
@Service("customerInfoService")
public class CustomerInfoServiceImpl implements CustomerInfoService {

    @Autowired
    private CustomerInfoMapper customerInfoMapper;

    @Autowired
    private EngineerInfoMapper engineerInfoMapper;

    private static  final Logger logger = LoggerFactory.getLogger(CustomerInfoServiceImpl.class);


    @Override
    public ServerResponse register(CustomerInfo customerInfo) {
        if (customerInfo == null || customerInfo.getCustomerName() == null || customerInfo.getPassword() == null || customerInfo.getPhone() == null || customerInfo.getEmail() == null || customerInfo.getPersonCode() == null)
            return ServerResponse.createByErrorMessage("参数不能为空");

        if(customerInfo.getCustomerName() != null){
            CustomerInfo dbCustomer = customerInfoMapper.selectByUserName(customerInfo.getCustomerName(),null,null,null,null);
            if (dbCustomer != null)
                return ServerResponse.createByErrorMessage("该用户名已存在");
        }

        if(customerInfo.getEmail() != null){
            CustomerInfo dbCustomer = customerInfoMapper.selectByUserName(null,null,customerInfo.getEmail(),null, null);
            if (dbCustomer != null)
                return ServerResponse.createByErrorMessage("该邮箱已存在");
        }

        if(customerInfo.getPhone() != null){
            CustomerInfo dbCustomer = customerInfoMapper.selectByUserName(null,null,null,customerInfo.getPhone(), null);
            if (dbCustomer != null)
                return ServerResponse.createByErrorMessage("该电话已存在");
        }

        if(customerInfo.getPersonCode() != null){
            CustomerInfo dbCustomer = customerInfoMapper.selectByUserName(null,null,null,null, customerInfo.getPersonCode());
            if (dbCustomer != null)
                return ServerResponse.createByErrorMessage("该身份证已存在");
        }
        customerInfo.setCustomerBalance(new BigDecimal(0));
        customerInfo.setCustomerState(Const.CustomerInfo.ABLE);
        customerInfo.setOrderCount(0);
        int row = customerInfoMapper.insert(customerInfo);
        if (row > 0)
            return ServerResponse.createBySuccess("注册成功");
        return ServerResponse.createByErrorMessage("注册失败");
    }

    @Override
    public ServerResponse<PageInfo> list(int pageSize, int pageNum, Integer state) {
        PageHelper.startPage(pageNum,pageSize);
        PageHelper.orderBy("customer_id desc");
        List<CustomerInfo> list = customerInfoMapper.list(state);
        PageInfo pageInfo = new PageInfo(list);
        return ServerResponse.createBySuccess(pageInfo);
    }

    @Override
    public ServerResponse<CustomerInfo> getEngineerInfoById(Integer customerId) {
        if (customerId == null)
            return ServerResponse.createByErrorMessage("参数不能为空");
        CustomerInfo customerInfo = customerInfoMapper.selectByPrimaryKey(customerId);
        if (customerInfo == null)
            return ServerResponse.createByErrorMessage("该客户不存在");

        return ServerResponse.createBySuccess(customerInfo);
    }

    @Override
    public ServerResponse<CustomerInfo> login(String customerName, String password) {
        if (customerName == null || password == null)
            return ServerResponse.createByErrorMessage("参数不能为空");
        CustomerInfo dbCurtomerInfo = customerInfoMapper.login(customerName,password);
        if (dbCurtomerInfo == null){
            dbCurtomerInfo = customerInfoMapper.loginByPhone(customerName, password);
            if (dbCurtomerInfo == null)
                return ServerResponse.createByErrorMessage("您的用户名或者密码错误");
        }
        if (dbCurtomerInfo != null){
            if (dbCurtomerInfo.getCustomerState() == Const.CustomerInfo.BAN)
                return ServerResponse.createByErrorMessage("您的账号已被停止使用");
        }
        return ServerResponse.createBySuccess(dbCurtomerInfo);
    }

    @Override
    public ServerResponse update(CustomerInfo customerInfo) {
        if (customerInfo == null || customerInfo.getCustomerId() == null)
            return ServerResponse.createByErrorMessage("参数不能为空");
        CustomerInfo newCustomerInfo = new CustomerInfo();
        newCustomerInfo.setCustomerName(customerInfo.getCustomerName());
        newCustomerInfo.setPassword(customerInfo.getPassword());
        newCustomerInfo.setEmail(customerInfo.getEmail());
        newCustomerInfo.setCustomerCity(customerInfo.getCustomerCity());
        newCustomerInfo.setCustomerProv(customerInfo.getCustomerProv());
        newCustomerInfo.setCustomerPayCount(customerInfo.getCustomerPayCount());
        newCustomerInfo.setCustomerQq(customerInfo.getCustomerQq());
        newCustomerInfo.setCustomerAttention(customerInfo.getCustomerAttention());
        newCustomerInfo.setCustomerId(customerInfo.getCustomerId());
        if(customerInfo.getCustomerName() != null){
            CustomerInfo dbCustomer = customerInfoMapper.selectByUserName(customerInfo.getCustomerName(),customerInfo.getCustomerId(),null,null, null);
            if (dbCustomer != null)
                return ServerResponse.createByErrorMessage("该用户名已存在");
        }

        if(customerInfo.getEmail() != null){
            CustomerInfo dbCustomer = customerInfoMapper.selectByUserName(null,customerInfo.getCustomerId(),customerInfo.getEmail(), null , null);
            if (dbCustomer != null)
                return ServerResponse.createByErrorMessage("该邮箱已存在");
        }

        int row = customerInfoMapper.updateByPrimaryKeySelective(newCustomerInfo);
        if (row > 0)
            return ServerResponse.createBySuccess("修改成功");
        return ServerResponse.createByErrorMessage("修改失败");
    }

    @Override
    public ServerResponse getPassword(CustomerInfo customerInfo) {
        if (customerInfo == null || customerInfo.getCustomerName() == null || customerInfo.getEmail() == null)
            return ServerResponse.createByErrorMessage("参数不能为空");
        CustomerInfo dbCustomerInfo = customerInfoMapper.selectByUserName(customerInfo.getCustomerName(),null,customerInfo.getEmail(), null, null);
        if (dbCustomerInfo  == null)
            return ServerResponse.createByErrorMessage("您的用户名和邮箱不匹配");
        try {
            MailUtil.sendMail(dbCustomerInfo.getEmail(),dbCustomerInfo.getPassword());
        }catch (Exception e){
            logger.debug("e:" + e.getMessage());
            return ServerResponse.createByErrorMessage("邮件发送失败" + e);
        }

        return ServerResponse.createBySuccess("密码已发送至您的邮箱");
    }

    @Override
    public ServerResponse comfirmUserNameAndEmail(CustomerInfo customerInfo) {
        if (customerInfo == null && customerInfo.getCustomerName() == null && customerInfo.getEmail() == null && customerInfo.getPersonCode() == null && customerInfo.getPhone() == null)
            return ServerResponse.createByErrorMessage("参数不能为空");
        if(customerInfo.getCustomerName() != null){
            CustomerInfo dbCustomer = customerInfoMapper.selectByUserName(customerInfo.getCustomerName(),null,null,null,null);
            if (dbCustomer != null)
                return ServerResponse.createByErrorMessage("该用户名已存在");
        }

        if(customerInfo.getEmail() != null){
            CustomerInfo dbCustomer = customerInfoMapper.selectByUserName(null,null,customerInfo.getEmail(),null, null);
            if (dbCustomer != null)
                return ServerResponse.createByErrorMessage("该邮箱已存在");
        }

        if(customerInfo.getPhone() != null){
            CustomerInfo dbCustomer = customerInfoMapper.selectByUserName(null,null,null,customerInfo.getPhone(), null);
            if (dbCustomer != null)
                return ServerResponse.createByErrorMessage("该电话已存在");
        }

        if(customerInfo.getPersonCode() != null){
            CustomerInfo dbCustomer = customerInfoMapper.selectByUserName(null,null,null,null, customerInfo.getPersonCode());
            if (dbCustomer != null)
                return ServerResponse.createByErrorMessage("该身份证已存在");
        }
        return ServerResponse.createBySuccess("验证成功");
    }

    @Override
    public ServerResponse check(CustomerInfo customerInfo) {
        if (customerInfo == null || customerInfo.getCustomerId() == null)
            return ServerResponse.createByErrorMessage("参数不能为空");
        customerInfo.setOrderCount(null);
        customerInfo.setCustomerBalance(null);
        int row = customerInfoMapper.updateByPrimaryKeySelective(customerInfo);
        if (row > 0)
            return ServerResponse.createBySuccess("审核修改成功");
        return ServerResponse.createByErrorMessage("审核修改失败");
    }

    @Override
    public ServerResponse engineerDefriend(Integer customerId, Integer engineerId) {
        if (engineerId == null)
            return ServerResponse.createByErrorMessage("参数为空");
        EngineerInfo engineerInfo = engineerInfoMapper.selectByPrimaryKey(engineerId);
        if (engineerInfo != null){
            List<EngineerDefriendJson> list = new ArrayList<>();
            EngineerDefriendJson engineerDefriendJson = new EngineerDefriendJson();
            engineerDefriendJson.setEngineerId(engineerId);
            engineerDefriendJson.setEngineerName(engineerInfo.getEngineerName());
            list.add(engineerDefriendJson);
            CustomerInfo customerInfo = customerInfoMapper.selectByPrimaryKey(customerId);
            if (customerInfo.getEngineerDefriend() == null){
                customerInfo.setEngineerDefriend(JsonUtil.toJonSting(list));
                int row  = customerInfoMapper.updateByPrimaryKeySelective(customerInfo);
                if (row > 0)
                    return ServerResponse.createBySuccess("拉黑成功");
                else
                    return ServerResponse.createByErrorMessage("拉黑失败");
            }
            list = JsonUtil.toJsonList(customerInfo.getEngineerDefriend());
            list.add(engineerDefriendJson);
            customerInfo.setEngineerDefriend(JsonUtil.toJonSting(list));
            int row  = customerInfoMapper.updateByPrimaryKeySelective(customerInfo);
            if (row > 0)
                return ServerResponse.createBySuccess("拉黑成功");
            else
                return ServerResponse.createByErrorMessage("拉黑失败");

        }
        return ServerResponse.createByErrorMessage("找不到该工程师");
    }


    @Override
    public ServerResponse<PageInfo> selectByIdLike(int pageNum, int pageSize, Integer customerId) {
        if (customerId == null)
            return ServerResponse.createByErrorMessage("参数为空");
        String customerIdString = "" + customerId + "%";
        PageHelper.startPage(pageNum,pageSize);
        PageHelper.orderBy("create_time asc");
        List<CustomerInfo> list = customerInfoMapper.selectByIdLike(customerIdString);
        PageInfo pageInfo = new PageInfo(list);
        return ServerResponse.createBySuccess(pageInfo);
    }


}
