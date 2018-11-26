package com.util;

import com.common.Const;
import com.dao.CustomerInfoMapper;
import com.dao.EngineerInfoMapper;
import com.dao.OrderInfoMapper;
import com.pojo.CustomerInfo;
import com.pojo.EngineerInfo;
import com.pojo.OrderInfo;
import com.pojo.QuantityInfo;
import com.service.impl.OrderInfoServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.TimerTask;

/**
 * Created by upupgogogo on 2018/11/26.下午5:06
 */
public class TimerChangeOrderFinished extends TimerTask {

    private static  final Logger logger = LoggerFactory.getLogger(TimerChangeOrderFinished.class);

    private Integer orderId;

    private OrderInfoMapper orderInfoMapper;

    private EngineerInfoMapper engineerInfoMapper;

    private CustomerInfoMapper customerInfoMapper;

    private BigDecimal drawEnginnerQae;

    private BigDecimal drawEnginner;

    public TimerChangeOrderFinished(Integer orderId, OrderInfoMapper orderInfoMapper, EngineerInfoMapper engineerInfoMapper, CustomerInfoMapper customerInfoMapper, BigDecimal drawEnginnerQae, BigDecimal drawEnginner) {
        this.orderId = orderId;
        this.orderInfoMapper = orderInfoMapper;
        this.engineerInfoMapper = engineerInfoMapper;
        this.customerInfoMapper = customerInfoMapper;
        this.drawEnginnerQae = drawEnginnerQae;
        this.drawEnginner = drawEnginner;
    }

    public TimerChangeOrderFinished() {
    }



    public void setDrawEnginner(BigDecimal drawEnginner) {
        this.drawEnginner = drawEnginner;
    }

    public void setDrawEnginnerQae(BigDecimal drawEnginnerQae) {
        this.drawEnginnerQae = drawEnginnerQae;
    }

    public BigDecimal getDrawEnginnerQae() {

        return drawEnginnerQae;
    }

    public BigDecimal getDrawEnginner() {
        return drawEnginner;
    }

    public void setEngineerInfoMapper(EngineerInfoMapper engineerInfoMapper) {

        this.engineerInfoMapper = engineerInfoMapper;
    }

    public void setCustomerInfoMapper(CustomerInfoMapper customerInfoMapper) {
        this.customerInfoMapper = customerInfoMapper;
    }

    public Integer getOrderId() {

        return orderId;
    }

    public OrderInfoMapper getOrderInfoMapper() {
        return orderInfoMapper;
    }

    public EngineerInfoMapper getEngineerInfoMapper() {
        return engineerInfoMapper;
    }

    public CustomerInfoMapper getCustomerInfoMapper() {
        return customerInfoMapper;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public void setOrderInfoMapper(OrderInfoMapper orderInfoMapper) {
        this.orderInfoMapper = orderInfoMapper;
    }

    @Override
    public void run() {
       OrderInfo orderInfo = orderInfoMapper.selectByPrimaryKey(orderId);
       if (orderInfo.getOrderState() == Const.Order.HAVE_REIVER_ORDER){
           orderInfo.setOrderState(Const.Order.HAVE_FINISHED);
           orderInfoMapper.updateByPrimaryKeySelective(orderInfo);
           CustomerInfo customerInfo = customerInfoMapper.selectByPrimaryKey(orderInfo.getCustomerId());
           customerInfo.setOrderCount(customerInfo.getOrderCount() == null ? 1 : customerInfo.getOrderCount() + 1);
           customerInfoMapper.updateByPrimaryKeySelective(customerInfo);
           EngineerInfo engineerInfo = engineerInfoMapper.selectByPrimaryKey(orderInfo.getEngineerId());
           BigDecimal orderPrice = BigDecimalUtil.mul(orderInfo.getOrderPrice().doubleValue(),drawEnginner.doubleValue());
           engineerInfo.setEngineerBalance(engineerInfo.getEngineerBalance() == null ? orderPrice : BigDecimalUtil.add(engineerInfo.getEngineerBalance().doubleValue(),orderPrice.doubleValue()));

           engineerInfo.setOrderCount(engineerInfo.getOrderCount() - 1);
           engineerInfoMapper.updateByPrimaryKey(engineerInfo);

           try {
               MailUtil.sendMail(engineerInfo.getEmail(), "您的订单已完成");
           } catch (Exception e) {
               logger.debug("邮件发送失败");
           }

       }
    }


}
