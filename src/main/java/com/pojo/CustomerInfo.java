package com.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class CustomerInfo {
    private Integer customerId;

    private String customerName;

    private String password;

    private String phone;

    private String email;

    private String personCode;

    private String customerQq;

    private String customerProv;

    private String customerCity;

    private Byte customerState;

    private String customerPayCount;

    private BigDecimal customerBalance;

    private String customerAttention;

    private Integer orderCount;

    private String engineerDefriend;

    private Date createTime;

    private Date updateTime;


    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPersonCode(String personCode) {
        this.personCode = personCode;
    }

    public void setCustomerQq(String customerQq) {
        this.customerQq = customerQq;
    }

    public void setCustomerProv(String customerProv) {
        this.customerProv = customerProv;
    }

    public void setCustomerCity(String customerCity) {
        this.customerCity = customerCity;
    }

    public void setCustomerState(Byte customerState) {
        this.customerState = customerState;
    }

    public void setCustomerPayCount(String customerPayCount) {
        this.customerPayCount = customerPayCount;
    }

    public void setCustomerBalance(BigDecimal customerBalance) {
        this.customerBalance = customerBalance;
    }

    public void setCustomerAttention(String customerAttention) {
        this.customerAttention = customerAttention;
    }

    public void setOrderCount(Integer orderCount) {
        this.orderCount = orderCount;
    }

    public void setEngineerDefriend(String engineerDefriend) {
        this.engineerDefriend = engineerDefriend;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getCustomerId() {

        return customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getPassword() {
        return password;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getPersonCode() {
        return personCode;
    }

    public String getCustomerQq() {
        return customerQq;
    }

    public String getCustomerProv() {
        return customerProv;
    }

    public String getCustomerCity() {
        return customerCity;
    }

    public Byte getCustomerState() {
        return customerState;
    }

    public String getCustomerPayCount() {
        return customerPayCount;
    }

    public BigDecimal getCustomerBalance() {
        return customerBalance;
    }

    public String getCustomerAttention() {
        return customerAttention;
    }

    public Integer getOrderCount() {
        return orderCount;
    }

    public String getEngineerDefriend() {
        return engineerDefriend;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public CustomerInfo(Integer customerId, String customerName, String password, String phone, String email, String personCode, String customerQq, String customerProv, String customerCity, Byte customerState, String customerPayCount, BigDecimal customerBalance, String customerAttention, Integer orderCount, String engineerDefriend, Date createTime, Date updateTime) {

        this.customerId = customerId;
        this.customerName = customerName;
        this.password = password;
        this.phone = phone;
        this.email = email;
        this.personCode = personCode;
        this.customerQq = customerQq;
        this.customerProv = customerProv;
        this.customerCity = customerCity;
        this.customerState = customerState;
        this.customerPayCount = customerPayCount;
        this.customerBalance = customerBalance;
        this.customerAttention = customerAttention;
        this.orderCount = orderCount;
        this.engineerDefriend = engineerDefriend;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public CustomerInfo() {

    }
}