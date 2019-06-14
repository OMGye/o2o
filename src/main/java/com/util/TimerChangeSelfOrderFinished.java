package com.util;

import com.common.Const;
import com.dao.*;
import com.pojo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.TimerTask;

/**
 * Created by upupgogogo on 2018/11/26.下午5:06
 */
public class TimerChangeSelfOrderFinished extends TimerTask {

    private static  final Logger logger = LoggerFactory.getLogger(TimerChangeSelfOrderFinished.class);

    private Integer orderId;

    private SelfOrderMapper orderMapper;

    private EngineerInfoMapper engineerInfoMapper;

    private CustomerInfoMapper customerInfoMapper;

    private BigDecimal drawEnginnerQae;

    private BigDecimal drawEnginner;

    private BillInfoMapper billInfoMapper;

    private IncomeInfoMapper incomeInfoMapper;

    public TimerChangeSelfOrderFinished(Integer orderId, SelfOrderMapper orderMapper, EngineerInfoMapper engineerInfoMapper, CustomerInfoMapper customerInfoMapper, BigDecimal drawEnginnerQae, BigDecimal drawEnginner, BillInfoMapper billInfoMapper, IncomeInfoMapper incomeInfoMapper) {
        this.orderId = orderId;
        this.orderMapper = orderMapper;
        this.engineerInfoMapper = engineerInfoMapper;
        this.customerInfoMapper = customerInfoMapper;
        this.drawEnginnerQae = drawEnginnerQae;
        this.drawEnginner = drawEnginner;
        this.billInfoMapper = billInfoMapper;
        this.incomeInfoMapper = incomeInfoMapper;
    }

    public TimerChangeSelfOrderFinished() {
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

    public void setOrderMapper(SelfOrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }

    public SelfOrderMapper getOrderMapper() {

        return orderMapper;
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



    @Override
    public void run() {
       SelfOrder order = orderMapper.selectByPrimaryKey(orderId);
       if (order.getOrderState() == Const.SelfOrder.REIVER){
           order.setOrderState(Const.SelfOrder.FINISHED);

           CustomerInfo customerInfo = customerInfoMapper.selectByPrimaryKey(order.getCustomerId());
           customerInfo.setOrderCount(customerInfo.getOrderCount() + 1);
           customerInfoMapper.updateByPrimaryKeySelective(customerInfo);


           EngineerInfo engineerInfo = engineerInfoMapper.selectByPrimaryKey(order.getEngineerId());
           BigDecimal price = order.getOrderPrice();
           price = BigDecimalUtil.sub(price.doubleValue(), BigDecimalUtil.mul(price.doubleValue(), drawEnginner.doubleValue()).doubleValue());
           engineerInfo.setEngineerBalance(BigDecimalUtil.add(price.doubleValue(),engineerInfo.getEngineerBalance().doubleValue()));
           engineerInfoMapper.updateByPrimaryKey(engineerInfo);

           BillInfo billInfo = new BillInfo();
           billInfo.setUserId(engineerInfo.getEngineerId());
           billInfo.setBillMoney(price);
           billInfo.setUserName(engineerInfo.getEngineerName());
           billInfo.setUserType(1);
           billInfo.setBillDec("自建订单收入 :" + order.getOrderId());
           billInfoMapper.insert(billInfo);


           IncomeInfo incomeInfo = new IncomeInfo();
           incomeInfo.setOrderId(orderId);
           incomeInfo.setIncomeMoney(BigDecimalUtil.sub(order.getOrderPrice().doubleValue(), price.doubleValue()));
           incomeInfoMapper.insert(incomeInfo);

           order.setAdminPrice(incomeInfo.getIncomeMoney());
           orderMapper.updateByPrimaryKeySelective(order);


           try {
               MailUtil.sendMail(engineerInfo.getEmail(), "您的订单已完成");
           } catch (Exception e) {
               logger.debug("邮件发送失败");
           }

       }
    }


}
