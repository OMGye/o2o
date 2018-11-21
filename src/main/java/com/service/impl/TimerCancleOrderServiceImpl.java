package com.service.impl;

import com.common.Const;
import com.common.Varible;
import com.dao.OrderInfoMapper;
import com.pojo.OrderInfo;
import com.service.OrderInfoService;
import com.service.TimerCancleOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.TimerTask;

/**
 * Created by upupgogogo on 2018/11/21.下午4:11
 */
@Service("timerCancleOrderService")
public class TimerCancleOrderServiceImpl extends TimerTask implements TimerCancleOrderService {

    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Autowired
    private OrderInfoService orderInfoService;


    @Override
    public void run() {

        OrderInfo orderInfo = orderInfoMapper.selectByPrimaryKey(Varible.UN_PAY_CHAGE_ORDERID);
        if (orderInfo == null)
            return;
        if (orderInfo.getOrderId() == Const.Order.PAYING)
            orderInfo.setOrderState(Const.Order.CANNCEL);
        orderInfoMapper.updateByPrimaryKeySelective(orderInfo);
    }

}
