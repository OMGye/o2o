package com.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class SelfCategory {
    private Integer selfCategoryId;

    private String selfCategoryName;

    private BigDecimal selfCategoryPrice;

    private Integer rank;

    private Date createTime;

    private Date updateTime;

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Integer getRank() {

        return rank;
    }

    public SelfCategory(Integer selfCategoryId, String selfCategoryName, BigDecimal selfCategoryPrice, Integer rank, Date createTime, Date updateTime) {

        this.selfCategoryId = selfCategoryId;
        this.selfCategoryName = selfCategoryName;
        this.selfCategoryPrice = selfCategoryPrice;
        this.rank = rank;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public SelfCategory() {
        super();
    }

    public Integer getSelfCategoryId() {
        return selfCategoryId;
    }

    public void setSelfCategoryId(Integer selfCategoryId) {
        this.selfCategoryId = selfCategoryId;
    }

    public String getSelfCategoryName() {
        return selfCategoryName;
    }

    public void setSelfCategoryName(String selfCategoryName) {
        this.selfCategoryName = selfCategoryName == null ? null : selfCategoryName.trim();
    }

    public BigDecimal getSelfCategoryPrice() {
        return selfCategoryPrice;
    }

    public void setSelfCategoryPrice(BigDecimal selfCategoryPrice) {
        this.selfCategoryPrice = selfCategoryPrice;
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