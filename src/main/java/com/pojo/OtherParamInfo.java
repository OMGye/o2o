package com.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class OtherParamInfo {
    private Integer paramId;

    private String paramName;

    private BigDecimal paramPercentage;

    private String paramDec;

    private Date createTime;

    private Date updateTime;

    public OtherParamInfo(Integer paramId, String paramName, BigDecimal paramPercentage, String paramDec, Date createTime, Date updateTime) {
        this.paramId = paramId;
        this.paramName = paramName;
        this.paramPercentage = paramPercentage;
        this.paramDec = paramDec;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public OtherParamInfo() {
        super();
    }

    public Integer getParamId() {
        return paramId;
    }

    public void setParamId(Integer paramId) {
        this.paramId = paramId;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName == null ? null : paramName.trim();
    }

    public BigDecimal getParamPercentage() {
        return paramPercentage;
    }

    public void setParamPercentage(BigDecimal paramPercentage) {
        this.paramPercentage = paramPercentage;
    }

    public String getParamDec() {
        return paramDec;
    }

    public void setParamDec(String paramDec) {
        this.paramDec = paramDec == null ? null : paramDec.trim();
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