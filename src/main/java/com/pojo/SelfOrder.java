package com.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class SelfOrder {
    private Integer orderId;

    private Integer customerId;

    private String customerName;

    private Integer engineerId;

    private String engineerName;

    private String orderFirstCategory;

    private String orderSelfCategory;

    private String orderDec;

    private Integer orderDeductRank;

    private String orderDeductDec;

    private Integer orderState;

    private BigDecimal orderPrice;

    private BigDecimal adminPrice;

    private String orderFile;

    private String orderCustomerFile;

    private String customerFileRealName;

    private String refuseDec;

    private Integer download;

    private Date createTime;

    private Date updateTime;

    public SelfOrder(Integer orderId, Integer customerId, String customerName, Integer engineerId, String engineerName, String orderFirstCategory, String orderSelfCategory, String orderDec, Integer orderDeductRank, String orderDeductDec, Integer orderState, BigDecimal orderPrice, BigDecimal adminPrice, String orderFile, String orderCustomerFile, String customerFileRealName, String refuseDec, Integer download, Date createTime, Date updateTime) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.customerName = customerName;
        this.engineerId = engineerId;
        this.engineerName = engineerName;
        this.orderFirstCategory = orderFirstCategory;
        this.orderSelfCategory = orderSelfCategory;
        this.orderDec = orderDec;
        this.orderDeductRank = orderDeductRank;
        this.orderDeductDec = orderDeductDec;
        this.orderState = orderState;
        this.orderPrice = orderPrice;
        this.adminPrice = adminPrice;
        this.orderFile = orderFile;
        this.orderCustomerFile = orderCustomerFile;
        this.customerFileRealName = customerFileRealName;
        this.refuseDec = refuseDec;
        this.download = download;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public SelfOrder() {
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

    public String getOrderFirstCategory() {
        return orderFirstCategory;
    }

    public void setOrderFirstCategory(String orderFirstCategory) {
        this.orderFirstCategory = orderFirstCategory == null ? null : orderFirstCategory.trim();
    }

    public String getOrderSelfCategory() {
        return orderSelfCategory;
    }

    public void setOrderSelfCategory(String orderSelfCategory) {
        this.orderSelfCategory = orderSelfCategory == null ? null : orderSelfCategory.trim();
    }

    public String getOrderDec() {
        return orderDec;
    }

    public void setOrderDec(String orderDec) {
        this.orderDec = orderDec == null ? null : orderDec.trim();
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

    public Integer getOrderState() {
        return orderState;
    }

    public void setOrderState(Integer orderState) {
        this.orderState = orderState;
    }

    public BigDecimal getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(BigDecimal orderPrice) {
        this.orderPrice = orderPrice;
    }

    public BigDecimal getAdminPrice() {
        return adminPrice;
    }

    public void setAdminPrice(BigDecimal adminPrice) {
        this.adminPrice = adminPrice;
    }

    public String getOrderFile() {
        return orderFile;
    }

    public void setOrderFile(String orderFile) {
        this.orderFile = orderFile == null ? null : orderFile.trim();
    }

    public String getOrderCustomerFile() {
        return orderCustomerFile;
    }

    public void setOrderCustomerFile(String orderCustomerFile) {
        this.orderCustomerFile = orderCustomerFile == null ? null : orderCustomerFile.trim();
    }

    public String getCustomerFileRealName() {
        return customerFileRealName;
    }

    public void setCustomerFileRealName(String customerFileRealName) {
        this.customerFileRealName = customerFileRealName == null ? null : customerFileRealName.trim();
    }

    public String getRefuseDec() {
        return refuseDec;
    }

    public void setRefuseDec(String refuseDec) {
        this.refuseDec = refuseDec == null ? null : refuseDec.trim();
    }

    public Integer getDownload() {
        return download;
    }

    public void setDownload(Integer download) {
        this.download = download;
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