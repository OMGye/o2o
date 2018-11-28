package com.service.impl;

import com.common.Const;
import com.common.ServerResponse;
import com.common.Varible;
import com.dao.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.pojo.*;
import com.service.BasicPriceInfoService;
import com.service.OrderInfoService;
import com.util.*;
import com.vo.EngineerDefriendJson;
import com.vo.EngineerRankVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

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
    private CustomerInfoMapper customerInfoMapper;

    @Autowired
    private O2oPayInfoMapper o2oPayInfoMapper;

    @Autowired
    private EngineerInfoMapper engineerInfoMapper;

    @Autowired
    private QuantityInfoMapper quantityInfoMapper;



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

        if (bigDecimalCAM == null)
            return ServerResponse.createByErrorMessage("CAM基础价格为空,下单失败");

        //2.得到MI的基础价格
        BigDecimal bigDecimalMI = null;
        if (order.getOrderMi() == 1){
            basicPriceInfo.setThirdCategory("MI");
            response = basicPriceInfoService.getPrice(basicPriceInfo);
            bigDecimalMI = (BigDecimal) response.getData();
            if (bigDecimalMI == null)
                return ServerResponse.createByErrorMessage("MI基础价格为空,下单失败");
        }

        //3.得到QAE的基础价格
        BigDecimal bigDecimalQAE = null;
        if (order.getOrderMi() == 1){
            basicPriceInfo.setThirdCategory("QAE");
            response = basicPriceInfoService.getPrice(basicPriceInfo);
            bigDecimalQAE = (BigDecimal) response.getData();
            if (bigDecimalQAE == null)
                return ServerResponse.createByErrorMessage("QAE基础价格为空,下单失败");
        }

        //得到总的基础价格，在计算带参价格
        BigDecimal bigDecimalParam = BigDecimalUtil.add(0,bigDecimalCAM.doubleValue());

        if (bigDecimalMI != null)
            bigDecimalParam = BigDecimalUtil.add(bigDecimalParam.doubleValue(),bigDecimalMI.doubleValue());

        if (bigDecimalQAE != null)
            bigDecimalParam = BigDecimalUtil.add(bigDecimalParam.doubleValue(),bigDecimalQAE.doubleValue());

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
            if (curPriceTogetherInfo == null)
                return ServerResponse.createByErrorMessage("CAM对应拼款层数找不到");
            if (curPriceTogetherInfo != null){
                bigDecimalCAM = BigDecimalUtil.mul(bigDecimalCAM.doubleValue(),curPriceTogetherInfo.getPriceTogetherPercentage().doubleValue());
            }
            if (bigDecimalMI != null){
                priceTogetherInfo.setPriceTogetherName("MI");
                curPriceTogetherInfo = priceTogetherInfoMapper.selectByPriceTogetherInfo(priceTogetherInfo);
                if (curPriceTogetherInfo == null)
                    return ServerResponse.createByErrorMessage("MI对应拼款层数找不到");
                if (curPriceTogetherInfo != null){
                    bigDecimalMI = BigDecimalUtil.mul(bigDecimalMI.doubleValue(),curPriceTogetherInfo.getPriceTogetherPercentage().doubleValue());
                }
            }

            if (bigDecimalQAE != null){
                priceTogetherInfo.setPriceTogetherName("QAE");
                curPriceTogetherInfo = priceTogetherInfoMapper.selectByPriceTogetherInfo(priceTogetherInfo);
                if (curPriceTogetherInfo == null)
                    return ServerResponse.createByErrorMessage("QAE对应拼款层数找不到");
                if (curPriceTogetherInfo != null){
                    bigDecimalQAE = BigDecimalUtil.mul(bigDecimalQAE.doubleValue(),curPriceTogetherInfo.getPriceTogetherPercentage().doubleValue());
                }
            }

            BigDecimal bigDecimalTogether = BigDecimalUtil.add(0,bigDecimalCAM.doubleValue());
            if (bigDecimalMI != null)
                bigDecimalTogether = BigDecimalUtil.add(bigDecimalTogether.doubleValue(),bigDecimalMI.doubleValue());

            if (bigDecimalQAE != null)
                bigDecimalTogether = BigDecimalUtil.add(bigDecimalTogether.doubleValue(),bigDecimalQAE.doubleValue());

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
            TimerChangePayOrder changePayOrder = new TimerChangePayOrder(order.getOrderId(),orderInfoMapper);
            timer.schedule(changePayOrder,Const.TIMER);
            return ServerResponse.createBySuccess("下单成功",order.getOrderId());

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
                logger.debug("非o2o的订单,回调忽略");
                return ServerResponse.createByErrorMessage("非o2o的订单,回调忽略");
            }
            if(order.getOrderState() >= Const.Order.PAIED){
                logger.debug("支付宝重复调用");
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
            logger.debug("支付宝验证回调异常",e);
        }

        return ServerResponse.createBySuccess();
    }

    @Override
    public ServerResponse<PageInfo> customerList(int pageSize, int pageNum,CustomerInfo customerInfo, Integer orderState) {
        PageHelper.startPage(pageNum,pageSize);
        PageHelper.orderBy("update_time desc");
        List<OrderInfo> list = orderInfoMapper.customerListOrder(customerInfo.getCustomerId(),orderState);
        PageInfo pageInfo = new PageInfo(list);
        return ServerResponse.createBySuccess(pageInfo);
    }

    @Override
    public ServerResponse<PageInfo> engineerCaughtList(int pageSize, int pageNum, EngineerRankVO engineerRankVO) {
        PageHelper.startPage(pageNum,pageSize);
        PageHelper.orderBy("update_time desc");
        List<OrderInfo> list = orderInfoMapper.engineerCaughtList(engineerRankVO.getFirstCategory(),engineerRankVO.getSecondCategories(),engineerRankVO.getMI() == 1 ? null : 0,Const.Order.PAIED);
        PageInfo pageInfo = new PageInfo(list);
        return ServerResponse.createBySuccess(pageInfo);
    }

    @Override
    public ServerResponse caughtCamOrder(Integer orderId, EngineerRankVO engineerRankVO, EngineerInfo engineerInfo) {
        if (orderId == null)
            return ServerResponse.createByErrorMessage("订单号为空");

        OrderInfo orderInfo = orderInfoMapper.selectByPrimaryKey(orderId);
        if (orderInfo == null)
            return ServerResponse.createByErrorMessage("找不到该订单");

        if (orderInfo.getOrderState() != Const.Order.PAIED)
            return ServerResponse.createByErrorMessage("该订单状态不可接");

        CustomerInfo customerInfo = customerInfoMapper.selectByPrimaryKey
                (orderInfo.getCustomerId());
        if (customerInfo.getEngineerDefriend() != null){
            List<EngineerDefriendJson> list = JsonUtil.toJsonList(customerInfo.getEngineerDefriend());
            for (EngineerDefriendJson engineerDefriendJson : list){
                if (engineerDefriendJson.getEngineerId().intValue() == engineerInfo.getEngineerId())
                    return ServerResponse.createByErrorMessage("您已被该客户拉黑");
            }
        }

        EngineerInfo dbEngineerInfo = engineerInfoMapper.selectByPrimaryKey(engineerInfo.getEngineerId());
        if (dbEngineerInfo != null){
            if (dbEngineerInfo.getOrderCount() == 3)
                return ServerResponse.createByErrorMessage("您当前可接订单数已满");
        }

        if (orderInfo.getOrderFirstCategory().equals(engineerRankVO.getFirstCategory()) &&
                orderInfo.getOrderMi() <= engineerRankVO.getMI()) {
            for (String str : engineerRankVO.getSecondCategories()) {
                if (str.equals(orderInfo.getOrderSecondCategory())) {
                    orderInfo.setEngineerId(dbEngineerInfo.getEngineerId());
                    orderInfo.setEngineerName(dbEngineerInfo.getEngineerName());
                    orderInfo.setOrderState(Const.Order.HAVE_CAUGHT);
                    int row = orderInfoMapper.updateByPrimaryKeySelective(orderInfo);
                    if (row > 0) {
                        Timer timer = new Timer();
                        TimerEmailCaughtOrder caughtOrder = new TimerEmailCaughtOrder(customerInfo.getEmail(),"您的订单已被接单");
                        timer.schedule(caughtOrder,Const.TIMER_FOR_SEND_EMAIL);

                        engineerInfo.setOrderCount(dbEngineerInfo.getOrderCount() + 1);
                        engineerInfoMapper.updateByPrimaryKeySelective(engineerInfo);
                        return ServerResponse.createBySuccess("接单成功");
                    }
                    return ServerResponse.createByErrorMessage("接单失败");
                }
            }
        }
        return ServerResponse.createByErrorMessage("该订单等级");
    }

    @Override
    public ServerResponse orderUploadFile(Integer orderId, EngineerInfo engineerInfo, MultipartFile file, String path) {
        if (orderId == null && file == null)
            return ServerResponse.createByErrorMessage("参数不能为空");

        OrderInfo orderInfo = orderInfoMapper.selectByPrimaryKey(orderId);
        if (orderInfo == null && orderInfo.getEngineerId().intValue() != engineerInfo.getEngineerId())
            return ServerResponse.createByErrorMessage("找不到该订单");

        if (orderInfo.getOrderState() != Const.Order.HAVE_CAUGHT)
            return ServerResponse.createByErrorMessage("该订单状态不可上传文件");

        if (file != null) {
            String fileName = file.getOriginalFilename();
            //扩展名
            //abc.jpg
            String fileExtensionName = fileName.substring(fileName.lastIndexOf(".") + 1);
            String uploadFileName = UUID.randomUUID().toString() + "." + fileExtensionName;
            logger.info("开始上传文件,上传文件的文件名:{},上传的路径:{},新文件名:{}", fileName, path, uploadFileName);

            File fileDir = new File(path);
            if (!fileDir.exists()) {
                fileDir.setWritable(true);
                fileDir.mkdirs();
            }
            File targetFile = new File(path, uploadFileName);


            try {
                file.transferTo(targetFile);
                //文件已经上传成功了

                FTPUtil.uploadFile(Lists.newArrayList(targetFile));
                //已经上传到ftp服务器上

                targetFile.delete();
            } catch (IOException e) {
                logger.error("上传文件异常", e);
                return ServerResponse.createByErrorMessage("上传文件异常");
            }
            orderInfo.setOrderFile(PropertiesUtil.getProperty("ftp.server.http.prefix") + targetFile.getName());
        }
        orderInfo.setOrderState(Const.Order.HAVE_UPLOAD_FILE);
        int row = orderInfoMapper.updateByPrimaryKeySelective(orderInfo);
        if (row <= 0)
            return ServerResponse.createByErrorMessage("上传失败");
        if (orderInfo.getOrderQae() == 0){
            CustomerInfo customerInfo = customerInfoMapper.selectByPrimaryKey
                    (orderInfo.getCustomerId());
            Timer timer = new Timer();
            TimerEmailCaughtOrder caughtOrder = new TimerEmailCaughtOrder(customerInfo.getEmail(),"您的订单已被工程师完成");
            timer.schedule(caughtOrder,Const.TIMER_FOR_SEND_EMAIL);
        }
        return ServerResponse.createBySuccess("上传成功");
    }


    @Override
    public ServerResponse receiveOrder(Integer orderId, CustomerInfo customerInfo) {
        if (orderId == null)
            return ServerResponse.createByErrorMessage("参数不能为空");

        OrderInfo orderInfo = orderInfoMapper.selectByPrimaryKey(orderId);
        if (orderInfo != null && orderInfo.getOrderState() == Const.Order.HAVE_UPLOAD_FILE &&
                orderInfo.getOrderQae() == 0 && orderInfo.getCustomerId().intValue() ==  customerInfo.getCustomerId().intValue()){
            orderInfo.setOrderState(Const.Order.HAVE_REIVER_ORDER);
            int row = orderInfoMapper.updateByPrimaryKeySelective(orderInfo);
            if (row > 0){
                EngineerInfo engineerInfo = engineerInfoMapper.selectByPrimaryKey
                        (orderInfo.getEngineerId());
                Timer timer = new Timer();
                TimerEmailCaughtOrder caughtOrder = new TimerEmailCaughtOrder(customerInfo.getEmail(),"您完成的订单已被客户查看，如果三天之内没有确认，系统将直接帮您确认，并将资金直接转入您的账户");
                timer.schedule(caughtOrder,Const.TIMER_FOR_SEND_EMAIL);

                List<QuantityInfo> list = quantityInfoMapper.selectByQuantiy(engineerInfo.getEngineerQuantity());
                QuantityInfo maxQuantity = list.get(0);
                Timer newTimer = new Timer();
                TimerChangeOrderFinished timerChangeOrderFinished = new TimerChangeOrderFinished(orderId,orderInfoMapper, engineerInfoMapper,customerInfoMapper,null, maxQuantity.getQuantityDraw());
                newTimer.schedule(timerChangeOrderFinished,Const.TIMER);

                return ServerResponse.createBySuccess("确认下载附件成功");
            }

        }

        return ServerResponse.createByErrorMessage("确认下载附件失败");
    }

    @Override
    public ServerResponse refuseToEnigneer(String refuseDec, Integer orderId, Integer customerId) {
        if (refuseDec == null && orderId == null)
            return ServerResponse.createByErrorMessage("参数为空");

        OrderInfo orderInfo = orderInfoMapper.selectByPrimaryKey(orderId);
        if (orderInfo == null && orderInfo.getCustomerId().intValue() != customerId)
            return ServerResponse.createByErrorMessage("找不到该订单或者该订单不属于该客户");

        if (orderInfo.getOrderState() != Const.Order.HAVE_REIVER_ORDER)
            return ServerResponse.createByErrorMessage("该订单状态不可拒绝");

        orderInfo.setRefuseDec(orderInfo.getRefuseDec() == null ? refuseDec : orderInfo.getRefuseDec() + "---" +refuseDec);
        orderInfo.setOrderState(Const.Order.HAVE_CAUGHT);
        int row = orderInfoMapper.updateByPrimaryKeySelective(orderInfo);
        if (row > 0){
            EngineerInfo engineerInfo  = engineerInfoMapper.selectByPrimaryKey(orderInfo.getEngineerId());
            Timer timer = new Timer();
            TimerEmailCaughtOrder caughtOrder = new TimerEmailCaughtOrder(engineerInfo.getEmail(),"您的订单已被客户拒绝,拒绝原因：" + refuseDec);
            timer.schedule(caughtOrder,Const.TIMER_FOR_SEND_EMAIL);

            return ServerResponse.createBySuccess("拒绝成功");
        }

        return ServerResponse.createByErrorMessage("拒绝失败");
    }

    @Override
    public ServerResponse comfirmOrder(Integer orderId, Integer customerId) {
        if (orderId == null)
            return ServerResponse.createByErrorMessage("参数为空");

        OrderInfo orderInfo = orderInfoMapper.selectByPrimaryKey(orderId);
        if (orderInfo == null)
            return ServerResponse.createByErrorMessage("找不到该订单");

        if (orderInfo.getOrderState() != Const.Order.HAVE_REIVER_ORDER)
            return ServerResponse.createByErrorMessage("当前订单状态不可确认");

        if (orderInfo.getOrderQae() == 0){
            orderInfo.setOrderState(Const.Order.HAVE_FINISHED);
            int row = orderInfoMapper.updateByPrimaryKeySelective(orderInfo);
            if (row > 0) {
                EngineerInfo engineerInfo = engineerInfoMapper.selectByPrimaryKey(orderInfo.getEngineerId());
                List<QuantityInfo> list = quantityInfoMapper.selectByQuantiy(engineerInfo.getEngineerQuantity());
                QuantityInfo maxQuantity = list.get(0);
                BigDecimal orderPrice = BigDecimalUtil.mul(orderInfo.getOrderPrice().doubleValue(), maxQuantity.getQuantityDraw().doubleValue());
                orderPrice = BigDecimalUtil.sub(orderInfo.getOrderPrice().doubleValue(), orderPrice.doubleValue());
                engineerInfo.setEngineerBalance(BigDecimalUtil.add(engineerInfo.getEngineerBalance().doubleValue(), orderPrice.doubleValue()));
                engineerInfo.setOrderCount(engineerInfo.getOrderCount() - 1);
                engineerInfoMapper.updateByPrimaryKey(engineerInfo);

                CustomerInfo customerInfo = customerInfoMapper.selectByPrimaryKey(orderInfo.getCustomerId());
                customerInfo.setOrderCount(customerInfo.getOrderCount() + 1);
                customerInfoMapper.updateByPrimaryKeySelective(customerInfo);

                Timer timer = new Timer();
                TimerEmailCaughtOrder caughtOrder = new TimerEmailCaughtOrder(engineerInfo.getEmail(),"您的订单已被客户确认，金额已经转入您的账户");
                timer.schedule(caughtOrder,Const.TIMER_FOR_SEND_EMAIL);
                return ServerResponse.createBySuccess("确认成功");
            }
        }
        return ServerResponse.createByErrorMessage("确认失败");
    }

    @Override
    public ServerResponse engineerList(int pageSize, int pageNum, EngineerInfo engineerInfo, Integer orderState) {
        PageHelper.startPage(pageNum,pageSize);
        PageHelper.orderBy("update_time desc");
        List<OrderInfo> list = orderInfoMapper.engineerListOrder(engineerInfo.getEngineerId(),orderState);
        PageInfo pageInfo = new PageInfo(list);
        return ServerResponse.createBySuccess(pageInfo);
    }

    @Override
    public ServerResponse orderDeduct(Integer orderId, Integer priceDeductId) {
        return null;
    }

    @Override
    public ServerResponse<PageInfo> engineerQaeCaughtList(int pageSize, int pageNum, EngineerRankVO engineerRankVO) {
        PageHelper.startPage(pageNum,pageSize);
        PageHelper.orderBy("update_time desc");
        List<OrderInfo> list = orderInfoMapper.engineerCaughtList(engineerRankVO.getFirstCategory(),engineerRankVO.getSecondCategories(),engineerRankVO.getMI() == 1 ? null : 0,Const.Order.PAIED);
        PageInfo pageInfo = new PageInfo(list);
        return ServerResponse.createBySuccess(pageInfo);
    }

}
