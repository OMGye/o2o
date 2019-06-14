package com.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class SelfQuantityInfo {
    private Integer selfQuantityId;

    private Integer selfQuantiyRank;

    private BigDecimal quantityPercentage;

    private BigDecimal quantityDraw;

    private String quantityDec;

    private Date createTime;

    private Date updateTime;

    public SelfQuantityInfo(Integer selfQuantityId, Integer selfQuantiyRank, BigDecimal quantityPercentage, BigDecimal quantityDraw, String quantityDec, Date createTime, Date updateTime) {
        this.selfQuantityId = selfQuantityId;
        this.selfQuantiyRank = selfQuantiyRank;
        this.quantityPercentage = quantityPercentage;
        this.quantityDraw = quantityDraw;
        this.quantityDec = quantityDec;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public SelfQuantityInfo() {
        super();
    }

    public Integer getSelfQuantityId() {
        return selfQuantityId;
    }

    public void setSelfQuantityId(Integer selfQuantityId) {
        this.selfQuantityId = selfQuantityId;
    }

    public Integer getSelfQuantiyRank() {
        return selfQuantiyRank;
    }

    public void setSelfQuantiyRank(Integer selfQuantiyRank) {
        this.selfQuantiyRank = selfQuantiyRank;
    }

    public BigDecimal getQuantityPercentage() {
        return quantityPercentage;
    }

    public void setQuantityPercentage(BigDecimal quantityPercentage) {
        this.quantityPercentage = quantityPercentage;
    }

    public BigDecimal getQuantityDraw() {
        return quantityDraw;
    }

    public void setQuantityDraw(BigDecimal quantityDraw) {
        this.quantityDraw = quantityDraw;
    }

    public String getQuantityDec() {
        return quantityDec;
    }

    public void setQuantityDec(String quantityDec) {
        this.quantityDec = quantityDec == null ? null : quantityDec.trim();
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