package com.common;

/**
 * Created by upupgogogo on 2018/7/29.下午10:28
 */
public class Const {

    public static final String CURRENT_USER = "currentUser";


    public interface EngineerRankInfo{
        int CAN = 1;
        int CANNOT = 0;
    }

    public interface EngineerInfo{
        byte ABLE = 1;
        byte UNABLE = 0;
        byte BAN = 10;
    }

    public interface User{
        int ACTIVATE = 1;
        int UNACTIVATE = 0;
    }

    public interface Item{
        int WORKING = 0;
        int FINISHED = 1;
    }


    public interface RecordConst{
        int UNCHECK = 0;
        int FIRST_CHECK = 1;
        int Last_CHECK = 2;

        int RECORD_REFUSE = 10;
    }

}
