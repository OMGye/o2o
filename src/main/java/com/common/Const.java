package com.common;

/**
 * Created by upupgogogo on 2018/7/29.下午10:28
 */
public class Const {

    public static final String CURRENT_USER = "currentUser";

    public static final String CURRENT_RANK = "currentRank";

    public static final Long TIMER = (long)604800000;

    public static final Long TIMER_FOR_SEND_EMAIL = (long)3000;


    public interface AdminCheck{
        int UNCHECK = 0;
        int CHECKING = 1;
        int CHECK = 2;
    }


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
        int HAVE_CAUGHT = 2;
        int HAVE_UPLOAD_FILE = 3;
        int QAE_HAVE_CAUGHT = 4;
        int CHECK = 5;
        int HAVE_REIVER_ORDER = 6;
        int HAVE_FINISHED = 7;
        int DEDUCT = 8;  //客户选择扣款后的状态
        int ENGINEER_COMFIRM = 9;
        int COMPLAIN = 10;
        int CANNCEL = 11;
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


    public interface Draw{

        int UN_DEAL = 0;
        int DEAL = 1;
    }

    public interface AdminRank{
        int BOSS = 0;
        int MANAGER = 1;
        int EMPLOY = 2;
    }


}
