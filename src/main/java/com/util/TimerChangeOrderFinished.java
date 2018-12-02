package com.util;

import com.common.Const;
import com.dao.*;
import com.pojo.*;
import com.service.impl.OrderInfoServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

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

    private BillInfoMapper billInfoMapper;

    private IncomeInfoMapper incomeInfoMapper;

    public TimerChangeOrderFinished(Integer orderId, OrderInfoMapper orderInfoMapper, EngineerInfoMapper engineerInfoMapper, CustomerInfoMapper customerInfoMapper, BigDecimal drawEnginnerQae, BigDecimal drawEnginner, BillInfoMapper billInfoMapper, IncomeInfoMapper incomeInfoMapper) {
        this.orderId = orderId;
        this.orderInfoMapper = orderInfoMapper;
        this.engineerInfoMapper = engineerInfoMapper;
        this.customerInfoMapper = customerInfoMapper;
        this.drawEnginnerQae = drawEnginnerQae;
        this.drawEnginner = drawEnginner;
        this.billInfoMapper = billInfoMapper;
        this.incomeInfoMapper = incomeInfoMapper;
    }

    public TimerChangeOrderFinished() {
    }


    public void setBillInfoMapper(BillInfoMapper billInfoMapper) {
        this.billInfoMapper = billInfoMapper;
    }

    public void setIncomeInfoMapper(IncomeInfoMapper incomeInfoMapper) {
        this.incomeInfoMapper = incomeInfoMapper;
    }

    public BillInfoMapper getBillInfoMapper() {

        return billInfoMapper;
    }

    public IncomeInfoMapper getIncomeInfoMapper() {
        return incomeInfoMapper;
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
           customerInfo.setOrderCount(customerInfo.getOrderCount() + 1);
           customerInfoMapper.updateByPrimaryKeySelective(customerInfo);


           EngineerInfo engineerInfo = engineerInfoMapper.selectByPrimaryKey(orderInfo.getEngineerId());
           BigDecimal orderCamPrice = BigDecimalUtil.sub(orderInfo.getOrderPrice().doubleValue(),orderInfo.getOrderQaePrice().doubleValue());
           orderCamPrice = BigDecimalUtil.sub(orderCamPrice.doubleValue(), BigDecimalUtil.mul(orderCamPrice.doubleValue(), drawEnginner.doubleValue()).doubleValue());
           engineerInfo.setEngineerBalance(BigDecimalUtil.add(orderCamPrice.doubleValue(),engineerInfo.getEngineerBalance().doubleValue()));
           engineerInfo.setOrderCount(engineerInfo.getOrderCount() - 1);
           engineerInfoMapper.updateByPrimaryKey(engineerInfo);

           BillInfo billInfo = new BillInfo();
           billInfo.setUserId(engineerInfo.getEngineerId());
           billInfo.setBillMoney(orderCamPrice);
           billInfo.setUserName(engineerInfo.getEngineerName());
           billInfo.setUserType(1);
           billInfo.setBillDec("订单收入 :" + orderInfo.getOrderId());
           billInfoMapper.insert(billInfo);


           BigDecimal orderQaePrice = orderInfo.getOrderQaePrice();
           if (drawEnginnerQae != null){
               EngineerInfo qaeEngineerInfo = engineerInfoMapper.selectByPrimaryKey(orderInfo.getEngineerCheckId());
               orderQaePrice = BigDecimalUtil.sub(orderQaePrice.doubleValue(), BigDecimalUtil.mul(orderQaePrice.doubleValue(), drawEnginnerQae.doubleValue()).doubleValue());
               qaeEngineerInfo.setEngineerBalance(BigDecimalUtil.add(qaeEngineerInfo.getEngineerBalance().doubleValue(), orderQaePrice.doubleValue()));
               qaeEngineerInfo.setOrderCount(qaeEngineerInfo.getOrderCount() - 1);
               engineerInfoMapper.updateByPrimaryKey(qaeEngineerInfo);

               BillInfo billInfoQae = new BillInfo();
               billInfoQae.setUserId(qaeEngineerInfo.getEngineerId());
               billInfoQae.setBillMoney(orderQaePrice);
               billInfoQae.setUserName(qaeEngineerInfo.getEngineerName());
               billInfoQae.setUserType(1);
               billInfoQae.setBillDec("审核订单收入 :" + orderInfo.getOrderId());
               billInfoMapper.insert(billInfoQae);

               try {
                   MailUtil.sendMail(qaeEngineerInfo.getEmail(), "您的订单已完成");
               } catch (Exception e) {
                   logger.debug("邮件发送失败");
               }
           }

           BigDecimal engineerAllPrice = BigDecimalUtil.add(orderQaePrice.doubleValue(),orderCamPrice.doubleValue());
           IncomeInfo incomeInfo = new IncomeInfo();
           incomeInfo.setOrderId(orderId);
           incomeInfo.setIncomeMoney(BigDecimalUtil.sub(orderInfo.getOrderPrice().doubleValue(), engineerAllPrice.doubleValue()));
           incomeInfoMapper.insert(incomeInfo);

           try {
               MailUtil.sendMail(engineerInfo.getEmail(), "您的订单已完成");
           } catch (Exception e) {
               logger.debug("邮件发送失败");
           }

       }
    }


}
