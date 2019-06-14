package com.pojo;

import java.util.Date;

public class SelfOrderAnsque {
    private Integer orderAnsqueId;

    private Integer selfOrderId;

    private Integer userType;

    private Integer userId;

    private String userName;

    private String orderAnsqueContent;

    private Integer state;

    private Date createTime;

    public SelfOrderAnsque(Integer orderAnsqueId, Integer selfOrderId, Integer userType, Integer userId, String userName, String orderAnsqueContent, Integer state, Date createTime) {
        this.orderAnsqueId = orderAnsqueId;
        this.selfOrderId = selfOrderId;
        this.userType = userType;
        this.userId = userId;
        this.userName = userName;
        this.orderAnsqueContent = orderAnsqueContent;
        this.state = state;
        this.createTime = createTime;
    }

    public SelfOrderAnsque() {
        super();
    }

    public Integer getOrderAnsqueId() {
        return orderAnsqueId;
    }

    public void setOrderAnsqueId(Integer orderAnsqueId) {
        this.orderAnsqueId = orderAnsqueId;
    }

    public Integer getSelfOrderId() {
        return selfOrderId;
    }

    public void setSelfOrderId(Integer selfOrderId) {
        this.selfOrderId = selfOrderId;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getOrderAnsqueContent() {
        return orderAnsqueContent;
    }

    public void setOrderAnsqueContent(String orderAnsqueContent) {
        this.orderAnsqueContent = orderAnsqueContent == null ? null : orderAnsqueContent.trim();
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
}