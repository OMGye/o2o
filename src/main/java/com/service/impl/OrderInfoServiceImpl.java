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
import com.vo.AdminAllPrice;
import com.vo.EngineerDefriendJson;
import com.vo.EngineerRankVO;
import com.vo.OrderAndMessagenumVo;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.*;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by upupgogogo on 2018/11/20.下午2:52
 */
@Service("orderInfoService")
public class OrderInfoServiceImpl implements OrderInfoService {

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
    private PayInfoMapper payInfoMapper;

    @Autowired
    private EngineerInfoMapper engineerInfoMapper;

    @Autowired
    private QuantityInfoMapper quantityInfoMapper;

    @Autowired
    private PriceDeductInfoMapper priceDeductInfoMapper;

    @Autowired
    private BillInfoMapper billInfoMapper;

    @Autowired
    private IncomeInfoMapper incomeInfoMapper;

    @Autowired
    private OrderAnsqueInfoMapper orderAnsqueInfoMapper;

    @Autowired
    private DrawCashInfoMapper drawCashInfoMapper;


    private static final Logger logger = LoggerFactory.getLogger(OrderInfoServiceImpl.class);

    @Override
    public ServerResponse createOrder(OrderInfo order, CustomerInfo customerInfo, Integer params[], Integer rushId, MultipartFile file, String path) {
        if (order.getOrderFirstCategory() == null || order.getOrderSecondCategory() == null ||
                order.getOrderMi() == null || order.getOrderQae() == null || order.getBasicLayer() == null || order.getPriceTogetherNum() == null || file == null)
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
        if (order.getOrderMi() == 1) {
            basicPriceInfo.setThirdCategory("MI");
            response = basicPriceInfoService.getPrice(basicPriceInfo);
            bigDecimalMI = (BigDecimal) response.getData();
            if (bigDecimalMI == null)
                return ServerResponse.createByErrorMessage("MI基础价格为空,下单失败");
        }

        BigDecimal orderQaePrice = new BigDecimal("0");
        BigDecimal orderQaePriceParam = new BigDecimal("0");
        BigDecimal orderQaePricetogether = new BigDecimal("0");
        //3.得到QAE的基础价格
        BigDecimal bigDecimalQAE = null;
        if (order.getOrderQae() == 1) {
            basicPriceInfo.setThirdCategory("QAE");
            response = basicPriceInfoService.getPrice(basicPriceInfo);
            bigDecimalQAE = (BigDecimal) response.getData();
            orderQaePrice = BigDecimalUtil.add(orderQaePrice.doubleValue(), bigDecimalQAE.doubleValue());
            if (bigDecimalQAE == null)
                return ServerResponse.createByErrorMessage("QAE基础价格为空,下单失败");
        }

        //得到总的基础价格，在计算带参价格
        BigDecimal bigDecimalParam = BigDecimalUtil.add(0, bigDecimalCAM.doubleValue());

        if (bigDecimalMI != null)
            bigDecimalParam = BigDecimalUtil.add(bigDecimalParam.doubleValue(), bigDecimalMI.doubleValue());

        if (bigDecimalQAE != null)
            bigDecimalParam = BigDecimalUtil.add(bigDecimalParam.doubleValue(), bigDecimalQAE.doubleValue());

        String paramName = null;
        if (params != null && params.length > 0) {
            BigDecimal param = new BigDecimal("0");
            for (Integer paramId : params) {
                OtherParamInfo otherParamInfo = otherParamInfoMapper.selectByPrimaryKey(paramId);
                if (otherParamInfo != null) {
                    paramName = (paramName == null ? "" : paramName + ",") + otherParamInfo.getParamName();
                    order.setOtherParamInfo(paramName);
                    param = BigDecimalUtil.add(param.doubleValue(), otherParamInfo.getParamPercentage().doubleValue());
                }
            }
            if (param.doubleValue() != 0) {
                bigDecimalParam = BigDecimalUtil.add(bigDecimalParam.doubleValue(), BigDecimalUtil.mul(bigDecimalParam.doubleValue(), param.doubleValue()).doubleValue());
                orderQaePriceParam = BigDecimalUtil.mul(orderQaePrice.doubleValue(), param.doubleValue());
            }
        }


        //5.得到每个基础价格拼款后的价格
        BigDecimal allCount = new BigDecimal("0");
        allCount = BigDecimalUtil.add(allCount.doubleValue(), bigDecimalParam.doubleValue());
        if (order.getPriceTogetherNum() >= 1) {
            PriceTogetherInfo priceTogetherInfo = new PriceTogetherInfo();
            priceTogetherInfo.setPriceTogetherNum(order.getPriceTogetherNum());
            priceTogetherInfo.setPriceTogetherName("CAM");
            PriceTogetherInfo curPriceTogetherInfo = priceTogetherInfoMapper.selectByPriceTogetherInfo(priceTogetherInfo);
            if (curPriceTogetherInfo == null)
                return ServerResponse.createByErrorMessage("CAM对应拼款层数找不到");
            if (curPriceTogetherInfo != null) {
                bigDecimalCAM = BigDecimalUtil.mul(bigDecimalCAM.doubleValue(), curPriceTogetherInfo.getPriceTogetherPercentage().doubleValue());
            }
            if (bigDecimalMI != null) {
                priceTogetherInfo.setPriceTogetherName("MI");
                curPriceTogetherInfo = priceTogetherInfoMapper.selectByPriceTogetherInfo(priceTogetherInfo);
                if (curPriceTogetherInfo == null)
                    return ServerResponse.createByErrorMessage("MI对应拼款层数找不到");
                if (curPriceTogetherInfo != null) {
                    bigDecimalMI = BigDecimalUtil.mul(bigDecimalMI.doubleValue(), curPriceTogetherInfo.getPriceTogetherPercentage().doubleValue());
                }
            }

            if (bigDecimalQAE != null) {
                priceTogetherInfo.setPriceTogetherName("QAE");
                curPriceTogetherInfo = priceTogetherInfoMapper.selectByPriceTogetherInfo(priceTogetherInfo);
                if (curPriceTogetherInfo == null)
                    return ServerResponse.createByErrorMessage("QAE对应拼款层数找不到");
                if (curPriceTogetherInfo != null) {
                    bigDecimalQAE = BigDecimalUtil.mul(bigDecimalQAE.doubleValue(), curPriceTogetherInfo.getPriceTogetherPercentage().doubleValue());

                    orderQaePricetogether = BigDecimalUtil.mul(orderQaePrice.doubleValue(), curPriceTogetherInfo.getPriceTogetherPercentage().doubleValue());
                }
            }

            orderQaePrice = BigDecimalUtil.add(orderQaePrice.doubleValue(), orderQaePriceParam.doubleValue());
            orderQaePrice = BigDecimalUtil.add(orderQaePrice.doubleValue(), orderQaePricetogether.doubleValue());
            BigDecimal bigDecimalTogether = BigDecimalUtil.add(0, bigDecimalCAM.doubleValue());
            if (bigDecimalMI != null)
                bigDecimalTogether = BigDecimalUtil.add(bigDecimalTogether.doubleValue(), bigDecimalMI.doubleValue());

            if (bigDecimalQAE != null)
                bigDecimalTogether = BigDecimalUtil.add(bigDecimalTogether.doubleValue(), bigDecimalQAE.doubleValue());

            //6.得到总计的拼款价格与参数基本价格之和
            allCount = BigDecimalUtil.add(bigDecimalParam.doubleValue(), bigDecimalTogether.doubleValue());

        }

        //7.是否加急
        if (rushId != null) {
            order.setOrderRush(1);
            OtherParamInfo otherParamInfo = otherParamInfoMapper.selectByPrimaryKey(rushId);
            if (otherParamInfo != null) {
                allCount = BigDecimalUtil.add(allCount.doubleValue(), BigDecimalUtil.mul(allCount.doubleValue(), otherParamInfo.getParamPercentage().doubleValue()).doubleValue());
                orderQaePrice = BigDecimalUtil.add(orderQaePrice.doubleValue(), BigDecimalUtil.mul(orderQaePrice.doubleValue(), otherParamInfo.getParamPercentage().doubleValue()).doubleValue());
            }
        } else
            order.setOrderRush(0);

        order.setOrderQaePrice(orderQaePrice);
        order.setCustomerId(customerInfo.getCustomerId());
        order.setCustomerName(customerInfo.getCustomerName());
        order.setOrderState(Const.Order.PAYING);
        order.setOrderPrice(allCount);

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
        order.setOrderCustomerFile(PropertiesUtil.getProperty("ftp.server.http.prefix") + targetFile.getName());


        int row = orderInfoMapper.insert(order);
        if (row > 0) {
            Timer timer = new Timer();
            TimerChangePayOrder changePayOrder = new TimerChangePayOrder(order.getOrderId(), orderInfoMapper);
            timer.schedule(changePayOrder, Const.TIMER);
            return ServerResponse.createBySuccess("下单成功", order.getOrderId());

        }

        return ServerResponse.createByErrorMessage("下单失败");
    }

    @Override
    public ServerResponse payForBalance(Integer orderId, Integer customerId){
        if (orderId == null)
            return ServerResponse.createByErrorMessage("订单号为空");

        OrderInfo orderInfo = orderInfoMapper.selectByPrimaryKey(orderId);
        if (orderInfo == null)
            return ServerResponse.createByErrorMessage("找不到该订单");

        if (orderInfo.getOrderState() != Const.Order.PAYING)
            return ServerResponse.createByErrorMessage("该订单状态不可支付");

        CustomerInfo customerInfo = customerInfoMapper.selectByPrimaryKey
                (customerId);
        if (orderInfo.getOrderPrice().doubleValue() > customerInfo.getCustomerBalance().doubleValue()){
            return ServerResponse.createByErrorMessage("余额不足");
        }

        customerInfo.setCustomerBalance(BigDecimalUtil.sub(customerInfo.getCustomerBalance().doubleValue(), orderInfo.getOrderPrice().doubleValue()));
        customerInfoMapper.updateByPrimaryKeySelective(customerInfo);

        orderInfo.setOrderState(Const.Order.PAIED);
        orderInfoMapper.updateByPrimaryKeySelective(orderInfo);

        BillInfo billInfo = new BillInfo();
        billInfo.setUserId(customerInfo.getCustomerId());
        billInfo.setBillMoney(BigDecimalUtil.sub(orderInfo.getOrderPrice().doubleValue(), BigDecimalUtil.add(orderInfo.getOrderPrice().doubleValue(), orderInfo.getOrderPrice().doubleValue()).doubleValue()));
        billInfo.setUserName(customerInfo.getCustomerName());
        billInfo.setUserType(0);
        billInfo.setBillDec("支付订单 :" + orderInfo.getOrderId());
        billInfoMapper.insert(billInfo);

        return ServerResponse.createBySuccess("支付成功");
    }

    @Override
    public ServerResponse getOrderById(Integer orderId) {
        if (orderId == null)
            return ServerResponse.createByErrorMessage("参数为空");
        OrderInfo orderInfo = orderInfoMapper.selectByPrimaryKey(orderId);
        return ServerResponse.createBySuccess(orderInfo);
    }

