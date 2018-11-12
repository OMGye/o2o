package com.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class PriceTogetherInfo {
    private Integer priceTogetherId;

    private Integer priceTogetherNum;

    private BigDecimal priceTogetherPercentage;

    private String priceTogetherName;

    private String priceTogetherDec;

    private Date createTime;

    private Date updateTime;

    public PriceTogetherInfo(Integer priceTogetherId, Integer priceTogetherNum, BigDecimal priceTogetherPercentage, String priceTogetherName, String priceTogetherDec, Date createTime, Date updateTime) {
        this.priceTogetherId = priceTogetherId;
        this.priceTogetherNum = priceTogetherNum;
        this.priceTogetherPercentage = priceTogetherPercentage;
        this.priceTogetherName = priceTogetherName;
        this.priceTogetherDec = priceTogetherDec;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public PriceTogetherInfo() {
        super();
    }

    public Integer getPriceTogetherId() {
        return priceTogetherId;
    }

    public void setPriceTogetherId(Integer priceTogetherId) {
        this.priceTogetherId = priceTogetherId;
    }

    public Integer getPriceTogetherNum() {
        return priceTogetherNum;
    }

    public void setPriceTogetherNum(Integer priceTogetherNum) {
        this.priceTogetherNum = priceTogetherNum;
    }

    public BigDecimal getPriceTogetherPercentage() {
        return priceTogetherPercentage;
    }

    public void setPriceTogetherPercentage(BigDecimal priceTogetherPercentage) {
        this.priceTogetherPercentage = priceTogetherPercentage;
    }

    public String getPriceTogetherName() {
        return priceTogetherName;
    }

    public void setPriceTogetherName(String priceTogetherName) {
        this.priceTogetherName = priceTogetherName == null ? null : priceTogetherName.trim();
    }

    public String getPriceTogetherDec() {
        return priceTogetherDec;
    }

    public void setPriceTogetherDec(String priceTogetherDec) {
        this.priceTogetherDec = priceTogetherDec == null ? null : priceTogetherDec.trim();
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