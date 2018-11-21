package com.common;

/**
 * Created by upupgogogo on 2018/7/29.下午10:28
 */
public class Const {

    public static final String CURRENT_USER = "currentUser";

    public static final Long TIMER = (long)60000;


    public interface EngineerRankInfo{
        int CAN = 1;
        int CANNOT = 0;
    }

    public interface EngineerInfo{
        byte ABLE = 1;
        byte UNABLE = 0;
        byte BAN = 10;
    }

    public interface Order{
        int PAYING = 0;
        int PAIED = 1;
        int CANNCEL = 10;
    }


    public interface  AlipayCallback{
        String TRADE_STATUS_WAIT_BUYER_PAY = "WAIT_BUYER_PAY";
        String TRADE_STATUS_TRADE_SUCCESS = "TRADE_SUCCESS";

        String RESPONSE_SUCCESS = "success";
        String RESPONSE_FAILED = "failed";
    }

    public enum PayPlatformEnum{
        ALIPAY(1,"支付宝");

        PayPlatformEnum(int code,String value){
            this.code = code;
            this.value = value;
        }
        private String value;
        private int code;

        public String getValue() {
            return value;
        }

        public int getCode() {
            return code;
        }
    }

    public interface CustomerInfo{

        byte ABLE = 1;
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
