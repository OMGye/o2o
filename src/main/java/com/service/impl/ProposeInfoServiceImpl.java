package com.service.impl;

import com.common.ServerResponse;
import com.dao.CustomerInfoMapper;
import com.dao.EngineerInfoMapper;
import com.dao.ProposeInfoMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pojo.CustomerInfo;
import com.pojo.DrawCashInfo;
import com.pojo.EngineerInfo;
import com.pojo.ProposeInfo;
import com.service.ProposeInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by upupgogogo on 2018/12/7.下午3:30
 */
@Service("proposeInfoService")
public class ProposeInfoServiceImpl implements ProposeInfoService {

    @Autowired
    private ProposeInfoMapper proposeInfoMapper;

    @Autowired
    private CustomerInfoMapper customerInfoMapper;

    @Autowired
    private EngineerInfoMapper engineerInfoMapper;


    @Override
    public ServerResponse add(ProposeInfo proposeInfo, Integer type, Integer userId) {

        if (proposeInfo.getProposeContent() == null)
            return ServerResponse.createByErrorMessage("参数为空");

        if (type == 0){
            CustomerInfo customerInfo = customerInfoMapper.selectByPrimaryKey(userId);

            proposeInfo.setUserId(userId);
            proposeInfo.setUserType(0);
            proposeInfo.setUserName(customerInfo.getCustomerName());
            int row = proposeInfoMapper.insert(proposeInfo);
            if (row > 0)
                return ServerResponse.createBySuccess("操作成功");
        }

        if (type == 1){
            EngineerInfo engineerInfo = engineerInfoMapper.selectByPrimaryKey(userId);

            proposeInfo.setUserId(userId);
            proposeInfo.setUserType(1);
            proposeInfo.setUserName(engineerInfo.getEngineerName());
            int row = proposeInfoMapper.insert(proposeInfo);
            if (row > 0)
                return ServerResponse.createBySuccess("操作成功");
        }

        return ServerResponse.createByErrorMessage("操作失败");
    }

    @Override
    public ServerResponse list(int pageSize, int pageNum) {
        PageHelper.startPage(pageNum,pageSize);
        PageHelper.orderBy("create_time desc");
        List<ProposeInfo> list = proposeInfoMapper.list();
        PageInfo pageInfo = new PageInfo(list);
        return ServerResponse.createBySuccess(pageInfo);
    }

    @Override
    public ServerResponse delete(Integer proposeId, Integer userId, Integer type) {
        if (proposeId == null)
            return ServerResponse.createByErrorMessage("参数为空");

        if (userId == null && type == null){
            int row = proposeInfoMapper.deleteByPrimaryKey(proposeId);
            if (row > 0)
                return ServerResponse.createBySuccess("操作成功");
            return ServerResponse.createByErrorMessage("操作失败");
        }

        ProposeInfo proposeInfo = proposeInfoMapper.selectByPrimaryKey(proposeId);
        if (proposeInfo != null){
            if (proposeInfo.getUserType().intValue() == type.intValue() && proposeInfo.getUserId().intValue() == userId.intValue()){
                int row = proposeInfoMapper.deleteByPrimaryKey(proposeId);
                if (row > 0)
                    return ServerResponse.createBySuccess("操作成功");
                return ServerResponse.createByErrorMessage("操作失败");
            }
        }
        return ServerResponse.createByErrorMessage("操作失败");
    }

    @Override
    public ServerResponse getById(Integer proposeId) {
        if (proposeId == null)
            return ServerResponse.createByErrorMessage("参数为空");
        ProposeInfo proposeInfo = proposeInfoMapper.selectByPrimaryKey(proposeId);
        return ServerResponse.createBySuccess(proposeInfo);
    }
}
