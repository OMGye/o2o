package com.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class BasicPriceInfo {
    private Integer basicPriceId;

    private String firstCategory;

    private String secondRank;

    private String thirdCategory;

    private Integer basicLayer;

    private BigDecimal price;

    private String basicDec;

    private Date createTime;

    private Date updateTime;

    public BasicPriceInfo(Integer basicPriceId, String firstCategory, String secondRank, String thirdCategory, Integer basicLayer, BigDecimal price, String basicDec, Date createTime, Date updateTime) {
        this.basicPriceId = basicPriceId;
        this.firstCategory = firstCategory;
        this.secondRank = secondRank;
        this.thirdCategory = thirdCategory;
        this.basicLayer = basicLayer;
        this.price = price;
        this.basicDec = basicDec;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public BasicPriceInfo() {
        super();
    }

    public Integer getBasicPriceId() {
        return basicPriceId;
    }

    public void setBasicPriceId(Integer basicPriceId) {
        this.basicPriceId = basicPriceId;
    }

    public String getFirstCategory() {
        return firstCategory;
    }

    public void setFirstCategory(String firstCategory) {
        this.firstCategory = firstCategory == null ? null : firstCategory.trim();
    }

    public String getSecondRank() {
        return secondRank;
    }

    public void setSecondRank(String secondRank) {
        this.secondRank = secondRank == null ? null : secondRank.trim();
    }

    public String getThirdCategory() {
        return thirdCategory;
    }

    public void setThirdCategory(String thirdCategory) {
        this.thirdCategory = thirdCategory == null ? null : thirdCategory.trim();
    }

    public Integer getBasicLayer() {
        return basicLayer;
    }

    public void setBasicLayer(Integer basicLayer) {
        this.basicLayer = basicLayer;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getBasicDec() {
        return basicDec;
    }

    public void setBasicDec(String basicDec) {
        this.basicDec = basicDec == null ? null : basicDec.trim();
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