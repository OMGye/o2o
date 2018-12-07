package com.service.impl;

import com.common.Const;
import com.common.ServerResponse;
import com.dao.BillInfoMapper;
import com.dao.CustomerInfoMapper;
import com.dao.DrawCashInfoMapper;
import com.dao.EngineerInfoMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pojo.BillInfo;
import com.pojo.CustomerInfo;
import com.pojo.DrawCashInfo;
import com.pojo.EngineerInfo;
import com.service.DrawCashInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by upupgogogo on 2018/12/7.下午2:27
 */
@Service("drawCashInfoService")
public class DrawCashInfoServiceImpl implements DrawCashInfoService {

    @Autowired
    private CustomerInfoMapper customerInfoMapper;

    @Autowired
    private EngineerInfoMapper engineerInfoMapper;

    @Autowired
    private BillInfoMapper billInfoMapper;

    @Autowired
    private DrawCashInfoMapper drawCashInfoMapper;

    @Override
    public ServerResponse add(DrawCashInfo drawCashInfo, Integer userId, Integer type, String userName) {

        if (drawCashInfo.getDrawAccount() == null)
            return ServerResponse.createByErrorMessage("参数为空");

        if (type == 0){
            CustomerInfo customerInfo = customerInfoMapper.selectByPrimaryKey(userId);
            if (customerInfo.getCustomerBalance().doubleValue() > 0){
                drawCashInfo.setDrawCashMoney(customerInfo.getCustomerBalance());
                drawCashInfo.setUserId(customerInfo.getCustomerId());
                drawCashInfo.setUserType(0);
                drawCashInfo.setUserName(customerInfo.getCustomerName());
                drawCashInfo.setState(Const.Draw.UN_DEAL);
                int row = drawCashInfoMapper.insert(drawCashInfo);
                if (row > 0){
                    customerInfo.setCustomerBalance(new BigDecimal(0));
                    customerInfoMapper.updateByPrimaryKeySelective(customerInfo);

                    BillInfo billInfo = new BillInfo();
                    billInfo.setUserId(customerInfo.getCustomerId());
                    billInfo.setUserName(customerInfo.getCustomerName());
                    billInfo.setBillDec("提现操作");
                    billInfo.setBillMoney(drawCashInfo.getDrawCashMoney());
                    billInfo.setUserType(0);

                    billInfoMapper.insert(billInfo);
                    return ServerResponse.createBySuccess("提现成功");
                }
                return ServerResponse.createByErrorMessage("操作失败");
            }
        }

        if (type == 1){
            EngineerInfo engineerInfo = engineerInfoMapper.selectByPrimaryKey(userId);
            if (engineerInfo.getEngineerBalance().doubleValue() > 0){
                drawCashInfo.setDrawCashMoney(engineerInfo.getEngineerBalance());
                drawCashInfo.setUserId(engineerInfo.getEngineerId());
                drawCashInfo.setUserType(1);
                drawCashInfo.setUserName(engineerInfo.getEngineerName());
                drawCashInfo.setState(Const.Draw.UN_DEAL);
                int row = drawCashInfoMapper.insert(drawCashInfo);
                if (row > 0){
                    engineerInfo.setEngineerBalance(new BigDecimal(0));
                    engineerInfoMapper.updateByPrimaryKeySelective(engineerInfo);

                    BillInfo billInfo = new BillInfo();
                    billInfo.setUserId(engineerInfo.getEngineerId());
                    billInfo.setUserName(engineerInfo.getEngineerName());
                    billInfo.setBillDec("提现操作");
                    billInfo.setBillMoney(drawCashInfo.getDrawCashMoney());
                    billInfo.setUserType(1);

                    billInfoMapper.insert(billInfo);
                    return ServerResponse.createBySuccess("提现成功");
                }
                return ServerResponse.createByErrorMessage("操作失败");
            }
        }
        return ServerResponse.createByErrorMessage("操作失败");
    }

    @Override
    public ServerResponse list(int pageSize, int pageNum, Integer userId, Integer type, Integer state) {
        PageHelper.startPage(pageNum,pageSize);
        PageHelper.orderBy("create_time desc");
        List<DrawCashInfo> list = drawCashInfoMapper.list(userId,type,state);
        PageInfo pageInfo = new PageInfo(list);
        return ServerResponse.createBySuccess(pageInfo);
    }

    @Override
    public ServerResponse check(Integer drawCashId) {
        if (drawCashId == null)
            return ServerResponse.createByErrorMessage("参数为空");

        DrawCashInfo drawCashInfo = drawCashInfoMapper.selectByPrimaryKey(drawCashId);
        if (drawCashInfo != null){
            drawCashInfo.setState(Const.Draw.DEAL);
            int row = drawCashInfoMapper.updateByPrimaryKeySelective(drawCashInfo);
            if (row > 0)
                return ServerResponse.createBySuccess("操作成功");
        }
        return ServerResponse.createByErrorMessage("操作失败");
    }

    @Override
    public ServerResponse getById(Integer drawCashId) {
        if (drawCashId == null)
            return ServerResponse.createByErrorMessage("参数为空");
        DrawCashInfo drawCashInfo = drawCashInfoMapper.selectByPrimaryKey(drawCashId);
        return ServerResponse.createBySuccess(drawCashInfo);
    }
}
