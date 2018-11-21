package com.util;

import com.common.Const;
import com.dao.OrderInfoMapper;
import com.pojo.OrderInfo;

import java.util.TimerTask;

/**
 * Created by upupgogogo on 2018/11/21.下午6:34
 */
public class TimerChangePayOrder extends TimerTask {

    private Integer orderId;
    private OrderInfoMapper orderInfoMapper;

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public void setOrderInfoMapper(OrderInfoMapper orderInfoMapper) {
        this.orderInfoMapper = orderInfoMapper;
    }

    @Override
    public void run() {
        OrderInfo orderInfo = orderInfoMapper.selectByPrimaryKey(orderId);
        if (orderInfo.getOrderState() == Const.Order.PAYING)
            orderInfo.setOrderState(Const.Order.CANNCEL);
        orderInfoMapper.updateByPrimaryKeySelective(orderInfo);
    }

    public TimerChangePayOrder() {
    }

    public TimerChangePayOrder(Integer orderId, OrderInfoMapper orderInfoMapper) {

        this.orderId = orderId;
        this.orderInfoMapper = orderInfoMapper;
    }
}
