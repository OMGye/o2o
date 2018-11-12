package com.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class BillInfo {
    private Integer billId;

    private Integer userId;

    private Integer userType;

    private String userName;

    private BigDecimal billMoney;

    private String billDec;

    private Date createTime;

    public BillInfo(Integer billId, Integer userId, Integer userType, String userName, BigDecimal billMoney, String billDec, Date createTime) {
        this.billId = billId;
        this.userId = userId;
        this.userType = userType;
        this.userName = userName;
        this.billMoney = billMoney;
        this.billDec = billDec;
        this.createTime = createTime;
    }

    public BillInfo() {
        super();
    }

    public Integer getBillId() {
        return billId;
    }

    public void setBillId(Integer billId) {
        this.billId = billId;
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

    public BigDecimal getBillMoney() {
        return billMoney;
    }

    public void setBillMoney(BigDecimal billMoney) {
        this.billMoney = billMoney;
    }

    public String getBillDec() {
        return billDec;
    }

    public void setBillDec(String billDec) {
        this.billDec = billDec == null ? null : billDec.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}