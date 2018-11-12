package com.pojo;

import java.util.Date;

public class ProposeInfo {
    private Integer proposeId;

    private Integer userId;

    private Integer userType;

    private String userName;

    private String proposeContent;

    private Date createTime;

    public ProposeInfo(Integer proposeId, Integer userId, Integer userType, String userName, String proposeContent, Date createTime) {
        this.proposeId = proposeId;
        this.userId = userId;
        this.userType = userType;
        this.userName = userName;
        this.proposeContent = proposeContent;
        this.createTime = createTime;
    }

    public ProposeInfo() {
        super();
    }

    public Integer getProposeId() {
        return proposeId;
    }

    public void setProposeId(Integer proposeId) {
        this.proposeId = proposeId;
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

    public String getProposeContent() {
        return proposeContent;
    }

    public void setProposeContent(String proposeContent) {
        this.proposeContent = proposeContent == null ? null : proposeContent.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}