package com.util;

import com.common.Const;
import com.dao.OrderInfoMapper;
import com.dao.SelfOrderMapper;
import com.pojo.OrderInfo;
import com.pojo.SelfOrder;

import java.util.TimerTask;

/**
 * Created by upupgogogo on 2018/11/21.下午6:34
 */
public class TimerChangePaySelfOrder extends TimerTask {

    private Integer orderId;
    private SelfOrderMapper orderInfoMapper;

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public void setOrderInfoMapper(SelfOrderMapper orderInfoMapper) {
        this.orderInfoMapper = orderInfoMapper;
    }

    @Override
    public void run() {
        SelfOrder orderInfo = orderInfoMapper.selectByPrimaryKey(orderId);
        if (orderInfo.getOrderState() == Const.SelfOrder.CREATE) {
            orderInfo.setOrderState(Const.SelfOrder.CANNCEL);
            orderInfoMapper.updateByPrimaryKeySelective(orderInfo);
        }
    }

    public TimerChangePaySelfOrder() {
    }

    public TimerChangePaySelfOrder(Integer orderId, SelfOrderMapper orderInfoMapper) {

        this.orderId = orderId;
        this.orderInfoMapper = orderInfoMapper;
    }
}
