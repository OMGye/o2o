package com.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class IncomeInfo {
    private Integer incomeId;

    private Integer orderId;

    private BigDecimal incomeMoney;

    private Date createTime;

    public IncomeInfo(Integer incomeId, Integer orderId, BigDecimal incomeMoney, Date createTime) {
        this.incomeId = incomeId;
        this.orderId = orderId;
        this.incomeMoney = incomeMoney;
        this.createTime = createTime;
    }

    public IncomeInfo() {
        super();
    }

    public Integer getIncomeId() {
        return incomeId;
    }

    public void setIncomeId(Integer incomeId) {
        this.incomeId = incomeId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public BigDecimal getIncomeMoney() {
        return incomeMoney;
    }

    public void setIncomeMoney(BigDecimal incomeMoney) {
        this.incomeMoney = incomeMoney;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}