    @Override
    public ServerResponse aliCallback(Map<String, String> params) {
        try {
            Integer orderId = Integer.parseInt(params.get("out_trade_no"));
            String tradeNo = params.get("trade_no");
            String tradeStatus = params.get("trade_status");
            OrderInfo order = orderInfoMapper.selectByPrimaryKey(orderId);
            if (order == null) {
                logger.debug("非o2o的订单,回调忽略");
                return ServerResponse.createByErrorMessage("非o2o的订单,回调忽略");
            }
            if (order.getOrderState() >= Const.Order.PAIED) {
                logger.debug("支付宝重复调用");
                return ServerResponse.createByErrorMessage("支付宝重复调用");
            }
            if (Const.AlipayCallback.TRADE_STATUS_TRADE_SUCCESS.equals(tradeStatus)) {
                order.setOrderState(Const.Order.PAIED);
                orderInfoMapper.updateByPrimaryKeySelective(order);
            }

            CustomerInfo customerInfo = customerInfoMapper.selectByPrimaryKey(order.getCustomerId());

            PayInfo payInfo = new PayInfo();
            payInfo.setUserId(order.getCustomerId());
            payInfo.setOrderNo((long) order.getOrderId());
            payInfo.setPayPlatform(Const.PayPlatformEnum.ALIPAY.getCode());
            payInfo.setPlatformNumber(tradeNo);
            payInfo.setPlatformStatus(tradeStatus);
            payInfo.setUserName(customerInfo.getCustomerName());
            payInfo.setPrice(order.getOrderPrice());
            payInfoMapper.insert(payInfo);

            BillInfo payBillInfo = new BillInfo();
            payBillInfo.setUserId(customerInfo.getCustomerId());
            payBillInfo.setBillMoney(order.getOrderPrice());
            payBillInfo.setUserName(customerInfo.getCustomerName());
            payBillInfo.setUserType(0);
            payBillInfo.setBillDec("充值订单 :" + order.getOrderId());
            billInfoMapper.insert(payBillInfo);

            BillInfo billInfo = new BillInfo();
            billInfo.setUserId(customerInfo.getCustomerId());
            billInfo.setBillMoney(BigDecimalUtil.sub(order.getOrderPrice().doubleValue(), BigDecimalUtil.add(order.getOrderPrice().doubleValue(), order.getOrderPrice().doubleValue()).doubleValue()));
            billInfo.setUserName(customerInfo.getCustomerName());
            billInfo.setUserType(0);
            billInfo.setBillDec("支付订单 :" + order.getOrderId());
            billInfoMapper.insert(billInfo);
        } catch (Exception e) {
            logger.debug("支付宝验证回调异常", e);
        }

        return ServerResponse.createBySuccess();
    }

    @Override
    public ServerResponse aliCallbackBalance(Map<String, String> params) {
        try {
            String order = params.get("out_trade_no");
            Integer customerId = Integer.parseInt(order.substring(0,order.indexOf("-")));
            String tradeNo = params.get("trade_no");
            String tradeStatus = params.get("trade_status");
            BigDecimal price = new BigDecimal(params.get("total_amount"));

            if (!Const.AlipayCallback.TRADE_STATUS_TRADE_SUCCESS.equals(tradeStatus)) {
                return ServerResponse.createByError();
            }

            CustomerInfo customerInfo = customerInfoMapper.selectByPrimaryKey(customerId);
            customerInfo.setCustomerBalance(BigDecimalUtil.add(customerInfo.getCustomerBalance().doubleValue(), price.doubleValue()));
            customerInfoMapper.updateByPrimaryKeySelective(customerInfo);

            PayInfo payInfo = new PayInfo();
            payInfo.setUserId(customerId);
            payInfo.setPayPlatform(Const.PayPlatformEnum.ALIPAY.getCode());
            payInfo.setPlatformNumber(tradeNo);
            payInfo.setPlatformStatus(tradeStatus);
            payInfo.setUserName(customerInfo.getCustomerName());
            payInfo.setPrice(price);
            payInfoMapper.insert(payInfo);

            BillInfo payBillInfo = new BillInfo();
            payBillInfo.setUserId(customerInfo.getCustomerId());
            payBillInfo.setBillMoney(price);
            payBillInfo.setUserName(customerInfo.getCustomerName());
            payBillInfo.setUserType(0);
            payBillInfo.setBillDec("充值金额");
            billInfoMapper.insert(payBillInfo);

        } catch (Exception e) {
            logger.debug("支付宝验证回调异常", e);
        }

        return ServerResponse.createBySuccess();
    }

    @Override
    public ServerResponse<PageInfo> customerList(int pageSize, int pageNum, CustomerInfo customerInfo, Integer orderState) {
        PageHelper.startPage(pageNum, pageSize);
        PageHelper.orderBy("update_time desc");
        List<OrderInfo> list = orderInfoMapper.customerListOrder(customerInfo.getCustomerId(), orderState);
        List<OrderAndMessagenumVo> listVo = new ArrayList<>();
        for (OrderInfo orderInfo : list){
            OrderAndMessagenumVo orderAndMessagenumVo = new OrderAndMessagenumVo();
            BeanUtils.copyProperties(orderInfo,orderAndMessagenumVo);
            Integer num = orderAnsqueInfoMapper.getUnReadNum(orderInfo.getOrderId(), 0);
            orderAndMessagenumVo.setUnReadMessage(num);
            listVo.add(orderAndMessagenumVo);
        }
        PageInfo pageInfo = new PageInfo(list);
        pageInfo.setList(listVo);
        return ServerResponse.createBySuccess(pageInfo);
    }

