package com.util;

import com.common.Const;
import com.common.ServerResponse;
import com.pojo.OrderInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.TimerTask;

/**
 * Created by upupgogogo on 2018/11/26.下午3:41
 */
public class TimerEmailCaughtOrder extends TimerTask {

    private Logger logger = LoggerFactory.getLogger(TimerEmailCaughtOrder.class);

    private String email;

    private String msg;

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {

        return msg;
    }

    public TimerEmailCaughtOrder() {
    }

    public TimerEmailCaughtOrder(String email, String msg) {
        this.email = email;
        this.msg = msg;
    }

    public void setEmail(String email) {

        this.email = email;
    }

    public String getEmail() {

        return email;
    }

    @Override
    public void run() {
        try {
            MailUtil.sendMail(email, "您的订单已被接单");
        } catch (Exception e) {
            logger.debug("邮件发送失败");
        }
    }
}
