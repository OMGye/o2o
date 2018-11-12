package com.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class QuantityInfo {
    private Integer quantityId;

    private Integer quantiyRank;

    private BigDecimal quantityPercentage;

    private BigDecimal quantityDraw;

    private String quantityDec;

    private Date createTime;

    private Date updateTime;

    public QuantityInfo(Integer quantityId, Integer quantiyRank, BigDecimal quantityPercentage, BigDecimal quantityDraw, String quantityDec, Date createTime, Date updateTime) {
        this.quantityId = quantityId;
        this.quantiyRank = quantiyRank;
        this.quantityPercentage = quantityPercentage;
        this.quantityDraw = quantityDraw;
        this.quantityDec = quantityDec;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public QuantityInfo() {
        super();
    }

    public Integer getQuantityId() {
        return quantityId;
    }

    public void setQuantityId(Integer quantityId) {
        this.quantityId = quantityId;
    }

    public Integer getQuantiyRank() {
        return quantiyRank;
    }

    public void setQuantiyRank(Integer quantiyRank) {
        this.quantiyRank = quantiyRank;
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