    @Override
    public ServerResponse<PageInfo> engineerCaughtList(int pageSize, int pageNum, EngineerRankVO engineerRankVO) {
        PageHelper.startPage(pageNum, pageSize);
        PageHelper.orderBy("update_time desc");
        List<OrderInfo> list = orderInfoMapper.engineerCaughtList(engineerRankVO.getFirstCategory(), engineerRankVO.getSecondCategories(), engineerRankVO.getMI() == 1 ? null : 0, Const.Order.PAIED);
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
        if (customerInfo.getEngineerDefriend() != null) {
            List<EngineerDefriendJson> list = JsonUtil.toJsonList(customerInfo.getEngineerDefriend());
            for (EngineerDefriendJson engineerDefriendJson : list) {
                if (engineerDefriendJson.getEngineerId().intValue() == engineerInfo.getEngineerId())
                    return ServerResponse.createByErrorMessage("您已被该客户拉黑");
            }
        }

        EngineerInfo dbEngineerInfo = engineerInfoMapper.selectByPrimaryKey(engineerInfo.getEngineerId());
        if (dbEngineerInfo != null) {
            if (dbEngineerInfo.getOrderCount() >= 1){
                List<OrderInfo> list = orderInfoMapper.engineerListOrder(dbEngineerInfo.getEngineerId(), Const.Order.HAVE_CAUGHT);
                if (list.size() >= 1){
                    return ServerResponse.createByErrorMessage("您当前可接订单数已满");
                }
            }
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
                        TimerEmailCaughtOrder caughtOrder = new TimerEmailCaughtOrder(customerInfo.getEmail(), "您的订单已被接单");
                        timer.schedule(caughtOrder, Const.TIMER_FOR_SEND_EMAIL);

                        dbEngineerInfo.setOrderCount(dbEngineerInfo.getOrderCount() + 1);
                        engineerInfoMapper.updateByPrimaryKeySelective(dbEngineerInfo);
                        return ServerResponse.createBySuccess("接单成功");
                    }
                    return ServerResponse.createByErrorMessage("接单失败");
                }
            }
        }
        return ServerResponse.createByErrorMessage("您不满足该订单等级");
    }

    @Override
    public ServerResponse orderUploadFile(Integer orderId, EngineerInfo engineerInfo, MultipartFile file, String path) {
        if (orderId == null || file == null)
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

        if (orderInfo.getEngineerCheckId() != null) {
            orderInfo.setOrderState(Const.Order.QAE_HAVE_CAUGHT);
        } else {
            orderInfo.setOrderState(Const.Order.HAVE_UPLOAD_FILE);
        }
        int row = orderInfoMapper.updateByPrimaryKeySelective(orderInfo);
        if (row <= 0)
            return ServerResponse.createByErrorMessage("上传失败");

        if (orderInfo.getOrderQae() == 0) {
            CustomerInfo customerInfo = customerInfoMapper.selectByPrimaryKey
                    (orderInfo.getCustomerId());
            Timer timer = new Timer();
            TimerEmailCaughtOrder caughtOrder = new TimerEmailCaughtOrder(customerInfo.getEmail(), "您的订单已被工程师完成");
            timer.schedule(caughtOrder, Const.TIMER_FOR_SEND_EMAIL);
        }
        if (orderInfo.getOrderQae() == 1 && orderInfo.getEngineerCheckId() == null) {
            CustomerInfo customerInfo = customerInfoMapper.selectByPrimaryKey
                    (orderInfo.getCustomerId());
            Timer timer = new Timer();
            TimerEmailCaughtOrder caughtOrder = new TimerEmailCaughtOrder(customerInfo.getEmail(), "您的订单已被工程师完成,等待审核工程师接单审核");
            timer.schedule(caughtOrder, Const.TIMER_FOR_SEND_EMAIL);
        }

        if (orderInfo.getOrderQae() == 1 && orderInfo.getEngineerCheckId() != null) {
            CustomerInfo customerInfo = customerInfoMapper.selectByPrimaryKey
                    (orderInfo.getCustomerId());
            Timer timer = new Timer();
            TimerEmailCaughtOrder caughtOrder = new TimerEmailCaughtOrder(customerInfo.getEmail(), "您的订单已被工程师完成,等待审核工程师审核");
            timer.schedule(caughtOrder, Const.TIMER_FOR_SEND_EMAIL);
        }

        return ServerResponse.createBySuccess("上传成功");
    }


    @Override
    public ServerResponse receiveOrder(Integer orderId, CustomerInfo customerInfo) {
        if (orderId == null)
            return ServerResponse.createByErrorMessage("参数不能为空");

        OrderInfo orderInfo = orderInfoMapper.selectByPrimaryKey(orderId);
        if (orderInfo != null && orderInfo.getCustomerId().intValue() == customerInfo.getCustomerId().intValue()) {
            if ((orderInfo.getOrderState() == Const.Order.HAVE_UPLOAD_FILE && orderInfo.getOrderQae() == 0) || (orderInfo.getOrderState() == Const.Order.CHECK && orderInfo.getOrderQae() == 1)) {
                orderInfo.setOrderState(Const.Order.HAVE_REIVER_ORDER);
                int row = orderInfoMapper.updateByPrimaryKeySelective(orderInfo);
                if (row > 0) {
                    EngineerInfo engineerInfo = engineerInfoMapper.selectByPrimaryKey
                            (orderInfo.getEngineerId());
                    Timer timer = new Timer();
                    TimerEmailCaughtOrder caughtOrder = new TimerEmailCaughtOrder(customerInfo.getEmail(), "您完成的订单已被客户查看，如果七天之内没有确认，系统将直接帮您确认，并将资金直接转入您的账户");
                    timer.schedule(caughtOrder, Const.TIMER_FOR_SEND_EMAIL);

                    List<QuantityInfo> list = quantityInfoMapper.selectByQuantiy(engineerInfo.getEngineerQuantity());
                    QuantityInfo maxQuantity = list.get(0);

                    BigDecimal drawEnginner = null;
                    if (orderInfo.getEngineerCheckId() != null) {
                        EngineerInfo qaeEngineerInfo = engineerInfoMapper.selectByPrimaryKey
                                (orderInfo.getEngineerId());
                        Timer timerQae = new Timer();
                        TimerEmailCaughtOrder caughtOrderQae = new TimerEmailCaughtOrder(customerInfo.getEmail(), "您完成的订单已被客户查看，如果七天之内没有确认，系统将直接帮您确认，并将资金直接转入您的账户");
                        timerQae.schedule(caughtOrderQae, Const.TIMER_FOR_SEND_EMAIL);
                        List<QuantityInfo> listQae = quantityInfoMapper.selectByQuantiy(qaeEngineerInfo.getEngineerQuantity());
                        QuantityInfo qaeQuantity = listQae.get(0);
                        drawEnginner = qaeQuantity.getQuantityDraw();
                    }
                    Timer newTimer = new Timer();
                    TimerChangeOrderFinished timerChangeOrderFinished = new TimerChangeOrderFinished(orderId, orderInfoMapper, engineerInfoMapper, customerInfoMapper, drawEnginner, maxQuantity.getQuantityDraw(), billInfoMapper, incomeInfoMapper);
                    newTimer.schedule(timerChangeOrderFinished, Const.TIMER);

                    return ServerResponse.createBySuccess("确认下载附件成功");
                }
            }
        }

        return ServerResponse.createByErrorMessage("确认下载附件失败");
    }

    @Override
    public ServerResponse refuseToEnigneer(String refuseDec, Integer orderId, Integer customerId) {
        if (refuseDec == null || orderId == null)
            return ServerResponse.createByErrorMessage("参数为空");

        OrderInfo orderInfo = orderInfoMapper.selectByPrimaryKey(orderId);
        if (orderInfo == null && orderInfo.getCustomerId().intValue() != customerId)
            return ServerResponse.createByErrorMessage("找不到该订单或者该订单不属于该客户");

        if (orderInfo.getOrderState() != Const.Order.HAVE_REIVER_ORDER)
            return ServerResponse.createByErrorMessage("该订单状态不可拒绝");

        orderInfo.setRefuseDec(orderInfo.getRefuseDec() == null ? refuseDec : orderInfo.getRefuseDec() + "---" + refuseDec);
        orderInfo.setOrderState(Const.Order.HAVE_CAUGHT);
        int row = orderInfoMapper.updateByPrimaryKeySelective(orderInfo);
        if (row > 0) {
            EngineerInfo engineerInfo = engineerInfoMapper.selectByPrimaryKey(orderInfo.getEngineerId());
            Timer timer = new Timer();
            TimerEmailCaughtOrder caughtOrder = new TimerEmailCaughtOrder(engineerInfo.getEmail(), "您的订单已被客户拒绝,拒绝原因：" + refuseDec);
            timer.schedule(caughtOrder, Const.TIMER_FOR_SEND_EMAIL);

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

        if (orderInfo.getOrderQae() == 0) {
            orderInfo.setOrderState(Const.Order.HAVE_FINISHED);

            EngineerInfo engineerInfo = engineerInfoMapper.selectByPrimaryKey(orderInfo.getEngineerId());
            List<QuantityInfo> list = quantityInfoMapper.selectByQuantiy(engineerInfo.getEngineerQuantity());
            QuantityInfo maxQuantity = list.get(0);
            BigDecimal orderCamPrice = BigDecimalUtil.sub(orderInfo.getOrderPrice().doubleValue(), orderInfo.getOrderQaePrice().doubleValue());
            orderCamPrice = BigDecimalUtil.sub(orderCamPrice.doubleValue(), BigDecimalUtil.mul(orderCamPrice.doubleValue(), maxQuantity.getQuantityDraw().doubleValue()).doubleValue());
            engineerInfo.setEngineerBalance(BigDecimalUtil.add(orderCamPrice.doubleValue(), engineerInfo.getEngineerBalance().doubleValue()));
            engineerInfo.setOrderCount(engineerInfo.getOrderCount() - 1);
            engineerInfoMapper.updateByPrimaryKey(engineerInfo);

            //客户完成的订单加1
            CustomerInfo customerInfo = customerInfoMapper.selectByPrimaryKey(orderInfo.getCustomerId());
            customerInfo.setOrderCount(customerInfo.getOrderCount() + 1);
            customerInfoMapper.updateByPrimaryKeySelective(customerInfo);

            //个人账单
            BillInfo billInfo = new BillInfo();
            billInfo.setUserId(engineerInfo.getEngineerId());
            billInfo.setBillMoney(orderCamPrice);
            billInfo.setUserName(engineerInfo.getEngineerName());
            billInfo.setUserType(1);
            billInfo.setBillDec("订单收入 :" + orderInfo.getOrderId());
            billInfoMapper.insert(billInfo);

            //系统收入
            IncomeInfo incomeInfo = new IncomeInfo();
            incomeInfo.setOrderId(orderId);
            incomeInfo.setIncomeMoney(BigDecimalUtil.sub(orderInfo.getOrderPrice().doubleValue(), orderCamPrice.doubleValue()));
            incomeInfoMapper.insert(incomeInfo);

            orderInfo.setEngineerRealPrice(orderCamPrice);
            orderInfo.setEngineerQaeRealPrice(new BigDecimal(0));
            orderInfo.setAdminPrice(BigDecimalUtil.sub(orderInfo.getOrderPrice().doubleValue(), orderCamPrice.doubleValue()));
            orderInfoMapper.updateByPrimaryKeySelective(orderInfo);

            Timer timer = new Timer();
            TimerEmailCaughtOrder caughtOrder = new TimerEmailCaughtOrder(engineerInfo.getEmail(), "您的订单已被客户确认，金额已经转入您的账户");
            timer.schedule(caughtOrder, Const.TIMER_FOR_SEND_EMAIL);
            return ServerResponse.createBySuccess("确认成功");

        }
        if (orderInfo.getOrderQae() == 1) {
            orderInfo.setOrderState(Const.Order.HAVE_FINISHED);

            CustomerInfo customerInfo = customerInfoMapper.selectByPrimaryKey(orderInfo.getCustomerId());
            customerInfo.setOrderCount(customerInfo.getOrderCount() + 1);
            customerInfoMapper.updateByPrimaryKeySelective(customerInfo);


            EngineerInfo engineerInfo = engineerInfoMapper.selectByPrimaryKey(orderInfo.getEngineerId());
            List<QuantityInfo> list = quantityInfoMapper.selectByQuantiy(engineerInfo.getEngineerQuantity());
            QuantityInfo maxQuantity = list.get(0);
            BigDecimal orderCamPrice = BigDecimalUtil.sub(orderInfo.getOrderPrice().doubleValue(), orderInfo.getOrderQaePrice().doubleValue());
            orderCamPrice = BigDecimalUtil.sub(orderCamPrice.doubleValue(), BigDecimalUtil.mul(orderCamPrice.doubleValue(), maxQuantity.getQuantityDraw().doubleValue()).doubleValue());
            engineerInfo.setEngineerBalance(BigDecimalUtil.add(orderCamPrice.doubleValue(), engineerInfo.getEngineerBalance().doubleValue()));
            engineerInfo.setOrderCount(engineerInfo.getOrderCount() - 1);
            engineerInfoMapper.updateByPrimaryKey(engineerInfo);

            BillInfo billInfo = new BillInfo();
            billInfo.setUserId(engineerInfo.getEngineerId());
            billInfo.setBillMoney(orderCamPrice);
            billInfo.setUserName(engineerInfo.getEngineerName());
            billInfo.setUserType(1);
            billInfo.setBillDec("订单收入 :" + orderInfo.getOrderId());
            billInfoMapper.insert(billInfo);

            EngineerInfo qaeEngineerInfo = engineerInfoMapper.selectByPrimaryKey(orderInfo.getEngineerCheckId());
            List<QuantityInfo> listQae = quantityInfoMapper.selectByQuantiy(qaeEngineerInfo.getEngineerQuantity());
            QuantityInfo maxQuantityQae = listQae.get(0);
            BigDecimal orderQaePrice = orderInfo.getOrderQaePrice();
            orderQaePrice = BigDecimalUtil.sub(orderQaePrice.doubleValue(), BigDecimalUtil.mul(orderQaePrice.doubleValue(), maxQuantityQae.getQuantityDraw().doubleValue()).doubleValue());
            qaeEngineerInfo.setEngineerBalance(BigDecimalUtil.add(qaeEngineerInfo.getEngineerBalance().doubleValue(), orderQaePrice.doubleValue()));
            qaeEngineerInfo.setOrderCount(qaeEngineerInfo.getOrderCount() - 1);
            engineerInfoMapper.updateByPrimaryKey(qaeEngineerInfo);

            BillInfo billInfoQae = new BillInfo();
            billInfoQae.setUserId(qaeEngineerInfo.getEngineerId());
            billInfoQae.setBillMoney(orderQaePrice);
            billInfoQae.setUserName(qaeEngineerInfo.getEngineerName());
            billInfoQae.setUserType(1);
            billInfoQae.setBillDec("审核订单收入 :" + orderInfo.getOrderId());
            billInfoMapper.insert(billInfoQae);

            BigDecimal engineerAllPrice = BigDecimalUtil.add(orderQaePrice.doubleValue(), orderCamPrice.doubleValue());
            IncomeInfo incomeInfo = new IncomeInfo();
            incomeInfo.setOrderId(orderId);
            incomeInfo.setIncomeMoney(BigDecimalUtil.sub(orderInfo.getOrderPrice().doubleValue(), engineerAllPrice.doubleValue()));
            incomeInfoMapper.insert(incomeInfo);

            orderInfo.setAdminPrice(BigDecimalUtil.sub(orderInfo.getOrderPrice().doubleValue(), engineerAllPrice.doubleValue()));
            orderInfo.setEngineerRealPrice(orderCamPrice);
            orderInfo.setEngineerQaeRealPrice(orderQaePrice);
            orderInfoMapper.updateByPrimaryKeySelective(orderInfo);

            Timer timer = new Timer();
            TimerEmailCaughtOrder caughtOrder = new TimerEmailCaughtOrder(engineerInfo.getEmail(), "您的订单已被客户确认，金额已经转入您的账户");
            timer.schedule(caughtOrder, Const.TIMER_FOR_SEND_EMAIL);

            Timer timerQae = new Timer();
            TimerEmailCaughtOrder caughtOrderQae = new TimerEmailCaughtOrder(engineerInfo.getEmail(), "您的订单已被客户确认，金额已经转入您的账户");
            timerQae.schedule(caughtOrderQae, Const.TIMER_FOR_SEND_EMAIL);
            return ServerResponse.createBySuccess("确认成功");

        }
        return ServerResponse.createByErrorMessage("确认失败");
    }

    @Override
    public ServerResponse engineerList(int pageSize, int pageNum, EngineerInfo engineerInfo, Integer orderState) {
        PageHelper.startPage(pageNum, pageSize);
        PageHelper.orderBy("update_time desc");
        List<OrderInfo> list = orderInfoMapper.engineerListOrder(engineerInfo.getEngineerId(), orderState);
        List<OrderAndMessagenumVo> listVo = new ArrayList<>();
        for (OrderInfo orderInfo : list){
            OrderAndMessagenumVo orderAndMessagenumVo = new OrderAndMessagenumVo();
            BeanUtils.copyProperties(orderInfo,orderAndMessagenumVo);
            Integer num = orderAnsqueInfoMapper.getUnReadNum(orderInfo.getOrderId(), 1);
            orderAndMessagenumVo.setUnReadMessage(num);
            listVo.add(orderAndMessagenumVo);
        }
        PageInfo pageInfo = new PageInfo(list);
        pageInfo.setList(listVo);
        return ServerResponse.createBySuccess(pageInfo);
    }

    @Override
    public ServerResponse engineerQaeList(int pageSize, int pageNum, EngineerInfo engineerInfo, Integer orderState) {
        PageHelper.startPage(pageNum, pageSize);
        PageHelper.orderBy("update_time desc");
        List<OrderInfo> list = orderInfoMapper.engineerQaeListOrder(engineerInfo.getEngineerId(), orderState);
        List<OrderAndMessagenumVo> listVo = new ArrayList<>();
        for (OrderInfo orderInfo : list){
            OrderAndMessagenumVo orderAndMessagenumVo = new OrderAndMessagenumVo();
            BeanUtils.copyProperties(orderInfo,orderAndMessagenumVo);
            Integer num = orderAnsqueInfoMapper.getUnReadNum(orderInfo.getOrderId(), 2);
            orderAndMessagenumVo.setUnReadMessage(num);
            listVo.add(orderAndMessagenumVo);
        }
        PageInfo pageInfo = new PageInfo(list);
        pageInfo.setList(listVo);
        return ServerResponse.createBySuccess(pageInfo);
    }


    @Override
    public ServerResponse orderDeduct(Integer orderId, Integer priceDeductId, String orderDeductDec, CustomerInfo customerInfo) {
        if (orderId == null || priceDeductId == null || orderDeductDec == null)
            return ServerResponse.createByErrorMessage("参数不能为空");

        OrderInfo orderInfo = orderInfoMapper.selectByPrimaryKey(orderId);
        if (orderInfo == null)
            return ServerResponse.createByErrorMessage("找不到该订单");

        PriceDeductInfo priceDeductInfo = priceDeductInfoMapper.selectByPrimaryKey(priceDeductId);
        if (priceDeductInfo == null)
            return ServerResponse.createByErrorMessage("找不到扣款等级");

        if (customerInfo.getCustomerId().intValue() == orderInfo.getCustomerId().intValue() && orderInfo.getOrderState() == Const.Order.HAVE_REIVER_ORDER) {
            orderInfo.setOrderState(Const.Order.DEDUCT);
            orderInfo.setOrderDeductRank(priceDeductInfo.getPriceDeductRank());
            orderInfo.setOrderDeductDec(orderDeductDec);
            int row = orderInfoMapper.updateByPrimaryKeySelective(orderInfo);
            if (row > 0) {
                EngineerInfo engineerInfo = engineerInfoMapper.selectByPrimaryKey(orderInfo.getEngineerId());
                Timer timer = new Timer();
                TimerEmailCaughtOrder caughtOrder = new TimerEmailCaughtOrder(engineerInfo.getEmail(), "您的订单被客户扣款，请您速去确认，如有问题可以点击投诉");
                timer.schedule(caughtOrder, Const.TIMER_FOR_SEND_EMAIL);

                return ServerResponse.createBySuccess("选择扣款成功");
            }
        }
        return ServerResponse.createByErrorMessage("选择扣款失败");
    }

    @Override
    public ServerResponse<PageInfo> engineerQaeCaughtList(int pageSize, int pageNum, EngineerRankVO engineerRankVO) {
        PageHelper.startPage(pageNum, pageSize);
        PageHelper.orderBy("update_time desc");
        List<OrderInfo> list = orderInfoMapper.engineerQaeCaughtList(engineerRankVO.getFirstCategory(), engineerRankVO.getSecondCategories(), engineerRankVO.getQAE(), Const.Order.HAVE_UPLOAD_FILE);
        PageInfo pageInfo = new PageInfo(list);
        return ServerResponse.createBySuccess(pageInfo);
    }

    @Override
    public ServerResponse caughtQaeOrder(Integer orderId, EngineerRankVO engineerRankVO, EngineerInfo engineerInfo) {
        if (orderId == null)
            return ServerResponse.createByErrorMessage("订单号为空");

        OrderInfo orderInfo = orderInfoMapper.selectByPrimaryKey(orderId);
        if (orderInfo == null)
            return ServerResponse.createByErrorMessage("找不到该订单");

        if (orderInfo.getOrderState() != Const.Order.HAVE_UPLOAD_FILE)
            return ServerResponse.createByErrorMessage("该订单状态不可接");

        if (orderInfo.getEngineerId().intValue() == engineerInfo.getEngineerId())
            return ServerResponse.createByErrorMessage("不能接自己制作的订单");

        CustomerInfo customerInfo = customerInfoMapper.selectByPrimaryKey
                (orderInfo.getCustomerId());
        if (customerInfo.getEngineerDefriend() != null) {
            List<EngineerDefriendJson> list = JsonUtil.toJsonList(customerInfo.getEngineerDefriend());
            for (EngineerDefriendJson engineerDefriendJson : list) {
                if (engineerDefriendJson.getEngineerId().intValue() == engineerInfo.getEngineerId())
                    return ServerResponse.createByErrorMessage("您已被该客户拉黑");
            }
        }

        EngineerInfo dbEngineerInfo = engineerInfoMapper.selectByPrimaryKey(engineerInfo.getEngineerId());
        if (dbEngineerInfo != null) {
            if (dbEngineerInfo.getOrderCount() >= 1){
                List<OrderInfo> list = orderInfoMapper.engineerQaeListOrder(dbEngineerInfo.getEngineerId(), Const.Order.QAE_HAVE_CAUGHT);
                if (list.size() >= 1){
                    return ServerResponse.createByErrorMessage("您当前可接订单数已满");
                }
            }
        }

        if (orderInfo.getOrderFirstCategory().equals(engineerRankVO.getFirstCategory()) &&
                orderInfo.getOrderQae() == engineerRankVO.getQAE()) {
            for (String str : engineerRankVO.getSecondCategories()) {
                if (str.equals(orderInfo.getOrderSecondCategory())) {
                    orderInfo.setEngineerCheckId(dbEngineerInfo.getEngineerId());
                    orderInfo.setEngineerCheckName(dbEngineerInfo.getEngineerName());
                    orderInfo.setOrderState(Const.Order.QAE_HAVE_CAUGHT);
                    int row = orderInfoMapper.updateByPrimaryKeySelective(orderInfo);
                    if (row > 0) {
                        Timer timer = new Timer();
                        TimerEmailCaughtOrder caughtOrder = new TimerEmailCaughtOrder(customerInfo.getEmail(), "您的订单已被审核工程师接单");
                        timer.schedule(caughtOrder, Const.TIMER_FOR_SEND_EMAIL);

                        engineerInfo.setOrderCount(dbEngineerInfo.getOrderCount() + 1);
                        engineerInfoMapper.updateByPrimaryKeySelective(engineerInfo);
                        return ServerResponse.createBySuccess("接单成功");
                    }
                    return ServerResponse.createByErrorMessage("接单失败");
                }
            }
        }
        return ServerResponse.createByErrorMessage("您不满足该订单等级");
    }

    @Override
    public ServerResponse qaeCheck(Integer orderId, Integer state, String refuseDec, EngineerInfo engineerInfo) {
        if (state == 0 && refuseDec != null && orderId != null) {
            OrderInfo orderInfo = orderInfoMapper.selectByPrimaryKey(orderId);
            if (orderInfo == null)
                return ServerResponse.createByErrorMessage("找不到该订单");

            if (orderInfo.getOrderState() != Const.Order.QAE_HAVE_CAUGHT)
                return ServerResponse.createByErrorMessage("当前订单状态不可拒绝");

            if (orderInfo.getEngineerCheckId() != null && engineerInfo.getEngineerId().intValue() == orderInfo.getEngineerCheckId()) {
                orderInfo.setOrderState(Const.Order.HAVE_CAUGHT);
                orderInfo.setRefuseDec(orderInfo.getRefuseDec() == null ? refuseDec : orderInfo.getRefuseDec() + "---" + refuseDec);
                int row = orderInfoMapper.updateByPrimaryKeySelective(orderInfo);
                if (row > 0) {
                    EngineerInfo dbengineerInfo = engineerInfoMapper.selectByPrimaryKey(orderInfo.getEngineerId());
                    Timer timer = new Timer();
                    TimerEmailCaughtOrder caughtOrder = new TimerEmailCaughtOrder(dbengineerInfo.getEmail(), "您的订单已被审核工程师拒绝,拒绝原因：" + refuseDec);
                    timer.schedule(caughtOrder, Const.TIMER_FOR_SEND_EMAIL);

                    return ServerResponse.createBySuccess("拒绝成功");
                }
                return ServerResponse.createByErrorMessage("拒绝失败");
            }
        }

        if (state == 1 && orderId != null) {
            OrderInfo orderInfo = orderInfoMapper.selectByPrimaryKey(orderId);
            if (orderInfo == null)
                return ServerResponse.createByErrorMessage("找不到该订单");

            if (orderInfo.getOrderState() != Const.Order.QAE_HAVE_CAUGHT)
                return ServerResponse.createByErrorMessage("当前订单状态不可通过");

            if (orderInfo.getEngineerCheckId() != null && engineerInfo.getEngineerId().intValue() == orderInfo.getEngineerCheckId()) {
                orderInfo.setOrderState(Const.Order.CHECK);
                int row = orderInfoMapper.updateByPrimaryKeySelective(orderInfo);
                if (row > 0) {
                    CustomerInfo customerInfo = customerInfoMapper.selectByPrimaryKey(orderInfo.getCustomerId());
                    Timer timer = new Timer();
                    TimerEmailCaughtOrder caughtOrder = new TimerEmailCaughtOrder(customerInfo.getEmail(), "您的订单已被审核工程师通过");
                    timer.schedule(caughtOrder, Const.TIMER_FOR_SEND_EMAIL);

                    return ServerResponse.createBySuccess("通过成功");
                }
                return ServerResponse.createByErrorMessage("通过失败");
            }
        }

        return ServerResponse.createByErrorMessage("参数异常");
    }

    @Override
    public ServerResponse customerCancleOrder(Integer orderId, CustomerInfo customerInfo) {
        if (orderId == null)
            return ServerResponse.createByErrorMessage("参数为空");

        OrderInfo orderInfo = orderInfoMapper.selectByPrimaryKey(orderId);
        if (orderInfo == null)
            return ServerResponse.createByErrorMessage("找不到该订单");

        if (orderInfo.getOrderState() == Const.Order.PAIED && customerInfo.getCustomerId().intValue() == orderInfo.getCustomerId().intValue()) {
            orderInfo.setOrderState(Const.Order.CANNCEL);
            int row = orderInfoMapper.updateByPrimaryKeySelective(orderInfo);
            if (row > 0) {
                CustomerInfo dbCustomerInfo = customerInfoMapper.selectByPrimaryKey(orderInfo.getCustomerId());
                dbCustomerInfo.setCustomerBalance(BigDecimalUtil.add(dbCustomerInfo.getCustomerBalance().doubleValue(), orderInfo.getOrderPrice().doubleValue()));
                customerInfoMapper.updateByPrimaryKeySelective(dbCustomerInfo);

                BillInfo billInfo = new BillInfo();
                billInfo.setUserId(customerInfo.getCustomerId());
                billInfo.setBillMoney(orderInfo.getOrderPrice());
                billInfo.setUserName(customerInfo.getCustomerName());
                billInfo.setUserType(0);
                billInfo.setBillDec("取消订单收入 :" + orderInfo.getOrderId());
                billInfoMapper.insert(billInfo);

                return ServerResponse.createBySuccess("取消成功");
            }
        }
        return ServerResponse.createByErrorMessage("取消失败");
    }

    @Override
    public ServerResponse engineerCancleOrder(Integer orderId, EngineerInfo engineerInfo) {
        if (orderId == null)
            return ServerResponse.createByErrorMessage("参数为空");

        OrderInfo orderInfo = orderInfoMapper.selectByPrimaryKey(orderId);
        if (orderInfo == null)
            return ServerResponse.createByErrorMessage("找不到该订单");

        if (orderInfo.getEngineerId().intValue() == engineerInfo.getEngineerId().intValue()
                && orderInfo.getOrderState() == Const.Order.HAVE_CAUGHT) {
            EngineerInfo dbEngineer = engineerInfoMapper.selectByPrimaryKey(orderInfo.getEngineerId());
            dbEngineer.setOrderCount(dbEngineer.getOrderCount() - 1);
            dbEngineer.setEngineerQuantity(BigDecimalUtil.sub(dbEngineer.getEngineerQuantity().doubleValue(), new BigDecimal("0.01").doubleValue()));
            engineerInfoMapper.updateByPrimaryKeySelective(dbEngineer);

            CustomerInfo customerInfo = customerInfoMapper.selectByPrimaryKey(orderInfo.getCustomerId());
            Timer timer = new Timer();
            TimerEmailCaughtOrder caughtOrder = new TimerEmailCaughtOrder(customerInfo.getEmail(), "您的订单已被工程师取消，等待下次接单");
            timer.schedule(caughtOrder, Const.TIMER_FOR_SEND_EMAIL);

            orderInfo.setOrderState(Const.Order.PAIED);
            orderInfo.setEngineerId(null);
            orderInfo.setEngineerName(null);
            orderInfo.setOrderFile(null);
            int row = orderInfoMapper.updateByPrimaryKey(orderInfo);
            if (row > 0) {
                return ServerResponse.createBySuccess("取消成功");
            }
        }
        return ServerResponse.createByErrorMessage("取消失败");
    }

    @Override
    public ServerResponse engineerCheckCancleOrder(Integer orderId, EngineerInfo engineerInfo) {
        if (orderId == null)
            return ServerResponse.createByErrorMessage("参数为空");

        OrderInfo orderInfo = orderInfoMapper.selectByPrimaryKey(orderId);
        if (orderInfo == null)
            return ServerResponse.createByErrorMessage("找不到该订单");

        if (orderInfo.getEngineerCheckId().intValue() == engineerInfo.getEngineerId().intValue() && orderInfo.getOrderState() == Const.Order.QAE_HAVE_CAUGHT) {

            EngineerInfo dbEngineer = engineerInfoMapper.selectByPrimaryKey(orderInfo.getEngineerCheckId());
            dbEngineer.setOrderCount(dbEngineer.getOrderCount() - 1);
            dbEngineer.setEngineerQuantity(BigDecimalUtil.sub(dbEngineer.getEngineerQuantity().doubleValue(), new BigDecimal("0.01").doubleValue()));
            engineerInfoMapper.updateByPrimaryKeySelective(dbEngineer);

            CustomerInfo customerInfo = customerInfoMapper.selectByPrimaryKey(orderInfo.getCustomerId());
            Timer timer = new Timer();
            TimerEmailCaughtOrder caughtOrder = new TimerEmailCaughtOrder(customerInfo.getEmail(), "您的订单已被审核工程师取消，等待下次接单");
            timer.schedule(caughtOrder, Const.TIMER_FOR_SEND_EMAIL);

            EngineerInfo engineerInfo1 = engineerInfoMapper.selectByPrimaryKey(orderInfo.getEngineerId());
            Timer timer1 = new Timer();
            TimerEmailCaughtOrder caughtOrder1 = new TimerEmailCaughtOrder(engineerInfo1.getEmail(), "您的订单已被审核工程师取消，等待下次接单");
            timer1.schedule(caughtOrder1, Const.TIMER_FOR_SEND_EMAIL);
            orderInfo.setOrderState(Const.Order.HAVE_UPLOAD_FILE);
            orderInfo.setEngineerCheckId(null);
            orderInfo.setEngineerCheckName(null);
            int row = orderInfoMapper.updateByPrimaryKey(orderInfo);
            if (row > 0) {
                return ServerResponse.createBySuccess("取消成功");
            }
        }
        return ServerResponse.createByErrorMessage("取消失败");
    }

    @Override
    public ServerResponse engineerComfirmDeductOrComplain(Integer orderId, EngineerInfo engineerInfo, Integer comfirmOrComplain, String dec) {
        if (orderId == null || comfirmOrComplain == null)
            return ServerResponse.createByErrorMessage("参数为空");

        OrderInfo orderInfo = orderInfoMapper.selectByPrimaryKey(orderId);
        if (orderInfo == null)
            return ServerResponse.createByErrorMessage("找不到该订单");

        if (orderInfo.getEngineerCheckId() == null) {
            if (orderInfo.getEngineerId().intValue() == engineerInfo.getEngineerId()) {
                if (orderInfo.getOrderState() == Const.Order.DEDUCT) {
                    if (comfirmOrComplain == 0) {
                        orderInfo.setOrderState(Const.Order.HAVE_FINISHED);

                        PriceDeductInfo priceDeductInfo = priceDeductInfoMapper.selectByPriceDeductRank(orderInfo.getOrderDeductRank());
                        BigDecimal customerDeductPrice = BigDecimalUtil.mul(orderInfo.getOrderPrice().doubleValue(), priceDeductInfo.getPriceDeductPercentage().doubleValue());
                        CustomerInfo customerInfo = customerInfoMapper.selectByPrimaryKey(orderInfo.getCustomerId());
                        customerInfo.setCustomerBalance(BigDecimalUtil.add(customerInfo.getCustomerBalance().doubleValue(), customerDeductPrice.doubleValue()));
                        customerInfo.setOrderCount(customerInfo.getOrderCount() + 1);
                        customerInfoMapper.updateByPrimaryKeySelective(customerInfo);

                        BillInfo billInfo = new BillInfo();
                        billInfo.setUserId(customerInfo.getCustomerId());
                        billInfo.setBillMoney(customerDeductPrice);
                        billInfo.setUserName(customerInfo.getCustomerName());
                        billInfo.setUserType(0);
                        billInfo.setBillDec("订单收入 :" + orderInfo.getOrderId());
                        billInfoMapper.insert(billInfo);


                        EngineerInfo dbengineerInfo = engineerInfoMapper.selectByPrimaryKey(orderInfo.getEngineerId());
                        List<QuantityInfo> list = quantityInfoMapper.selectByQuantiy(dbengineerInfo.getEngineerQuantity());
                        QuantityInfo maxQuantity = list.get(0);
                        BigDecimal orderCamPrice = BigDecimalUtil.sub(orderInfo.getOrderPrice().doubleValue(), customerDeductPrice.doubleValue());
                        orderCamPrice = BigDecimalUtil.sub(orderCamPrice.doubleValue(), BigDecimalUtil.mul(orderCamPrice.doubleValue(), maxQuantity.getQuantityDraw().doubleValue()).doubleValue());
                        dbengineerInfo.setEngineerBalance(BigDecimalUtil.add(orderCamPrice.doubleValue(), dbengineerInfo.getEngineerBalance().doubleValue()));
                        dbengineerInfo.setOrderCount(dbengineerInfo.getOrderCount() - 1);
                        engineerInfoMapper.updateByPrimaryKey(dbengineerInfo);

                        BillInfo billInfoEngineer = new BillInfo();
                        billInfoEngineer.setUserId(engineerInfo.getEngineerId());
                        billInfoEngineer.setBillMoney(orderCamPrice);
                        billInfoEngineer.setUserName(engineerInfo.getEngineerName());
                        billInfoEngineer.setUserType(1);
                        billInfoEngineer.setBillDec("订单收入 :" + orderInfo.getOrderId());
                        billInfoMapper.insert(billInfoEngineer);

                        BigDecimal allPrice = BigDecimalUtil.add(customerDeductPrice.doubleValue(), orderCamPrice.doubleValue());
                        IncomeInfo incomeInfo = new IncomeInfo();
                        incomeInfo.setOrderId(orderId);
                        incomeInfo.setIncomeMoney(BigDecimalUtil.sub(orderInfo.getOrderPrice().doubleValue(), allPrice.doubleValue()));
                        incomeInfoMapper.insert(incomeInfo);

                        orderInfo.setAdminPrice(BigDecimalUtil.sub(orderInfo.getOrderPrice().doubleValue(), allPrice.doubleValue()));
                        orderInfo.setEngineerRealPrice(orderCamPrice);
                        orderInfo.setEngineerQaeRealPrice(new BigDecimal(0));
                        orderInfoMapper.updateByPrimaryKeySelective(orderInfo);

                        return ServerResponse.createBySuccess("接受成功");
                    }

                    if (comfirmOrComplain == 1) {
                        if (dec == null)
                            return ServerResponse.createByErrorMessage("必须说明投诉原因");
                        orderInfo.setOrderState(Const.Order.COMPLAIN);
                        orderInfo.setOrderRateDec(dec);
                        int row = orderInfoMapper.updateByPrimaryKeySelective(orderInfo);
                        if (row > 0)
                            return ServerResponse.createBySuccess("投诉成功");
                        else
                            return ServerResponse.createByErrorMessage("投诉失败");
                    }
                }
            }
        }
        if (orderInfo.getEngineerCheckId() != null) {
            if (orderInfo.getEngineerId().intValue() == engineerInfo.getEngineerId()) {
                if (orderInfo.getOrderState() == Const.Order.DEDUCT) {
                    if (comfirmOrComplain == 0) {
                        orderInfo.setOrderState(Const.Order.ENGINEER_COMFIRM);
                        int row = orderInfoMapper.updateByPrimaryKeySelective(orderInfo);
                        if (row > 0) {
                            EngineerInfo engineerInfoCheck = engineerInfoMapper.selectByPrimaryKey(orderInfo.getEngineerCheckId());
                            Timer timerCheck = new Timer();
                            TimerEmailCaughtOrder caughtOrderCheck = new TimerEmailCaughtOrder(engineerInfoCheck.getEmail(), "您的订单被客户扣款，请您速去确认，如有问题可以点击投诉");
                            timerCheck.schedule(caughtOrderCheck, Const.TIMER_FOR_SEND_EMAIL);
                            return ServerResponse.createBySuccess("确认成功");
                        } else
                            return ServerResponse.createByErrorMessage("确认失败");
                    }
                    if (comfirmOrComplain == 1) {
                        orderInfo.setOrderState(Const.Order.COMPLAIN);
                        int row = orderInfoMapper.updateByPrimaryKeySelective(orderInfo);
                        if (row > 0) {
                            EngineerInfo engineerInfoCheck = engineerInfoMapper.selectByPrimaryKey(orderInfo.getEngineerCheckId());
                            Timer timerCheck = new Timer();
                            TimerEmailCaughtOrder caughtOrderCheck = new TimerEmailCaughtOrder(engineerInfoCheck.getEmail(), "您的订单被后台介入，请您速去查看");
                            timerCheck.schedule(caughtOrderCheck, Const.TIMER_FOR_SEND_EMAIL);
                            return ServerResponse.createBySuccess("投诉成功");
                        } else
                            return ServerResponse.createByErrorMessage("投诉失败");
                    }
                }
            }
        }
        return ServerResponse.createByErrorMessage("接受或者投诉失败");
    }

    @Override
    public ServerResponse engineerCheckComfirmDeductOrComplain(Integer orderId, EngineerInfo engineerInfo, Integer comfirmOrComplain, String dec) {
        if (orderId == null || comfirmOrComplain == null)
            return ServerResponse.createByErrorMessage("参数为空");

        OrderInfo orderInfo = orderInfoMapper.selectByPrimaryKey(orderId);
        if (orderInfo == null)
            return ServerResponse.createByErrorMessage("找不到该订单");

        if (orderInfo.getEngineerCheckId().intValue() == engineerInfo.getEngineerId().intValue()) {
            if (orderInfo.getOrderState() == Const.Order.ENGINEER_COMFIRM) {
                if (comfirmOrComplain == 0) {
                    orderInfo.setOrderState(Const.Order.HAVE_FINISHED);

                    PriceDeductInfo priceDeductInfo = priceDeductInfoMapper.selectByPriceDeductRank(orderInfo.getOrderDeductRank());
                    BigDecimal customerDeductPrice = BigDecimalUtil.mul(orderInfo.getOrderPrice().doubleValue(), priceDeductInfo.getPriceDeductPercentage().doubleValue());
                    CustomerInfo customerInfo = customerInfoMapper.selectByPrimaryKey(orderInfo.getCustomerId());
                    customerInfo.setCustomerBalance(BigDecimalUtil.add(customerInfo.getCustomerBalance().doubleValue(), customerDeductPrice.doubleValue()));
                    customerInfo.setOrderCount(customerInfo.getOrderCount() + 1);
                    customerInfoMapper.updateByPrimaryKeySelective(customerInfo);

                    BillInfo billInfo = new BillInfo();
                    billInfo.setUserId(customerInfo.getCustomerId());
                    billInfo.setBillMoney(customerDeductPrice);
                    billInfo.setUserName(customerInfo.getCustomerName());
                    billInfo.setUserType(0);
                    billInfo.setBillDec("订单收入 :" + orderInfo.getOrderId());
                    billInfoMapper.insert(billInfo);


                    EngineerInfo dbengineerInfo = engineerInfoMapper.selectByPrimaryKey(orderInfo.getEngineerId());
                    List<QuantityInfo> list = quantityInfoMapper.selectByQuantiy(dbengineerInfo.getEngineerQuantity());
                    QuantityInfo maxQuantity = list.get(0);
                    BigDecimal orderCamPrice = BigDecimalUtil.sub(orderInfo.getOrderPrice().doubleValue(), orderInfo.getOrderQaePrice().doubleValue());
                    orderCamPrice = BigDecimalUtil.sub(orderCamPrice.doubleValue(), BigDecimalUtil.mul(orderCamPrice.doubleValue(), priceDeductInfo.getPriceDeductPercentage().doubleValue()).doubleValue());
                    orderCamPrice = BigDecimalUtil.sub(orderCamPrice.doubleValue(), BigDecimalUtil.mul(orderCamPrice.doubleValue(), maxQuantity.getQuantityDraw().doubleValue()).doubleValue());
                    dbengineerInfo.setEngineerBalance(BigDecimalUtil.add(orderCamPrice.doubleValue(), dbengineerInfo.getEngineerBalance().doubleValue()));
                    dbengineerInfo.setOrderCount(dbengineerInfo.getOrderCount() - 1);
                    engineerInfoMapper.updateByPrimaryKey(dbengineerInfo);

                    BillInfo billInfoEngineer = new BillInfo();
                    billInfoEngineer.setUserId(dbengineerInfo.getEngineerId());
                    billInfoEngineer.setBillMoney(orderCamPrice);
                    billInfoEngineer.setUserName(dbengineerInfo.getEngineerName());
                    billInfoEngineer.setUserType(1);
                    billInfoEngineer.setBillDec("订单收入 :" + orderInfo.getOrderId());
                    billInfoMapper.insert(billInfoEngineer);


                    EngineerInfo dbengineerInfoQae = engineerInfoMapper.selectByPrimaryKey(orderInfo.getEngineerCheckId());
                    List<QuantityInfo> listQae = quantityInfoMapper.selectByQuantiy(dbengineerInfoQae.getEngineerQuantity());
                    QuantityInfo maxQuantityQae = listQae.get(0);
                    BigDecimal orderQaePrice = BigDecimalUtil.sub(orderInfo.getOrderQaePrice().doubleValue(), BigDecimalUtil.mul(orderInfo.getOrderQaePrice().doubleValue(), priceDeductInfo.getPriceDeductPercentage().doubleValue()).doubleValue());
                    orderQaePrice = BigDecimalUtil.sub(orderQaePrice.doubleValue(), BigDecimalUtil.mul(orderQaePrice.doubleValue(), maxQuantityQae.getQuantityDraw().doubleValue()).doubleValue());
                    dbengineerInfoQae.setEngineerBalance(BigDecimalUtil.add(orderQaePrice.doubleValue(), dbengineerInfoQae.getEngineerBalance().doubleValue()));
                    dbengineerInfoQae.setOrderCount(dbengineerInfoQae.getOrderCount() - 1);
                    engineerInfoMapper.updateByPrimaryKey(dbengineerInfoQae);

                    BillInfo billInfoEngineerQae = new BillInfo();
                    billInfoEngineerQae.setUserId(dbengineerInfoQae.getEngineerId());
                    billInfoEngineerQae.setBillMoney(orderQaePrice);
                    billInfoEngineerQae.setUserName(dbengineerInfoQae.getEngineerName());
                    billInfoEngineerQae.setUserType(1);
                    billInfoEngineerQae.setBillDec("审核订单收入 :" + orderInfo.getOrderId());
                    billInfoMapper.insert(billInfoEngineerQae);

                    BigDecimal allPrice = BigDecimalUtil.add(customerDeductPrice.doubleValue(), orderCamPrice.doubleValue());
                    allPrice = BigDecimalUtil.add(allPrice.doubleValue(), orderQaePrice.doubleValue());
                    IncomeInfo incomeInfo = new IncomeInfo();
                    incomeInfo.setOrderId(orderId);
                    incomeInfo.setIncomeMoney(BigDecimalUtil.sub(orderInfo.getOrderPrice().doubleValue(), allPrice.doubleValue()));
                    incomeInfoMapper.insert(incomeInfo);

                    orderInfo.setAdminPrice(BigDecimalUtil.sub(orderInfo.getOrderPrice().doubleValue(), allPrice.doubleValue()));
                    orderInfo.setEngineerRealPrice(orderCamPrice);
                    orderInfo.setEngineerQaeRealPrice(orderQaePrice);
                    orderInfoMapper.updateByPrimaryKeySelective(orderInfo);

                    return ServerResponse.createBySuccess("接受成功");

                }
                if (comfirmOrComplain == 1) {
                    if (dec == null)
                        return ServerResponse.createByErrorMessage("必须说明投诉原因");
                    orderInfo.setOrderState(Const.Order.COMPLAIN);
                    orderInfo.setOrderRateDec(dec);
                    int row = orderInfoMapper.updateByPrimaryKeySelective(orderInfo);
                    if (row > 0)
                        return ServerResponse.createBySuccess("投诉成功");
                    else
                        return ServerResponse.createByErrorMessage("投诉失败");
                }
            }
        }
        return ServerResponse.createByErrorMessage("接受或者投诉失败");
    }

    @Override
    public ServerResponse<PageInfo> adminOrderList(int pageSize, int pageNum, Integer state, Integer firstCategory, Integer orderQae) {
        String orderFirstCategory = null;
        if (firstCategory != null) {
            if (firstCategory == 0)
                orderFirstCategory = "PCB";
            else
                orderFirstCategory = "FPC";
        }
        PageHelper.startPage(pageNum, pageSize);
        PageHelper.orderBy("update_time desc");
        List<OrderInfo> list = orderInfoMapper.adminOrderList(state, orderFirstCategory, orderQae);
        PageInfo pageInfo = new PageInfo(list);
        return ServerResponse.createBySuccess(pageInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ServerResponse dealOrder(Integer orderId, BigDecimal customerPrice, BigDecimal engineerPrice, BigDecimal engineerQaePrice) {
        if (orderId == null || customerPrice == null || engineerPrice == null || engineerQaePrice == null)
            return ServerResponse.createByErrorMessage("参数为空");

        OrderInfo orderInfo = orderInfoMapper.selectByPrimaryKey(orderId);
        if (orderInfo == null)
            return ServerResponse.createByErrorMessage("找不到该订单");

        if (orderInfo.getOrderState() == Const.Order.HAVE_FINISHED || orderInfo.getOrderState() == Const.Order.CANNCEL || orderInfo.getOrderState() == Const.Order.PAYING || orderInfo.getOrderState() == Const.Order.PAIED)
            return ServerResponse.createByErrorMessage("异常操作");

        if (orderInfo.getEngineerCheckId() == null) {
            BigDecimal allPrice = BigDecimalUtil.add(customerPrice.doubleValue(), engineerPrice.doubleValue());
            if (allPrice.doubleValue() > orderInfo.getOrderPrice().doubleValue()) {
                return ServerResponse.createByErrorMessage("价格不合理");
            }
            orderInfo.setOrderState(Const.Order.HAVE_FINISHED);

            CustomerInfo customerInfo = customerInfoMapper.selectByPrimaryKey(orderInfo.getCustomerId());
            customerInfo.setCustomerBalance(BigDecimalUtil.add(customerPrice.doubleValue(), customerInfo.getCustomerBalance().doubleValue()));
            customerInfo.setOrderCount(customerInfo.getOrderCount() + 1);
            customerInfoMapper.updateByPrimaryKeySelective(customerInfo);

            BillInfo customerBillInfo = new BillInfo();
            customerBillInfo.setUserType(0);
            customerBillInfo.setUserName(customerInfo.getCustomerName());
            customerBillInfo.setBillMoney(customerPrice);
            customerBillInfo.setUserId(customerInfo.getCustomerId());
            customerBillInfo.setBillDec("订单收入 :" + orderInfo.getOrderId());
            billInfoMapper.insert(customerBillInfo);

            EngineerInfo engineerInfo = engineerInfoMapper.selectByPrimaryKey(orderInfo.getEngineerId());
            engineerInfo.setEngineerBalance(BigDecimalUtil.add(engineerInfo.getEngineerBalance().doubleValue(), engineerPrice.doubleValue()));
            engineerInfo.setOrderCount(engineerInfo.getOrderCount() - 1);
            engineerInfoMapper.updateByPrimaryKeySelective(engineerInfo);

            BillInfo engineerBillInfo = new BillInfo();
            engineerBillInfo.setUserType(1);
            engineerBillInfo.setUserName(engineerInfo.getEngineerName());
            engineerBillInfo.setBillMoney(engineerPrice);
            engineerBillInfo.setUserId(engineerInfo.getEngineerId());
            engineerBillInfo.setBillDec("订单收入 :" + orderInfo.getOrderId());
            billInfoMapper.insert(engineerBillInfo);

            IncomeInfo incomeInfo = new IncomeInfo();
            incomeInfo.setIncomeMoney(BigDecimalUtil.sub(orderInfo.getOrderPrice().doubleValue(), allPrice.doubleValue()));
            incomeInfo.setOrderId(orderId);
            incomeInfoMapper.insert(incomeInfo);

            orderInfo.setAdminPrice(BigDecimalUtil.sub(orderInfo.getOrderPrice().doubleValue(), allPrice.doubleValue()));
            orderInfo.setEngineerRealPrice(engineerPrice);
            orderInfo.setEngineerQaeRealPrice(new BigDecimal(0));
            orderInfoMapper.updateByPrimaryKeySelective(orderInfo);
            return ServerResponse.createBySuccess("操作成功");

        }

        if (orderInfo.getEngineerCheckId() != null) {
            BigDecimal allPrice = BigDecimalUtil.add(customerPrice.doubleValue(), engineerPrice.doubleValue());
            allPrice = BigDecimalUtil.add(allPrice.doubleValue(), engineerQaePrice.doubleValue());
            if (allPrice.doubleValue() > orderInfo.getOrderPrice().doubleValue()) {
                return ServerResponse.createByErrorMessage("价格不合理");
            }
            orderInfo.setOrderState(Const.Order.HAVE_FINISHED);

            CustomerInfo customerInfo = customerInfoMapper.selectByPrimaryKey(orderInfo.getCustomerId());
            customerInfo.setCustomerBalance(BigDecimalUtil.add(customerPrice.doubleValue(), customerInfo.getCustomerBalance().doubleValue()));
            customerInfo.setOrderCount(customerInfo.getOrderCount() + 1);
            customerInfoMapper.updateByPrimaryKeySelective(customerInfo);

            BillInfo customerBillInfo = new BillInfo();
            customerBillInfo.setUserType(0);
            customerBillInfo.setUserName(customerInfo.getCustomerName());
            customerBillInfo.setBillMoney(customerPrice);
            customerBillInfo.setUserId(customerInfo.getCustomerId());
            customerBillInfo.setBillDec("订单收入 :" + orderInfo.getOrderId());
            billInfoMapper.insert(customerBillInfo);

            EngineerInfo engineerInfo = engineerInfoMapper.selectByPrimaryKey(orderInfo.getEngineerId());
            engineerInfo.setEngineerBalance(BigDecimalUtil.add(engineerInfo.getEngineerBalance().doubleValue(), engineerPrice.doubleValue()));
            engineerInfo.setOrderCount(engineerInfo.getOrderCount() - 1);
            engineerInfoMapper.updateByPrimaryKeySelective(engineerInfo);

            BillInfo engineerBillInfo = new BillInfo();
            engineerBillInfo.setUserType(1);
            engineerBillInfo.setUserName(engineerInfo.getEngineerName());
            engineerBillInfo.setBillMoney(engineerPrice);
            engineerBillInfo.setUserId(engineerInfo.getEngineerId());
            engineerBillInfo.setBillDec("订单收入 :" + orderInfo.getOrderId());
            billInfoMapper.insert(engineerBillInfo);

            EngineerInfo engineerQaeInfo = engineerInfoMapper.selectByPrimaryKey(orderInfo.getEngineerCheckId());
            engineerQaeInfo.setEngineerBalance(BigDecimalUtil.add(engineerQaeInfo.getEngineerBalance().doubleValue(), engineerQaePrice.doubleValue()));
            engineerQaeInfo.setOrderCount(engineerQaeInfo.getOrderCount() - 1);
            engineerInfoMapper.updateByPrimaryKeySelective(engineerQaeInfo);

            BillInfo engineerQaeInfoBillInfo = new BillInfo();
            engineerQaeInfoBillInfo.setUserType(1);
            engineerQaeInfoBillInfo.setUserName(engineerQaeInfo.getEngineerName());
            engineerQaeInfoBillInfo.setBillMoney(engineerQaePrice);
            engineerQaeInfoBillInfo.setUserId(engineerQaeInfo.getEngineerId());
            engineerQaeInfoBillInfo.setBillDec("订单收入 :" + orderInfo.getOrderId());
            billInfoMapper.insert(engineerQaeInfoBillInfo);

            IncomeInfo incomeInfo = new IncomeInfo();
            incomeInfo.setIncomeMoney(BigDecimalUtil.sub(orderInfo.getOrderPrice().doubleValue(), allPrice.doubleValue()));
            incomeInfo.setOrderId(orderId);
            incomeInfoMapper.insert(incomeInfo);

            orderInfo.setAdminPrice(BigDecimalUtil.sub(orderInfo.getOrderPrice().doubleValue(), allPrice.doubleValue()));
            orderInfo.setEngineerRealPrice(engineerPrice);
            orderInfo.setEngineerQaeRealPrice(engineerQaePrice);
            orderInfoMapper.updateByPrimaryKeySelective(orderInfo);
            return ServerResponse.createBySuccess("操作成功");

        }

        return ServerResponse.createByErrorMessage("操作失败");
    }

    @Override
    public ServerResponse addAnsOrQue(Integer orderId, Integer userId, Integer type, String userName, String orderAnsqueContent) {
        if (orderId == null || orderAnsqueContent == null)
            return ServerResponse.createByErrorMessage("参数为空");

        OrderInfo orderInfo = orderInfoMapper.selectByPrimaryKey(orderId);
        if (orderInfo == null)
            return ServerResponse.createByErrorMessage("找不到该订单");

        if (orderInfo.getOrderState() == Const.Order.HAVE_FINISHED || orderInfo.getOrderState() == Const.Order.CANNCEL)
            return ServerResponse.createByErrorMessage("订单已完成或者已取消");

        if (type == 0 && userId.intValue() != orderInfo.getCustomerId().intValue())
            return ServerResponse.createByErrorMessage("您不属于该订单");

        if (type == 1 && userId.intValue() != orderInfo.getEngineerId().intValue()) {
            if (type == 1 && userId.intValue() != orderInfo.getEngineerCheckId().intValue()){
                return ServerResponse.createByErrorMessage("您不属于该订单");
            }
            type = 2;
        }

        OrderAnsqueInfo orderAnsqueInfo = new OrderAnsqueInfo();
        orderAnsqueInfo.setOrderAnsqueContent(orderAnsqueContent);
        orderAnsqueInfo.setOrderId(orderId);
        orderAnsqueInfo.setUserId(userId);
        orderAnsqueInfo.setUserName(userName);
        orderAnsqueInfo.setUserType(type);
        orderAnsqueInfo.setState(0);

        int row = orderAnsqueInfoMapper.insert(orderAnsqueInfo);
        if (row > 0)
            return ServerResponse.createBySuccess("操作成功");
        return ServerResponse.createByErrorMessage("操作失败");
    }

    @Override
    public ServerResponse uploadAnsOrQue(Integer orderId, Integer userId, Integer type, String userName, MultipartFile file, String path) {
        if (orderId == null || file == null)
            return ServerResponse.createByErrorMessage("参数为空");

        OrderInfo orderInfo = orderInfoMapper.selectByPrimaryKey(orderId);
        if (orderInfo == null)
            return ServerResponse.createByErrorMessage("找不到该订单");

        if (orderInfo.getOrderState() == Const.Order.HAVE_FINISHED || orderInfo.getOrderState() == Const.Order.CANNCEL)
            return ServerResponse.createByErrorMessage("订单已完成或者已取消");

        if (type == 0 && userId.intValue() != orderInfo.getCustomerId().intValue())
            return ServerResponse.createByErrorMessage("您不属于该订单");

        if (type == 1 && userId.intValue() != orderInfo.getEngineerId().intValue()){
            if (type == 1 && userId.intValue() != orderInfo.getEngineerCheckId().intValue()){
                return ServerResponse.createByErrorMessage("您不属于该订单");
            }
            type = 2;
        }

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

        OrderAnsqueInfo orderAnsqueInfo = new OrderAnsqueInfo();
        orderAnsqueInfo.setOrderAnsqueContent(PropertiesUtil.getProperty("ftp.server.http.prefix") + targetFile.getName());
        orderAnsqueInfo.setOrderId(orderId);
        orderAnsqueInfo.setUserId(userId);
        orderAnsqueInfo.setUserName(userName);
        orderAnsqueInfo.setUserType(type);
        orderAnsqueInfo.setState(0);

        int row = orderAnsqueInfoMapper.insert(orderAnsqueInfo);
        if (row > 0)
            return ServerResponse.createBySuccess("操作成功");
        return ServerResponse.createByErrorMessage("操作失败");
    }

    @Override
    public ServerResponse<PageInfo> listOrderAnsqueInfoByOrderId(int pageNum, int pageSize, Integer orderId, Integer userId, Integer type) {
        if (orderId == null)
            return ServerResponse.createByErrorMessage("参数为空");

        OrderInfo orderInfo = orderInfoMapper.selectByPrimaryKey(orderId);
        if (orderInfo == null)
            return ServerResponse.createByErrorMessage("找不到该订单");

        if (type == 0 && userId.intValue() != orderInfo.getCustomerId().intValue())
            return ServerResponse.createByErrorMessage("您不属于该订单");

        if (type == 1 && userId.intValue() != orderInfo.getEngineerId().intValue()) {
            if (type == 1 && userId.intValue() != orderInfo.getEngineerCheckId().intValue()){
                return ServerResponse.createByErrorMessage("您不属于该订单");
            }
            type = 2;
        }
        PageHelper.startPage(pageNum, pageSize);
        PageHelper.orderBy("create_time asc");
        List<OrderAnsqueInfo> list = orderAnsqueInfoMapper.list(orderId);
        orderAnsqueInfoMapper.updateMessAgeState(orderId,type);
        PageInfo pageInfo = new PageInfo(list);
        return ServerResponse.createBySuccess(pageInfo);
    }

    @Override
    public ServerResponse<PageInfo> listOrderAnsqueInfoByOrderId(int pageNum, int pageSize, Integer orderId) {
        if (orderId == null)
            return ServerResponse.createByErrorMessage("参数为空");

        OrderInfo orderInfo = orderInfoMapper.selectByPrimaryKey(orderId);
        if (orderInfo == null)
            return ServerResponse.createByErrorMessage("找不到该订单");

        PageHelper.startPage(pageNum, pageSize);
        PageHelper.orderBy("create_time asc");
        List<OrderAnsqueInfo> list = orderAnsqueInfoMapper.list(orderId);
        PageInfo pageInfo = new PageInfo(list);
        return ServerResponse.createBySuccess(pageInfo);
    }

    @Override
    public ServerResponse<PageInfo> selectByIdLike(int pageNum, int pageSize, Integer orderId) {
        if (orderId == null)
            return ServerResponse.createByErrorMessage("参数为空");
        String orderIdString = "" + orderId + "%";
        PageHelper.startPage(pageNum, pageSize);
        PageHelper.orderBy("create_time asc");
        List<OrderInfo> list = orderInfoMapper.selectByIdLike(orderIdString);
        PageInfo pageInfo = new PageInfo(list);
        return ServerResponse.createBySuccess(pageInfo);
    }

    @Override
    public ServerResponse<PageInfo> selectByOtherIdLike(int pageNum, int pageSize, Integer id, Integer type) {
        if (id == null)
            return ServerResponse.createByErrorMessage("参数为空");
        String idString = "" + id + "%";
        PageHelper.startPage(pageNum, pageSize);
        PageHelper.orderBy("create_time asc");
        List<OrderInfo> list = null;
        if (type == 0){
        list = orderInfoMapper.selectByOtherIdLike(idString,
                null, null);
        }
        if (type == 1){
            list = orderInfoMapper.selectByOtherIdLike(null, idString, null);
        }
        if (type == 2){
            list = orderInfoMapper.selectByOtherIdLike(null, null, idString);
        }
        PageInfo pageInfo = new PageInfo(list);
        return ServerResponse.createBySuccess(pageInfo);
    }

    @Override
    public ServerResponse getAllPrice(Integer type) {
        if (type == 0){
            BigDecimal payPrice = payInfoMapper.allPrice();
            return ServerResponse.createBySuccess(payPrice);
        }

        if (type == 1){
            BigDecimal drawPrice = drawCashInfoMapper.allPrice();
            return ServerResponse.createBySuccess(drawPrice);
        }

        if (type == 2){
            BigDecimal customerPrice = customerInfoMapper.allPrice();
            return ServerResponse.createBySuccess(customerPrice);
        }

        if (type == 3){
            BigDecimal engineerPrice = engineerInfoMapper.allPrice();
            return ServerResponse.createBySuccess(engineerPrice);
        }

        if (type == 4){
            BigDecimal incomePrice = incomeInfoMapper.allPrice();
            return ServerResponse.createBySuccess(incomePrice);
        }

        if (type == 5){
            BigDecimal unFinishedOrderPrice = orderInfoMapper.allUnfinishedPrice();
            return ServerResponse.createBySuccess(unFinishedOrderPrice);
        }

        if (type == 6){
            BigDecimal finishedOrderPrice = orderInfoMapper.hasFinshedPrice();
            return ServerResponse.createBySuccess(finishedOrderPrice);
        }
        return null;
    }


    @Override
    public XSSFWorkbook exportExcelInfo(String startTime, String endTime) {
        if (startTime == null || endTime == null)
            return null;
        try {
            //根据ID查找数据
            Date startDate = DateTimeUtil.strToDate(startTime, "yyyy-MM-dd");
            Date endDate = DateTimeUtil.strToDate(endTime, "yyyy-MM-dd");
            List<OrderInfo> orderInfoList = orderInfoMapper.selectByTime(startDate, endDate);

            XSSFWorkbook xssfWorkbook = null;
            List<ExcelBean> excel = new ArrayList<>();
            Map<Integer, List<ExcelBean>> map = new LinkedHashMap<>();
            //设置标题栏
            excel.add(new ExcelBean("订单id", "orderId", 0));
            excel.add(new ExcelBean("订单金额", "orderPrice", 0));
            excel.add(new ExcelBean("工程师收款金额", "engineerRealPrice", 0));
            excel.add(new ExcelBean("审核工程师收款金额", "engineerQaeRealPrice", 0));
            excel.add(new ExcelBean("平台抽成金额", "adminPrice", 0));
            excel.add(new ExcelBean("客户文件名", "orderCustomerFile", 0));
            excel.add(new ExcelBean("工程师完单文件", "orderFile", 0));
            excel.add(new ExcelBean("客户编号", "customerId", 0));
            excel.add(new ExcelBean("制作工程师编号", "engineerId", 0));
            excel.add(new ExcelBean("审核工程师编号", "engineerCheckId", 0));
            excel.add(new ExcelBean(" PCB种类", "orderFirstCategory", 0));
            excel.add(new ExcelBean("订单种类", "orderSecondCategory", 0));
            excel.add(new ExcelBean("MI", "orderMi", 0));
            excel.add(new ExcelBean("QAE", "orderQae", 0));
            excel.add(new ExcelBean("参数", "otherParamInfo", 0));
            excel.add(new ExcelBean("层数", "basicLayer", 0));
            excel.add(new ExcelBean("拼款数", "priceTogetherNum", 0));
            excel.add(new ExcelBean("下订单的时间", "createTime", 0));
            excel.add(new ExcelBean("完成时间", "updateTime", 0));
            map.put(0, excel);
            String sheetName = "order";
            xssfWorkbook = ExcelUtil.createExcelFile(OrderInfo.class, orderInfoList, map, sheetName);
            return xssfWorkbook;
        } catch (Exception e) {
            return null;
        }


    }

    @Override
    public XSSFWorkbook customerOrEngineerExportExcelInfo(Integer type, Integer id, String startTime, String endTime) {
        if (startTime == null || endTime == null)
            return null;
        if (type == 0) {
            try {
                Date startDate = DateTimeUtil.strToDate(startTime, "yyyy-MM-dd");
                Date endDate = DateTimeUtil.strToDate(endTime, "yyyy-MM-dd");
                List<OrderInfo> orderInfoList = orderInfoMapper.selectByTimeAndType(startDate, endDate, id, null, null, Const.Order.HAVE_FINISHED);
                XSSFWorkbook xssfWorkbook = null;
                List<ExcelBean> excel = new ArrayList<>();
                Map<Integer, List<ExcelBean>> map = new LinkedHashMap<>();
                //设置标题栏
                excel.add(new ExcelBean("订单id", "orderId", 0));
                excel.add(new ExcelBean("订单金额", "orderPrice", 0));
                excel.add(new ExcelBean("客户文件名", "orderCustomerFile", 0));
                excel.add(new ExcelBean("工程师完单文件", "orderFile", 0));
                excel.add(new ExcelBean("客户编号", "customerId", 0));
                excel.add(new ExcelBean("制作工程师编号", "engineerId", 0));
                excel.add(new ExcelBean("审核工程师编号", "engineerCheckId", 0));
                excel.add(new ExcelBean(" PCB种类", "orderFirstCategory", 0));
                excel.add(new ExcelBean("订单种类", "orderSecondCategory", 0));
                excel.add(new ExcelBean("MI", "orderMi", 0));
                excel.add(new ExcelBean("QAE", "orderQae", 0));
                excel.add(new ExcelBean("参数", "otherParamInfo", 0));
                excel.add(new ExcelBean("层数", "basicLayer", 0));
                excel.add(new ExcelBean("拼款数", "priceTogetherNum", 0));
                excel.add(new ExcelBean("下订单的时间", "createTime", 0));
                excel.add(new ExcelBean("完成时间", "updateTime", 0));
                map.put(0, excel);
                String sheetName = "order";
                xssfWorkbook = ExcelUtil.createExcelFile(OrderInfo.class, orderInfoList, map, sheetName);
                return xssfWorkbook;
            } catch (Exception e) {
                return null;
            }
        }
        if (type == 1){
            try {
                Date startDate = DateTimeUtil.strToDate(startTime,"yyyy-MM-dd");
                Date endDate = DateTimeUtil.strToDate(endTime, "yyyy-MM-dd");
                List<OrderInfo> orderInfoList = orderInfoMapper.selectByTimeAndType(startDate, endDate, null,id, null, Const.Order.HAVE_FINISHED);
                XSSFWorkbook xssfWorkbook=null;
                List<ExcelBean> excel=new ArrayList<>();
                Map<Integer,List<ExcelBean>> map=new LinkedHashMap<>();
                //设置标题栏
                excel.add(new ExcelBean("订单id","orderId",0));
                excel.add(new ExcelBean("订单金额","engineerRealPrice",0));
                excel.add(new ExcelBean("客户文件名","orderCustomerFile",0));
                excel.add(new ExcelBean("工程师完单文件","orderFile",0));
                excel.add(new ExcelBean("客户编号","customerId",0));
                excel.add(new ExcelBean("制作工程师编号","engineerId",0));
                excel.add(new ExcelBean("审核工程师编号","engineerCheckId",0));
                excel.add(new ExcelBean(" PCB种类","orderFirstCategory",0));
                excel.add(new ExcelBean("订单种类","orderSecondCategory",0));
                excel.add(new ExcelBean("MI","orderMi",0));
                excel.add(new ExcelBean("QAE","orderQae",0));
                excel.add(new ExcelBean("参数","otherParamInfo",0));
                excel.add(new ExcelBean("层数","basicLayer",0));
                excel.add(new ExcelBean("拼款数","priceTogetherNum",0));
                excel.add(new ExcelBean("下订单的时间","createTime",0));
                excel.add(new ExcelBean("完成时间","updateTime",0));
                map.put(0, excel);
                String sheetName ="order";
                xssfWorkbook = ExcelUtil.createExcelFile(OrderInfo.class, orderInfoList, map, sheetName);
                return xssfWorkbook;
            }catch (Exception e){
                return null;
            }
        }

        if (type == 2){
            try {
                Date startDate = DateTimeUtil.strToDate(startTime,"yyyy-MM-dd");
                Date endDate = DateTimeUtil.strToDate(endTime, "yyyy-MM-dd");
                List<OrderInfo> orderInfoList = orderInfoMapper.selectByTimeAndType(startDate, endDate, null, null, id, Const.Order.HAVE_FINISHED);
                XSSFWorkbook xssfWorkbook=null;
                List<ExcelBean> excel=new ArrayList<>();
                Map<Integer,List<ExcelBean>> map=new LinkedHashMap<>();
                //设置标题栏
                excel.add(new ExcelBean("订单id","orderId",0));
                excel.add(new ExcelBean("订单金额","engineerQaeRealPrice",0));
                excel.add(new ExcelBean("客户文件名","orderCustomerFile",0));
                excel.add(new ExcelBean("工程师完单文件","orderFile",0));
                excel.add(new ExcelBean("客户编号","customerId",0));
                excel.add(new ExcelBean("制作工程师编号","engineerId",0));
                excel.add(new ExcelBean("审核工程师编号","engineerCheckId",0));
                excel.add(new ExcelBean(" PCB种类","orderFirstCategory",0));
                excel.add(new ExcelBean("订单种类","orderSecondCategory",0));
                excel.add(new ExcelBean("MI","orderMi",0));
                excel.add(new ExcelBean("QAE","orderQae",0));
                excel.add(new ExcelBean("参数","otherParamInfo",0));
                excel.add(new ExcelBean("层数","basicLayer",0));
                excel.add(new ExcelBean("拼款数","priceTogetherNum",0));
                excel.add(new ExcelBean("下订单的时间","createTime",0));
                excel.add(new ExcelBean("完成时间","updateTime",0));
                map.put(0, excel);
                String sheetName ="order";
                xssfWorkbook = ExcelUtil.createExcelFile(OrderInfo.class, orderInfoList, map, sheetName);
                return xssfWorkbook;
            }catch (Exception e){
                return null;
            }
        }
        return null;
    }


    @Override
    public ServerResponse changeOrderState(Integer type, Integer orderId) {
        if (type == null || orderId == null)
            return ServerResponse.createByErrorMessage("参数为空");

        OrderInfo orderInfo = orderInfoMapper.selectByPrimaryKey(orderId);
        if (orderInfo == null)
            return ServerResponse.createByErrorMessage("找不到该订单");

        if (type == 0){
            orderInfo.setOrderState(Const.Order.HAVE_FINISHED);
            int row = orderInfoMapper.updateByPrimaryKeySelective(orderInfo);
            if (row > 0){
                if (orderInfo.getCustomerId() != null){
                    CustomerInfo customerInfo = customerInfoMapper.selectByPrimaryKey(orderInfo.getCustomerId());
                    sendEmai("您的订单已被后台设置为已完成,订单号：" + orderId, customerInfo.getEmail());
                }
                if (orderInfo.getEngineerId() != null){
                    EngineerInfo engineerInfo = engineerInfoMapper.selectByPrimaryKey(orderInfo.getEngineerId());
                    sendEmai("您的订单已被后台设置为已完成,订单号：" + orderId, engineerInfo.getEmail());
                }
                if (orderInfo.getEngineerCheckId() != null){
                    EngineerInfo engineerInfo = engineerInfoMapper.selectByPrimaryKey(orderInfo.getEngineerCheckId());
                    sendEmai("您的订单已被后台设置为已完成,订单号：" + orderId, engineerInfo.getEmail());
                }
                return ServerResponse.createBySuccess("已设置订单完成");
            }
            return ServerResponse.createByErrorMessage("修改失败");
        }

        if (type == 1){
            orderInfo.setOrderState(Const.Order.CANNCEL);
            int row = orderInfoMapper.updateByPrimaryKeySelective(orderInfo);
            if (row > 0){
                if (orderInfo.getCustomerId() != null){
                    CustomerInfo customerInfo = customerInfoMapper.selectByPrimaryKey(orderInfo.getCustomerId());
                    sendEmai("您的订单已被后台设置为已取消,订单号：" + orderId, customerInfo.getEmail());
                }
                if (orderInfo.getEngineerId() != null){
                    EngineerInfo engineerInfo = engineerInfoMapper.selectByPrimaryKey(orderInfo.getEngineerId());
                    sendEmai("您的订单已被后台设置为已取消,订单号：" + orderId, engineerInfo.getEmail());
                }
                if (orderInfo.getEngineerCheckId() != null){
                    EngineerInfo engineerInfo = engineerInfoMapper.selectByPrimaryKey(orderInfo.getEngineerCheckId());
                    sendEmai("您的订单已被后台设置为已取消,订单号：" + orderId, engineerInfo.getEmail());
                }
                return ServerResponse.createBySuccess("已设置订单取消");
            }
            return ServerResponse.createByErrorMessage("修改失败");
        }
        return ServerResponse.createByErrorMessage("异常操作");
    }

    @Override
    public ServerResponse createTarByDate(String startTime, String endTime) {
//        if (startTime == null || endTime == null)
//            return ServerResponse.createByErrorMessage("参数为空");
        Runtime run = Runtime.getRuntime();
        try {
            Process process = run.exec("echo ---进入测试文件夹----\n" +
                    "cd /product/ftpfile/img\n" +
                    "\n" +
                    "echo ----打包---\n" +
                    "tar -cvf 问问.tar 问问.txt 问问1.txt 问问2.txt\n" +
                    "\n" +
                    "\n" +
                    "echo ---删除----\n" +
                    "rm -rf 问问.txt 问问1.txt 问问2.txt\n" +
                    "\n");
            InputStream in = process.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuffer stringBuffer = new StringBuffer();
            byte b[] = new byte[8192];
            for (int n; (n = in.read(b)) != -1;){
                stringBuffer.append(new String(b, 0, n));
            }
            logger.info(stringBuffer.toString());
            in.close();
            process.destroy();
            return ServerResponse.createBySuccess("");
        }catch (IOException e){
            logger.info("执行失败");
            e.printStackTrace();
        }
        return null;
    }

    private void sendEmai(String str, String email){
        Timer timerCheck = new Timer();
        TimerEmailCaughtOrder caughtOrderCheck = new TimerEmailCaughtOrder(email, str);
        timerCheck.schedule(caughtOrderCheck, Const.TIMER_FOR_SEND_EMAIL);
    }

}
