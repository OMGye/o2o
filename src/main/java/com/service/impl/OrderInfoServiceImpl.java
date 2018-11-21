package com.service.impl;

import com.common.Const;
import com.common.ServerResponse;
import com.common.Varible;
import com.dao.O2oPayInfoMapper;
import com.dao.OrderInfoMapper;
import com.dao.OtherParamInfoMapper;
import com.dao.PriceTogetherInfoMapper;
import com.pojo.*;
import com.service.BasicPriceInfoService;
import com.service.OrderInfoService;
import com.service.TimerCancleOrderService;
import com.util.BigDecimalUtil;
import com.util.DateTimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Timer;

/**
 * Created by upupgogogo on 2018/11/20.下午2:52
 */
@Service("orderInfoService")
public class OrderInfoServiceImpl implements OrderInfoService{

    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Autowired
    private BasicPriceInfoService basicPriceInfoService;

    @Autowired
    private OtherParamInfoMapper otherParamInfoMapper;

    @Autowired
    private PriceTogetherInfoMapper priceTogetherInfoMapper;

    @Autowired
    private O2oPayInfoMapper o2oPayInfoMapper;

    private static  final Logger logger = LoggerFactory.getLogger(OrderInfoServiceImpl.class);

    @Override
    public ServerResponse createOrder(OrderInfo order, CustomerInfo customerInfo, Integer params[], Integer rushId) {
        if (order.getOrderFirstCategory() == null || order.getOrderSecondCategory() == null ||
                order.getOrderMi() == null || order.getOrderQae() == null || order.getBasicLayer() == null || order.getPriceTogetherNum() == null )
            return ServerResponse.createByErrorMessage("参数不能为空");

        //1.得到CAM的基础价格
        BasicPriceInfo basicPriceInfo = new BasicPriceInfo();
        basicPriceInfo.setFirstCategory(order.getOrderFirstCategory());
        basicPriceInfo.setSecondRank(order.getOrderSecondCategory());
        basicPriceInfo.setBasicLayer(order.getBasicLayer());
        basicPriceInfo.setThirdCategory("CAM");
        ServerResponse response = basicPriceInfoService.getPrice(basicPriceInfo);
        BigDecimal bigDecimalCAM = (BigDecimal) response.getData();

        //2.得到MI的基础价格
        BigDecimal bigDecimalMI = null;
        if (order.getOrderMi() == 1){
            basicPriceInfo.setThirdCategory("MI");
            response = basicPriceInfoService.getPrice(basicPriceInfo);
             bigDecimalMI = (BigDecimal) response.getData();
        }

        //3.得到QAE的基础价格
        BigDecimal bigDecimalQAE = null;
        if (order.getOrderMi() == 1){
            basicPriceInfo.setThirdCategory("QAE");
            response = basicPriceInfoService.getPrice(basicPriceInfo);
            bigDecimalQAE = (BigDecimal) response.getData();
        }

        //4.得到总计带参的基础价格
        BigDecimal bigDecimalParam = BigDecimalUtil.add(bigDecimalCAM.doubleValue(),bigDecimalMI == null ? null : bigDecimalMI.doubleValue());
        bigDecimalParam = BigDecimalUtil.add(bigDecimalParam.doubleValue(),bigDecimalQAE
                == null ? null : bigDecimalQAE.doubleValue());
        String paramName = null;
        if (params != null && params.length > 0){
            BigDecimal param = new BigDecimal("0");
            for (Integer paramId : params){
                OtherParamInfo otherParamInfo = otherParamInfoMapper.selectByPrimaryKey(paramId);
                if (otherParamInfo != null){
                     paramName = (paramName == null ? "" : paramName + ",") + otherParamInfo.getParamName();
                     order.setOtherParamInfo(paramName);
                    param = BigDecimalUtil.add(param.doubleValue(),otherParamInfo.getParamPercentage().doubleValue());
                }
            }
            if (param.doubleValue() != 0)
                bigDecimalParam = BigDecimalUtil.add(bigDecimalParam.doubleValue(),BigDecimalUtil.mul(bigDecimalParam.doubleValue(),param.doubleValue()).doubleValue());
        }


        //5.得到每个基础价格拼款后的价格
        BigDecimal allCount = new BigDecimal("0");
        allCount = BigDecimalUtil.add(allCount.doubleValue(),bigDecimalParam.doubleValue());
        if (order.getPriceTogetherNum() > 1){
            PriceTogetherInfo priceTogetherInfo = new PriceTogetherInfo();
            priceTogetherInfo.setPriceTogetherNum(order.getPriceTogetherNum());
            priceTogetherInfo.setPriceTogetherName("CAM");
            PriceTogetherInfo curPriceTogetherInfo = priceTogetherInfoMapper.selectByPriceTogetherInfo(priceTogetherInfo);
            if (curPriceTogetherInfo != null){
                bigDecimalCAM = BigDecimalUtil.mul(bigDecimalCAM.doubleValue(),curPriceTogetherInfo.getPriceTogetherPercentage().doubleValue());
            }
            if (bigDecimalMI != null){
                priceTogetherInfo.setPriceTogetherName("MI");
                curPriceTogetherInfo = priceTogetherInfoMapper.selectByPriceTogetherInfo(priceTogetherInfo);
                if (curPriceTogetherInfo != null){
                    bigDecimalMI = BigDecimalUtil.mul(bigDecimalMI.doubleValue(),curPriceTogetherInfo.getPriceTogetherPercentage().doubleValue());
                }
            }

            if (bigDecimalQAE != null){
                priceTogetherInfo.setPriceTogetherName("QAE");
                curPriceTogetherInfo = priceTogetherInfoMapper.selectByPriceTogetherInfo(priceTogetherInfo);
                if (curPriceTogetherInfo != null){
                    bigDecimalQAE = BigDecimalUtil.mul(bigDecimalQAE.doubleValue(),curPriceTogetherInfo.getPriceTogetherPercentage().doubleValue());
                }
            }

            BigDecimal bigDecimalTogether = BigDecimalUtil.add(bigDecimalCAM.doubleValue(),bigDecimalMI == null ? null : bigDecimalMI.doubleValue());
            bigDecimalTogether = BigDecimalUtil.add(bigDecimalTogether.doubleValue(),bigDecimalQAE == null ? null : bigDecimalQAE.doubleValue());
            //6.得到总计的拼款价格与参数基本价格之和
            allCount = BigDecimalUtil.add(bigDecimalParam.doubleValue(),bigDecimalTogether.doubleValue());

        }

        //7.是否加急
        if (rushId != null){
            order.setOrderRush(1);
            OtherParamInfo otherParamInfo = otherParamInfoMapper.selectByPrimaryKey(rushId);
            if (otherParamInfo != null)
                allCount = BigDecimalUtil.add(allCount.doubleValue(), BigDecimalUtil.mul(allCount.doubleValue(),otherParamInfo.getParamPercentage().doubleValue()).doubleValue());
        }
        else
            order.setOrderRush(0);

        order.setCustomerId(customerInfo.getCustomerId());
        order.setCustomerName(customerInfo.getCustomerName());
        order.setOrderState(Const.Order.PAYING);
        order.setOrderPrice(allCount);

        int row = orderInfoMapper.insert(order);
        if (row > 0){
            Timer timer = new Timer();
            TimerCancleOrderServiceImpl timerCancleOrderService = new TimerCancleOrderServiceImpl();
            Varible.UN_PAY_CHAGE_ORDERID = order.getOrderId();
            timer.schedule(timerCancleOrderService,Const.TIMER);
            return ServerResponse.createBySuccess("下单成功");

        }

        return ServerResponse.createByErrorMessage("下单失败");
    }

