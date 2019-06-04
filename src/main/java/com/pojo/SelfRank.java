package com.pojo;

import java.util.Date;

public class SelfRank {
    private Integer selfRankId;

    private Integer engineerRank;

    private Integer selfCategoryOne;

    private Integer selfCategoryTwo;

    private Integer selfCategoryThree;

    private Integer selfCategoryFour;

    private Integer selfCategoryFive;

    private Integer selfCategorySix;

    private Integer selfCategorySeven;

    private Integer selfCategoryEight;

    private Date createTime;

    private Date updateTime;

    public SelfRank(Integer selfRankId, Integer engineerRank, Integer selfCategoryOne, Integer selfCategoryTwo, Integer selfCategoryThree, Integer selfCategoryFour, Integer selfCategoryFive, Integer selfCategorySix, Integer selfCategorySeven, Integer selfCategoryEight, Date createTime, Date updateTime) {
        this.selfRankId = selfRankId;
        this.engineerRank = engineerRank;
        this.selfCategoryOne = selfCategoryOne;
        this.selfCategoryTwo = selfCategoryTwo;
        this.selfCategoryThree = selfCategoryThree;
        this.selfCategoryFour = selfCategoryFour;
        this.selfCategoryFive = selfCategoryFive;
        this.selfCategorySix = selfCategorySix;
        this.selfCategorySeven = selfCategorySeven;
        this.selfCategoryEight = selfCategoryEight;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public SelfRank() {
        super();
    }

    public Integer getSelfRankId() {
        return selfRankId;
    }

    public void setSelfRankId(Integer selfRankId) {
        this.selfRankId = selfRankId;
    }

    public Integer getEngineerRank() {
        return engineerRank;
    }

    public void setEngineerRank(Integer engineerRank) {
        this.engineerRank = engineerRank;
    }

    public Integer getSelfCategoryOne() {
        return selfCategoryOne;
    }

    public void setSelfCategoryOne(Integer selfCategoryOne) {
        this.selfCategoryOne = selfCategoryOne;
    }

    public Integer getSelfCategoryTwo() {
        return selfCategoryTwo;
    }

    public void setSelfCategoryTwo(Integer selfCategoryTwo) {
        this.selfCategoryTwo = selfCategoryTwo;
    }

    public Integer getSelfCategoryThree() {
        return selfCategoryThree;
    }

    public void setSelfCategoryThree(Integer selfCategoryThree) {
        this.selfCategoryThree = selfCategoryThree;
    }

    public Integer getSelfCategoryFour() {
        return selfCategoryFour;
    }

    public void setSelfCategoryFour(Integer selfCategoryFour) {
        this.selfCategoryFour = selfCategoryFour;
    }

    public Integer getSelfCategoryFive() {
        return selfCategoryFive;
    }

    public void setSelfCategoryFive(Integer selfCategoryFive) {
        this.selfCategoryFive = selfCategoryFive;
    }

    public Integer getSelfCategorySix() {
        return selfCategorySix;
    }

    public void setSelfCategorySix(Integer selfCategorySix) {
        this.selfCategorySix = selfCategorySix;
    }

    public Integer getSelfCategorySeven() {
        return selfCategorySeven;
    }

    public void setSelfCategorySeven(Integer selfCategorySeven) {
        this.selfCategorySeven = selfCategorySeven;
    }

    public Integer getSelfCategoryEight() {
        return selfCategoryEight;
    }

    public void setSelfCategoryEight(Integer selfCategoryEight) {
        this.selfCategoryEight = selfCategoryEight;
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