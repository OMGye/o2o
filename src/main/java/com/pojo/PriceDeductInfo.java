package com.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class PriceDeductInfo {
    private Integer priceDeductId;

    private Integer priceDeductRank;

    private BigDecimal priceDeductPercentage;

    private String priceDeductDec;

    private Date createTime;

    private Date updateTime;

    public PriceDeductInfo(Integer priceDeductId, Integer priceDeductRank, BigDecimal priceDeductPercentage, String priceDeductDec, Date createTime, Date updateTime) {
        this.priceDeductId = priceDeductId;
        this.priceDeductRank = priceDeductRank;
        this.priceDeductPercentage = priceDeductPercentage;
        this.priceDeductDec = priceDeductDec;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public PriceDeductInfo() {
        super();
    }

    public Integer getPriceDeductId() {
        return priceDeductId;
    }

    public void setPriceDeductId(Integer priceDeductId) {
        this.priceDeductId = priceDeductId;
    }

    public Integer getPriceDeductRank() {
        return priceDeductRank;
    }

    public void setPriceDeductRank(Integer priceDeductRank) {
        this.priceDeductRank = priceDeductRank;
    }

    public BigDecimal getPriceDeductPercentage() {
        return priceDeductPercentage;
    }

    public void setPriceDeductPercentage(BigDecimal priceDeductPercentage) {
        this.priceDeductPercentage = priceDeductPercentage;
    }

    public String getPriceDeductDec() {
        return priceDeductDec;
    }

    public void setPriceDeductDec(String priceDeductDec) {
        this.priceDeductDec = priceDeductDec == null ? null : priceDeductDec.trim();
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