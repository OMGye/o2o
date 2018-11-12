package com.pojo;

import java.util.Date;

public class EngineerRankInfo {
    private Integer engineerRankId;

    private Integer engineerRank;

    private Integer engineerRankA;

    private Integer engineerRankB;

    private Integer engineerRankC;

    private Integer engineerRankD;

    private Integer engineerRankCam;

    private Integer engineerRankMi;

    private Integer engineerRankQae;

    private Date createTime;

    private Date updateTime;

    public EngineerRankInfo(Integer engineerRankId, Integer engineerRank, Integer engineerRankA, Integer engineerRankB, Integer engineerRankC, Integer engineerRankD, Integer engineerRankCam, Integer engineerRankMi, Integer engineerRankQae, Date createTime, Date updateTime) {
        this.engineerRankId = engineerRankId;
        this.engineerRank = engineerRank;
        this.engineerRankA = engineerRankA;
        this.engineerRankB = engineerRankB;
        this.engineerRankC = engineerRankC;
        this.engineerRankD = engineerRankD;
        this.engineerRankCam = engineerRankCam;
        this.engineerRankMi = engineerRankMi;
        this.engineerRankQae = engineerRankQae;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public EngineerRankInfo() {
        super();
    }

    public Integer getEngineerRankId() {
        return engineerRankId;
    }

    public void setEngineerRankId(Integer engineerRankId) {
        this.engineerRankId = engineerRankId;
    }

    public Integer getEngineerRank() {
        return engineerRank;
    }

    public void setEngineerRank(Integer engineerRank) {
        this.engineerRank = engineerRank;
    }

    public Integer getEngineerRankA() {
        return engineerRankA;
    }

    public void setEngineerRankA(Integer engineerRankA) {
        this.engineerRankA = engineerRankA;
    }

    public Integer getEngineerRankB() {
        return engineerRankB;
    }

    public void setEngineerRankB(Integer engineerRankB) {
        this.engineerRankB = engineerRankB;
    }

    public Integer getEngineerRankC() {
        return engineerRankC;
    }

    public void setEngineerRankC(Integer engineerRankC) {
        this.engineerRankC = engineerRankC;
    }

    public Integer getEngineerRankD() {
        return engineerRankD;
    }

    public void setEngineerRankD(Integer engineerRankD) {
        this.engineerRankD = engineerRankD;
    }

    public Integer getEngineerRankCam() {
        return engineerRankCam;
    }

    public void setEngineerRankCam(Integer engineerRankCam) {
        this.engineerRankCam = engineerRankCam;
    }

    public Integer getEngineerRankMi() {
        return engineerRankMi;
    }

    public void setEngineerRankMi(Integer engineerRankMi) {
        this.engineerRankMi = engineerRankMi;
    }

    public Integer getEngineerRankQae() {
        return engineerRankQae;
    }

    public void setEngineerRankQae(Integer engineerRankQae) {
        this.engineerRankQae = engineerRankQae;
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