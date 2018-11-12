package com.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class DrawCashInfo {
    private Integer drawCashId;

    private Integer userId;

    private Integer userType;

    private String userName;

    private BigDecimal drawCashMoney;

    private String drawDec;

    private String drawAccount;

    private Integer state;

    private Date createTime;

    private Date updateTime;

    public DrawCashInfo(Integer drawCashId, Integer userId, Integer userType, String userName, BigDecimal drawCashMoney, String drawDec, String drawAccount, Integer state, Date createTime, Date updateTime) {
        this.drawCashId = drawCashId;
        this.userId = userId;
        this.userType = userType;
        this.userName = userName;
        this.drawCashMoney = drawCashMoney;
        this.drawDec = drawDec;
        this.drawAccount = drawAccount;
        this.state = state;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public DrawCashInfo() {
        super();
    }

    public Integer getDrawCashId() {
        return drawCashId;
    }

    public void setDrawCashId(Integer drawCashId) {
        this.drawCashId = drawCashId;
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

    public BigDecimal getDrawCashMoney() {
        return drawCashMoney;
    }

    public void setDrawCashMoney(BigDecimal drawCashMoney) {
        this.drawCashMoney = drawCashMoney;
    }

    public String getDrawDec() {
        return drawDec;
    }

    public void setDrawDec(String drawDec) {
        this.drawDec = drawDec == null ? null : drawDec.trim();
    }

    public String getDrawAccount() {
        return drawAccount;
    }

    public void setDrawAccount(String drawAccount) {
        this.drawAccount = drawAccount == null ? null : drawAccount.trim();
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
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