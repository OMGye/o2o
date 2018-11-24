package com.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class EngineerInfo {
    private Integer engineerId;

    private String engineerName;

    private String password;

    private String phone;

    private String email;

    private String engineerQq;

    private String engineerProv;

    private String engineerCity;

    private Byte engineerState;

    private String engineerPayCount;

    private BigDecimal engineerBalance;

    private String engineerFile;

    private Integer engineerRank;

    private String engineerClassfy;

    private BigDecimal engineerQuantity;

    private Integer orderCount;

    private Date createTime;

    private Date updateTime;

    public void setOrderCount(Integer orderCount) {
        this.orderCount = orderCount;
    }

    public Integer getOrderCount() {

        return orderCount;
    }

    public EngineerInfo(Integer engineerId, String engineerName, String password, String phone, String email, String engineerQq, String engineerProv, String engineerCity, Byte engineerState, String engineerPayCount, BigDecimal engineerBalance, String engineerFile, Integer engineerRank, String engineerClassfy, BigDecimal engineerQuantity, Integer orderCount, Date createTime, Date updateTime) {

        this.engineerId = engineerId;
        this.engineerName = engineerName;
        this.password = password;
        this.phone = phone;
        this.email = email;
        this.engineerQq = engineerQq;
        this.engineerProv = engineerProv;
        this.engineerCity = engineerCity;
        this.engineerState = engineerState;
        this.engineerPayCount = engineerPayCount;
        this.engineerBalance = engineerBalance;
        this.engineerFile = engineerFile;
        this.engineerRank = engineerRank;
        this.engineerClassfy = engineerClassfy;
        this.engineerQuantity = engineerQuantity;
        this.orderCount = orderCount;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public EngineerInfo() {
        super();
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

    public String getEngineerQq() {
        return engineerQq;
    }

    public void setEngineerQq(String engineerQq) {
        this.engineerQq = engineerQq == null ? null : engineerQq.trim();
    }

    public String getEngineerProv() {
        return engineerProv;
    }

    public void setEngineerProv(String engineerProv) {
        this.engineerProv = engineerProv == null ? null : engineerProv.trim();
    }

    public String getEngineerCity() {
        return engineerCity;
    }

    public void setEngineerCity(String engineerCity) {
        this.engineerCity = engineerCity == null ? null : engineerCity.trim();
    }

    public Byte getEngineerState() {
        return engineerState;
    }

    public void setEngineerState(Byte engineerState) {
        this.engineerState = engineerState;
    }

    public String getEngineerPayCount() {
        return engineerPayCount;
    }

    public void setEngineerPayCount(String engineerPayCount) {
        this.engineerPayCount = engineerPayCount == null ? null : engineerPayCount.trim();
    }

    public BigDecimal getEngineerBalance() {
        return engineerBalance;
    }

    public void setEngineerBalance(BigDecimal engineerBalance) {
        this.engineerBalance = engineerBalance;
    }

    public String getEngineerFile() {
        return engineerFile;
    }

    public void setEngineerFile(String engineerFile) {
        this.engineerFile = engineerFile == null ? null : engineerFile.trim();
    }

    public Integer getEngineerRank() {
        return engineerRank;
    }

    public void setEngineerRank(Integer engineerRank) {
        this.engineerRank = engineerRank;
    }

    public String getEngineerClassfy() {
        return engineerClassfy;
    }

    public void setEngineerClassfy(String engineerClassfy) {
        this.engineerClassfy = engineerClassfy == null ? null : engineerClassfy.trim();
    }

    public BigDecimal getEngineerQuantity() {
        return engineerQuantity;
    }

    public void setEngineerQuantity(BigDecimal engineerQuantity) {
        this.engineerQuantity = engineerQuantity;
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