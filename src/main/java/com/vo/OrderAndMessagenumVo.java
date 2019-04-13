package com.vo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Administrator on 2018/12/22.
 */
public class OrderAndMessagenumVo {

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

    private String otherParamInfo;

    private Integer basicLayer;

    private Integer priceTogetherNum;

    private String orderDec;

    private Integer orderRush;

    private Integer orderDeductRank;

    private String orderDeductDec;

    private String orderRateDec;

    private Integer orderState;

    private BigDecimal orderPrice;

    private BigDecimal orderQaePrice;

    private BigDecimal engineerRealPrice;

    private BigDecimal engineerQaeRealPrice;

    private BigDecimal adminPrice;

    private String orderFile;

    private String orderCustomerFile;

    private String customerFileRealName;

    public void setCustomerFileRealName(String customerFileRealName) {
        this.customerFileRealName = customerFileRealName;
    }

    public String getCustomerFileRealName() {

        return customerFileRealName;
    }

    private String refuseDec;

    private Date createTime;

    private Date updateTime;

    private Integer unReadMessage;

     private Integer adminCheck;

    public void setAdminCheck(Integer adminCheck) {
        this.adminCheck = adminCheck;
    }

    public Integer getAdminCheck() {

        return adminCheck;
    }

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

    public void setEngineerCheckId(Integer engineerCheckId) {
        this.engineerCheckId = engineerCheckId;
    }

    public void setEngineerCheckName(String engineerCheckName) {
        this.engineerCheckName = engineerCheckName;
    }

    public void setOrderFirstCategory(String orderFirstCategory) {
        this.orderFirstCategory = orderFirstCategory;
    }

    public void setOrderSecondCategory(String orderSecondCategory) {
        this.orderSecondCategory = orderSecondCategory;
    }

    public void setOrderMi(Integer orderMi) {
        this.orderMi = orderMi;
    }

    public void setOrderQae(Integer orderQae) {
        this.orderQae = orderQae;
    }

    public void setOtherParamInfo(String otherParamInfo) {
        this.otherParamInfo = otherParamInfo;
    }

    public void setBasicLayer(Integer basicLayer) {
        this.basicLayer = basicLayer;
    }

    public void setPriceTogetherNum(Integer priceTogetherNum) {
        this.priceTogetherNum = priceTogetherNum;
    }

    public void setOrderDec(String orderDec) {
        this.orderDec = orderDec;
    }

    public void setOrderRush(Integer orderRush) {
        this.orderRush = orderRush;
    }

    public void setOrderDeductRank(Integer orderDeductRank) {
        this.orderDeductRank = orderDeductRank;
    }

    public void setOrderDeductDec(String orderDeductDec) {
        this.orderDeductDec = orderDeductDec;
    }

    public void setOrderRateDec(String orderRateDec) {
        this.orderRateDec = orderRateDec;
    }

    public void setOrderState(Integer orderState) {
        this.orderState = orderState;
    }

    public void setOrderPrice(BigDecimal orderPrice) {
        this.orderPrice = orderPrice;
    }

    public void setOrderQaePrice(BigDecimal orderQaePrice) {
        this.orderQaePrice = orderQaePrice;
    }

    public void setEngineerRealPrice(BigDecimal engineerRealPrice) {
        this.engineerRealPrice = engineerRealPrice;
    }

    public void setEngineerQaeRealPrice(BigDecimal engineerQaeRealPrice) {
        this.engineerQaeRealPrice = engineerQaeRealPrice;
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

    public void setRefuseDec(String refuseDec) {
        this.refuseDec = refuseDec;
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

    public Integer getEngineerCheckId() {
        return engineerCheckId;
    }

    public String getEngineerCheckName() {
        return engineerCheckName;
    }

    public String getOrderFirstCategory() {
        return orderFirstCategory;
    }

    public String getOrderSecondCategory() {
        return orderSecondCategory;
    }

    public Integer getOrderMi() {
        return orderMi;
    }

    public Integer getOrderQae() {
        return orderQae;
    }

    public String getOtherParamInfo() {
        return otherParamInfo;
    }

    public Integer getBasicLayer() {
        return basicLayer;
    }

    public Integer getPriceTogetherNum() {
        return priceTogetherNum;
    }

    public String getOrderDec() {
        return orderDec;
    }

    public Integer getOrderRush() {
        return orderRush;
    }

    public Integer getOrderDeductRank() {
        return orderDeductRank;
    }

    public String getOrderDeductDec() {
        return orderDeductDec;
    }

    public String getOrderRateDec() {
        return orderRateDec;
    }

    public Integer getOrderState() {
        return orderState;
    }

    public BigDecimal getOrderPrice() {
        return orderPrice;
    }

    public BigDecimal getOrderQaePrice() {
        return orderQaePrice;
    }

    public BigDecimal getEngineerRealPrice() {
        return engineerRealPrice;
    }

    public BigDecimal getEngineerQaeRealPrice() {
        return engineerQaeRealPrice;
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

    public String getRefuseDec() {
        return refuseDec;
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