    @Override
    public ServerResponse getOrderById(Integer orderId) {
        if (orderId == null)
            return ServerResponse.createByErrorMessage("参数为空");
        OrderInfo orderInfo = orderInfoMapper.selectByPrimaryKey(orderId);
        return ServerResponse.createBySuccess(orderInfo);
    }

    @Override
    public ServerResponse aliCallback(Map<String,String> params){
        try {
            Integer orderId = Integer.parseInt(params.get("out_trade_no"));
            String tradeNo = params.get("trade_no");
            String tradeStatus = params.get("trade_status");
            OrderInfo order = orderInfoMapper.selectByPrimaryKey(orderId);
            if(order == null){
                logger.error("非o2o的订单,回调忽略");
                return ServerResponse.createByErrorMessage("非o2o的订单,回调忽略");
            }
            if(order.getOrderState() >= Const.Order.PAIED){
                logger.error("支付宝重复调用");
                return ServerResponse.createBySuccess("支付宝重复调用");
            }
            if(Const.AlipayCallback.TRADE_STATUS_TRADE_SUCCESS.equals(tradeStatus)){
                order.setOrderState(Const.Order.PAIED);
                orderInfoMapper.updateByPrimaryKeySelective(order);
            }
            O2oPayInfo payInfo = new O2oPayInfo();
            payInfo.setUserId(order.getCustomerId());
            payInfo.setOrderNo((long) order.getOrderId());
            payInfo.setPayPlatform(Const.PayPlatformEnum.ALIPAY.getCode());
            payInfo.setPlatformNumber(tradeNo);
            payInfo.setPlatformStatus(tradeStatus);

            o2oPayInfoMapper.insert(payInfo);
        }catch (Exception e){
            logger.error("支付宝验证回调异常",e);
        }

        return ServerResponse.createBySuccess();
    }



}
