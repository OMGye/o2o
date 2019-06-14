package com.vo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Administrator on 2018/12/22.
 */
public class SelfOrderAndMessagenumVo {

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

    private Integer unReadMessage;


    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setEngineerId(Integer engineerId) {
        this.engineerId = engineerId;
    }

    public void setEngineerName(String engineerName) {
        this.engineerName = engineerName;
    }

    public void setOrderFirstCategory(String orderFirstCategory) {
        this.orderFirstCategory = orderFirstCategory;
    }

    public void setOrderSelfCategory(String orderSelfCategory) {
        this.orderSelfCategory = orderSelfCategory;
    }

    public void setOrderDec(String orderDec) {
        this.orderDec = orderDec;
    }

    public void setOrderDeductRank(Integer orderDeductRank) {
        this.orderDeductRank = orderDeductRank;
    }

    public void setOrderDeductDec(String orderDeductDec) {
        this.orderDeductDec = orderDeductDec;
    }

    public void setOrderState(Integer orderState) {
        this.orderState = orderState;
    }

    public void setOrderPrice(BigDecimal orderPrice) {
        this.orderPrice = orderPrice;
    }

    public void setAdminPrice(BigDecimal adminPrice) {
        this.adminPrice = adminPrice;
    }

    public void setOrderFile(String orderFile) {
        this.orderFile = orderFile;
    }

    public void setOrderCustomerFile(String orderCustomerFile) {
        this.orderCustomerFile = orderCustomerFile;
    }

    public void setCustomerFileRealName(String customerFileRealName) {
        this.customerFileRealName = customerFileRealName;
    }

    public void setRefuseDec(String refuseDec) {
        this.refuseDec = refuseDec;
    }

    public void setDownload(Integer download) {
        this.download = download;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public void setUnReadMessage(Integer unReadMessage) {
        this.unReadMessage = unReadMessage;
    }

    public Integer getOrderId() {

        return orderId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public Integer getEngineerId() {
        return engineerId;
    }

    public String getEngineerName() {
        return engineerName;
    }

    public String getOrderFirstCategory() {
        return orderFirstCategory;
    }

    public String getOrderSelfCategory() {
        return orderSelfCategory;
    }

    public String getOrderDec() {
        return orderDec;
    }

    public Integer getOrderDeductRank() {
        return orderDeductRank;
    }

    public String getOrderDeductDec() {
        return orderDeductDec;
    }

    public Integer getOrderState() {
        return orderState;
    }

    public BigDecimal getOrderPrice() {
        return orderPrice;
    }

    public BigDecimal getAdminPrice() {
        return adminPrice;
    }

    public String getOrderFile() {
        return orderFile;
    }

    public String getOrderCustomerFile() {
        return orderCustomerFile;
    }

    public String getCustomerFileRealName() {
        return customerFileRealName;
    }

    public String getRefuseDec() {
        return refuseDec;
    }

    public Integer getDownload() {
        return download;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public Integer getUnReadMessage() {
        return unReadMessage;
    }
}
