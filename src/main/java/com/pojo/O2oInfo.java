package com.pojo;

import java.util.Date;

public class O2oInfo {
    private Integer o2oId;

    private String o2oParamName;

    private String o2oParamValue;

    private Date createTime;

    private Date updateTime;

    public O2oInfo(Integer o2oId, String o2oParamName, String o2oParamValue, Date createTime, Date updateTime) {
        this.o2oId = o2oId;
        this.o2oParamName = o2oParamName;
        this.o2oParamValue = o2oParamValue;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public O2oInfo() {
        super();
    }

    public Integer getO2oId() {
        return o2oId;
    }

    public void setO2oId(Integer o2oId) {
        this.o2oId = o2oId;
    }

    public String getO2oParamName() {
        return o2oParamName;
    }

    public void setO2oParamName(String o2oParamName) {
        this.o2oParamName = o2oParamName == null ? null : o2oParamName.trim();
    }

    public String getO2oParamValue() {
        return o2oParamValue;
    }

    public void setO2oParamValue(String o2oParamValue) {
        this.o2oParamValue = o2oParamValue == null ? null : o2oParamValue.trim();
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