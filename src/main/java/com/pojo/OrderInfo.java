package com.pojo;

import java.math.BigDecimal;
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

    private String refuseDec;

    private Integer download;

    private Integer adminCheck;

    private Date createTime;

    private Date updateTime;

    public void setDownload(Integer download) {
        this.download = download;
    }

    public Integer getDownload() {

        return download;
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

    public BigDecimal getEngineerRealPrice() {

        return engineerRealPrice;
    }

    public BigDecimal getEngineerQaeRealPrice() {
        return engineerQaeRealPrice;
    }

    public BigDecimal getAdminPrice() {
        return adminPrice;
    }

    public void setRefuseDec(String refuseDec) {
        this.refuseDec = refuseDec;
    }

    public String getRefuseDec() {

        return refuseDec;
    }

    public void setOrderQaePrice(BigDecimal orderQaePrice) {
        this.orderQaePrice = orderQaePrice;
    }

    public BigDecimal getOrderQaePrice() {

        return orderQaePrice;
    }

    public void setOrderCustomerFile(String orderCustomerFile) {
        this.orderCustomerFile = orderCustomerFile;
    }

    public String getOrderCustomerFile() {

        return orderCustomerFile;
    }

    public void setCustomerFileRealName(String customerFileRealName) {
        this.customerFileRealName = customerFileRealName;
    }

    public void setAdminCheck(Integer adminCheck) {
        this.adminCheck = adminCheck;
    }

    public Integer getAdminCheck() {

        return adminCheck;
    }

    public String getCustomerFileRealName() {

        return customerFileRealName;
    }

    public OrderInfo(Integer orderId, Integer customerId, String customerName, Integer engineerId, String engineerName, Integer engineerCheckId, String engineerCheckName, String orderFirstCategory, String orderSecondCategory, Integer orderMi, Integer orderQae, String otherParamInfo, Integer basicLayer, Integer priceTogetherNum, String orderDec, Integer orderRush, Integer orderDeductRank, String orderDeductDec, String orderRateDec, Integer orderState, BigDecimal orderPrice, BigDecimal orderQaePrice, BigDecimal engineerRealPrice, BigDecimal engineerQaeRealPrice, BigDecimal adminPrice, String orderFile, String orderCustomerFile, String customerFileRealName, String refuseDec, Integer download, Integer adminCheck, Date createTime, Date updateTime) {
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
        this.otherParamInfo = otherParamInfo;
        this.basicLayer = basicLayer;
        this.priceTogetherNum = priceTogetherNum;
        this.orderDec = orderDec;
        this.orderRush = orderRush;
        this.orderDeductRank = orderDeductRank;
        this.orderDeductDec = orderDeductDec;
        this.orderRateDec = orderRateDec;
        this.orderState = orderState;
        this.orderPrice = orderPrice;
        this.orderQaePrice = orderQaePrice;
        this.engineerRealPrice = engineerRealPrice;
        this.engineerQaeRealPrice = engineerQaeRealPrice;
        this.adminPrice = adminPrice;
        this.orderFile = orderFile;
        this.orderCustomerFile = orderCustomerFile;
        this.customerFileRealName = customerFileRealName;
        this.refuseDec = refuseDec;
        this.download = download;
        this.adminCheck = adminCheck;
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

    public String getOtherParamInfo() {
        return otherParamInfo;
    }

    public void setOtherParamInfo(String otherParamInfo) {
        this.otherParamInfo = otherParamInfo == null ? null : otherParamInfo.trim();
    }

    public Integer getBasicLayer() {
        return basicLayer;
    }

    public void setBasicLayer(Integer basicLayer) {
        this.basicLayer = basicLayer;
    }

    public Integer getPriceTogetherNum() {
        return priceTogetherNum;
    }

    public void setPriceTogetherNum(Integer priceTogetherNum) {
        this.priceTogetherNum = priceTogetherNum;
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

    public BigDecimal getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(BigDecimal orderPrice) {
        this.orderPrice = orderPrice;
    }

    public String getOrderFile() {
        return orderFile;
    }

    public void setOrderFile(String orderFile) {
        this.orderFile = orderFile == null ? null : orderFile.trim();
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