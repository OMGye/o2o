package com.vo;

/**
 * Created by upupgogogo on 2018/11/27.下午2:15
 */
public class EngineerDefriendJson {

    private Integer engineerId;

    private String engineerName;

    public EngineerDefriendJson() {
    }

    public EngineerDefriendJson(Integer engineerId, String engineerName) {

        this.engineerId = engineerId;
        this.engineerName = engineerName;
    }

    public void setEngineerId(Integer engineerId) {

        this.engineerId = engineerId;
    }

    public void setEngineerName(String engineerName) {
        this.engineerName = engineerName;
    }

    public Integer getEngineerId() {

        return engineerId;
    }

    public String getEngineerName() {
        return engineerName;
    }
}
