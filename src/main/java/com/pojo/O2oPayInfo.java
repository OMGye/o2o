package com.pojo;

import java.util.Date;

public class O2oPayInfo {
    private Integer payInfoId;

    private Integer userId;

    private Integer userType;

    private String userName;

    private Long orderNo;

    private Integer payPlatform;

    private String platformNumber;

    private String platformStatus;

    private Date createTime;

    private Date updateTime;

    public O2oPayInfo(Integer payInfoId, Integer userId, Integer userType, String userName, Long orderNo, Integer payPlatform, String platformNumber, String platformStatus, Date createTime, Date updateTime) {
        this.payInfoId = payInfoId;
        this.userId = userId;
        this.userType = userType;
        this.userName = userName;
        this.orderNo = orderNo;
        this.payPlatform = payPlatform;
        this.platformNumber = platformNumber;
        this.platformStatus = platformStatus;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public O2oPayInfo() {
        super();
    }

    public Integer getPayInfoId() {
        return payInfoId;
    }

    public void setPayInfoId(Integer payInfoId) {
        this.payInfoId = payInfoId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public Long getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Long orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getPayPlatform() {
        return payPlatform;
    }

    public void setPayPlatform(Integer payPlatform) {
        this.payPlatform = payPlatform;
    }

    public String getPlatformNumber() {
        return platformNumber;
    }

    public void setPlatformNumber(String platformNumber) {
        this.platformNumber = platformNumber == null ? null : platformNumber.trim();
    }

    public String getPlatformStatus() {
        return platformStatus;
    }

    public void setPlatformStatus(String platformStatus) {
        this.platformStatus = platformStatus == null ? null : platformStatus.trim();
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