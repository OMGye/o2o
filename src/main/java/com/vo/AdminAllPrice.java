package com.vo;

import java.math.BigDecimal;

/**
 * Created by upupgogogo on 2018/12/19.下午5:13
 */
public class AdminAllPrice {

    private BigDecimal payPrice;

    private BigDecimal finshedOrderPrice;

    private BigDecimal unfinshedOrderPrice;

    private BigDecimal customerAndEngineerDrawPrice;

    private BigDecimal incomePrice;

    private BigDecimal customerAndEngineerBalanace;

    public AdminAllPrice() {
    }

    public AdminAllPrice(BigDecimal payPrice, BigDecimal finshedOrderPrice, BigDecimal unfinshedOrderPrice, BigDecimal customerAndEngineerDrawPrice, BigDecimal incomePrice, BigDecimal customerAndEngineerBalanace) {

        this.payPrice = payPrice;
        this.finshedOrderPrice = finshedOrderPrice;
        this.unfinshedOrderPrice = unfinshedOrderPrice;
        this.customerAndEngineerDrawPrice = customerAndEngineerDrawPrice;
        this.incomePrice = incomePrice;
        this.customerAndEngineerBalanace = customerAndEngineerBalanace;
    }

    public void setPayPrice(BigDecimal payPrice) {

        this.payPrice = payPrice;
    }

    public void setFinshedOrderPrice(BigDecimal finshedOrderPrice) {
        this.finshedOrderPrice = finshedOrderPrice;
    }

    public void setUnfinshedOrderPrice(BigDecimal unfinshedOrderPrice) {
        this.unfinshedOrderPrice = unfinshedOrderPrice;
    }

    public void setCustomerAndEngineerDrawPrice(BigDecimal customerAndEngineerDrawPrice) {
        this.customerAndEngineerDrawPrice = customerAndEngineerDrawPrice;
    }

    public void setIncomePrice(BigDecimal incomePrice) {
        this.incomePrice = incomePrice;
    }

    public void setCustomerAndEngineerBalanace(BigDecimal customerAndEngineerBalanace) {
        this.customerAndEngineerBalanace = customerAndEngineerBalanace;
    }

    public BigDecimal getPayPrice() {

        return payPrice;
    }

    public BigDecimal getFinshedOrderPrice() {
        return finshedOrderPrice;
    }

    public BigDecimal getUnfinshedOrderPrice() {
        return unfinshedOrderPrice;
    }

    public BigDecimal getCustomerAndEngineerDrawPrice() {
        return customerAndEngineerDrawPrice;
    }

    public BigDecimal getIncomePrice() {
        return incomePrice;
    }

    public BigDecimal getCustomerAndEngineerBalanace() {
        return customerAndEngineerBalanace;
    }
}
