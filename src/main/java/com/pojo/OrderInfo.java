package com.pojo;

import java.util.Date;

public class OrderInfo {
    private Integer orderId;

    private Integer customerId;

    private String customerName;

    private Integer engineerId;

    private String engineerName;

    private Integer engineerCheckId;

    private String engineerCheckName;

    private String orderFirstCategory;

    private String orderSecondCategory;

    private Integer orderMi;

    private Integer orderQae;

    private String orderDec;

    private Integer orderRush;

    private Integer orderDeductRank;

    private String orderDeductDec;

    private String orderRateDec;

    private Integer orderState;

    private Date createTime;

    private Date updateTime;

    public OrderInfo(Integer orderId, Integer customerId, String customerName, Integer engineerId, String engineerName, Integer engineerCheckId, String engineerCheckName, String orderFirstCategory, String orderSecondCategory, Integer orderMi, Integer orderQae, String orderDec, Integer orderRush, Integer orderDeductRank, String orderDeductDec, String orderRateDec, Integer orderState, Date createTime, Date updateTime) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.customerName = customerName;
        this.engineerId = engineerId;
        this.engineerName = engineerName;
        this.engineerCheckId = engineerCheckId;
        this.engineerCheckName = engineerCheckName;
        this.orderFirstCategory = orderFirstCategory;
        this.orderSecondCategory = orderSecondCategory;
        this.orderMi = orderMi;
        this.orderQae = orderQae;
        this.orderDec = orderDec;
        this.orderRush = orderRush;
        this.orderDeductRank = orderDeductRank;
        this.orderDeductDec = orderDeductDec;
        this.orderRateDec = orderRateDec;
        this.orderState = orderState;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public OrderInfo() {
        super();
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName == null ? null : customerName.trim();
    }

    public Integer getEngineerId() {
        return engineerId;
    }

    public void setEngineerId(Integer engineerId) {
        this.engineerId = engineerId;
    }

    public String getEngineerName() {
        return engineerName;
    }

    public void setEngineerName(String engineerName) {
        this.engineerName = engineerName == null ? null : engineerName.trim();
    }

    public Integer getEngineerCheckId() {
        return engineerCheckId;
    }

    public void setEngineerCheckId(Integer engineerCheckId) {
        this.engineerCheckId = engineerCheckId;
    }

    public String getEngineerCheckName() {
        return engineerCheckName;
    }

    public void setEngineerCheckName(String engineerCheckName) {
        this.engineerCheckName = engineerCheckName == null ? null : engineerCheckName.trim();
    }

    public String getOrderFirstCategory() {
        return orderFirstCategory;
    }

    public void setOrderFirstCategory(String orderFirstCategory) {
        this.orderFirstCategory = orderFirstCategory == null ? null : orderFirstCategory.trim();
    }

    public String getOrderSecondCategory() {
        return orderSecondCategory;
    }

    public void setOrderSecondCategory(String orderSecondCategory) {
        this.orderSecondCategory = orderSecondCategory == null ? null : orderSecondCategory.trim();
    }

    public Integer getOrderMi() {
        return orderMi;
    }

    public void setOrderMi(Integer orderMi) {
        this.orderMi = orderMi;
    }

    public Integer getOrderQae() {
        return orderQae;
    }

    public void setOrderQae(Integer orderQae) {
        this.orderQae = orderQae;
    }

    public String getOrderDec() {
        return orderDec;
    }

    public void setOrderDec(String orderDec) {
        this.orderDec = orderDec == null ? null : orderDec.trim();
    }

    public Integer getOrderRush() {
        return orderRush;
    }

    public void setOrderRush(Integer orderRush) {
        this.orderRush = orderRush;
    }

    public Integer getOrderDeductRank() {
        return orderDeductRank;
    }

    public void setOrderDeductRank(Integer orderDeductRank) {
        this.orderDeductRank = orderDeductRank;
    }

    public String getOrderDeductDec() {
        return orderDeductDec;
    }

    public void setOrderDeductDec(String orderDeductDec) {
        this.orderDeductDec = orderDeductDec == null ? null : orderDeductDec.trim();
    }

    public String getOrderRateDec() {
        return orderRateDec;
    }

    public void setOrderRateDec(String orderRateDec) {
        this.orderRateDec = orderRateDec == null ? null : orderRateDec.trim();
    }

    public Integer getOrderState() {
        return orderState;
    }

    public void setOrderState(Integer orderState) {
        this.orderState = orderState;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}