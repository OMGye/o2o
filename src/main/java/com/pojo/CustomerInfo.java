package com.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class CustomerInfo {
    private Integer customerId;

    private String customerName;

    private String password;

    private String phone;

    private String email;

    private String customerQq;

    private String customerProv;

    private String customerCity;

    private Byte customerState;

    private String customerPayCount;

    private BigDecimal customerBalance;

    private Date createTime;

    private Date updateTime;

    private String customerAttention;

    public CustomerInfo(Integer customerId, String customerName, String password, String phone, String email, String customerQq, String customerProv, String customerCity, Byte customerState, String customerPayCount, BigDecimal customerBalance, Date createTime, Date updateTime, String customerAttention) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.password = password;
        this.phone = phone;
        this.email = email;
        this.customerQq = customerQq;
        this.customerProv = customerProv;
        this.customerCity = customerCity;
        this.customerState = customerState;
        this.customerPayCount = customerPayCount;
        this.customerBalance = customerBalance;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.customerAttention = customerAttention;
    }

    public CustomerInfo() {
        super();
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

    public String getCustomerQq() {
        return customerQq;
    }

    public void setCustomerQq(String customerQq) {
        this.customerQq = customerQq == null ? null : customerQq.trim();
    }

    public String getCustomerProv() {
        return customerProv;
    }

    public void setCustomerProv(String customerProv) {
        this.customerProv = customerProv == null ? null : customerProv.trim();
    }

    public String getCustomerCity() {
        return customerCity;
    }

    public void setCustomerCity(String customerCity) {
        this.customerCity = customerCity == null ? null : customerCity.trim();
    }

    public Byte getCustomerState() {
        return customerState;
    }

    public void setCustomerState(Byte customerState) {
        this.customerState = customerState;
    }

    public String getCustomerPayCount() {
        return customerPayCount;
    }

    public void setCustomerPayCount(String customerPayCount) {
        this.customerPayCount = customerPayCount == null ? null : customerPayCount.trim();
    }

    public BigDecimal getCustomerBalance() {
        return customerBalance;
    }

    public void setCustomerBalance(BigDecimal customerBalance) {
        this.customerBalance = customerBalance;
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

    public String getCustomerAttention() {
        return customerAttention;
    }

    public void setCustomerAttention(String customerAttention) {
        this.customerAttention = customerAttention == null ? null : customerAttention.trim();
    }
}