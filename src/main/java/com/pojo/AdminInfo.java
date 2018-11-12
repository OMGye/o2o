package com.pojo;

import java.util.Date;

public class AdminInfo {
    private Integer adminId;

    private String adminName;

    private String password;

    private String phone;

    private String email;

    private Integer adminRank;

    private Date createTime;

    private Date updateTime;

    public AdminInfo(Integer adminId, String adminName, String password, String phone, String email, Integer adminRank, Date createTime, Date updateTime) {
        this.adminId = adminId;
        this.adminName = adminName;
        this.password = password;
        this.phone = phone;
        this.email = email;
        this.adminRank = adminRank;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public AdminInfo() {
        super();
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName == null ? null : adminName.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public Integer getAdminRank() {
        return adminRank;
    }

    public void setAdminRank(Integer adminRank) {
        this.adminRank = adminRank;
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