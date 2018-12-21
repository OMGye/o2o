package com.vo;

import java.util.List;

/**
 * Created by upupgogogo on 2018/11/22.下午5:16
 */
public class EngineerRankVO {

    private String firstCategory;

    private List<String> secondCategories;

    private Integer MI;

    private Integer QAE;


    public void setFirstCategory(String firstCategory) {
        this.firstCategory = firstCategory;
    }

    public void setSecondCategories(List<String> secondCategories) {
        this.secondCategories = secondCategories;
    }

    public void setMI(Integer MI) {
        this.MI = MI;
    }

    public void setQAE(Integer QAE) {
        this.QAE = QAE;
    }

    public String getFirstCategory() {

        return firstCategory;
    }

    public List<String> getSecondCategories() {
        return secondCategories;
    }

    public Integer getMI() {
        return MI;
    }

    public Integer getQAE() {
        return QAE;
    }

    public EngineerRankVO() {

    }

    public EngineerRankVO(String firstCategory, List<String> secondCategories, Integer MI, Integer QAE) {

        this.firstCategory = firstCategory;
        this.secondCategories = secondCategories;
        this.MI = MI;
        this.QAE = QAE;
    }

    public static void main(String[] args) {
        String fileName = "ABC.JPG";
        String fisrtName = fileName.substring(0,fileName.indexOf("."));
        System.out.println(fisrtName);
    }
}
