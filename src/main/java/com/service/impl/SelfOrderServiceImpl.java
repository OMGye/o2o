package com.service.impl;

import com.common.Const;
import com.common.ServerResponse;
import com.dao.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.pojo.*;
import com.service.SelfOrderService;
import com.util.*;
import com.vo.EngineerDefriendJson;
import com.vo.OrderAndMessagenumVo;
import com.vo.SelfOrderAndMessagenumVo;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by upupgogogo on 2019/6/7.下午7:37
 */
@Service("selfOrderService")
public class SelfOrderServiceImpl implements SelfOrderService{

    @Autowired
    private SelfOrderMapper orderMapper;
    @Autowired
    private SelfCategoryMapper categoryMapper;
    @Autowired
    private CustomerInfoMapper customerInfoMapper;
    @Autowired
    private BillInfoMapper billInfoMapper;
    @Autowired
    private EngineerInfoMapper engineerInfoMapper;
    @Autowired
    private SelfQuantityInfoMapper quantityInfoMapper;
    @Autowired
    private IncomeInfoMapper incomeInfoMapper;
    @Autowired
    private PriceDeductInfoMapper priceDeductInfoMapper;
    @Autowired
    private SelfOrderAnsqueMapper selfOrderAnsqueMapper;

    @Autowired
    private PayInfoMapper payInfoMapper;

    private static final Logger logger = LoggerFactory.getLogger(OrderInfoServiceImpl.class);

    @Override
    public ServerResponse createOrder(SelfOrder order, CustomerInfo customerInfo, MultipartFile file, String path) {
        if (order == null || order.getOrderFirstCategory() == null || order.getOrderSelfCategory() == null || order.getOrderPrice() == null)
            return ServerResponse.createByErrorMessage("参数不能为空");

        SelfCategory category = categoryMapper.selectByName(order.getOrderSelfCategory());
        if (category.getSelfCategoryPrice().doubleValue() > order.getOrderPrice().doubleValue())
            return ServerResponse.createByErrorMessage("付款金额不能小于该参数价格");
        order.setCustomerId(customerInfo.getCustomerId());
        order.setCustomerName(customerInfo.getCustomerName());
        order.setOrderState(Const.SelfOrder.CREATE);


        String fileName = file.getOriginalFilename();
        String customerFileRealName = fileName.substring(0, fileName.lastIndexOf("."));
        order.setCustomerFileRealName(customerFileRealName);
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
        order.setDownload(0);

        int row = orderMapper.insert(order);
        if (row > 0) {
            Timer timer = new Timer();
            TimerChangePaySelfOrder changePayOrder = new TimerChangePaySelfOrder(order.getOrderId(), orderMapper);
            timer.schedule(changePayOrder, Const.TIMER);
            return ServerResponse.createBySuccess("下单成功", order.getOrderId());

        }
        return ServerResponse.createByErrorMessage("下单失败");

    }

    @Override
    public ServerResponse payForBalance(Integer orderId, Integer customerId) {
        if (orderId == null)
            return ServerResponse.createByErrorMessage("订单号为空");

        SelfOrder order = orderMapper.selectByPrimaryKey(orderId);
        if (order == null)
            return ServerResponse.createByErrorMessage("找不到该订单");

        if (order.getOrderState() != Const.SelfOrder.CREATE)
            return ServerResponse.createByErrorMessage("该订单状态不可支付");

        CustomerInfo customerInfo = customerInfoMapper.selectByPrimaryKey
                (customerId);
        if (order.getOrderPrice().doubleValue() > customerInfo.getCustomerBalance().doubleValue()){
            return ServerResponse.createByErrorMessage("余额不足");
        }

        customerInfo.setCustomerBalance(BigDecimalUtil.sub(customerInfo.getCustomerBalance().doubleValue(), order.getOrderPrice().doubleValue()));
        customerInfoMapper.updateByPrimaryKeySelective(customerInfo);

        order.setOrderState(Const.SelfOrder.PAID);
        orderMapper.updateByPrimaryKeySelective(order);

        BillInfo billInfo = new BillInfo();
        billInfo.setUserId(customerInfo.getCustomerId());
        billInfo.setBillMoney(BigDecimalUtil.sub(order.getOrderPrice().doubleValue(), BigDecimalUtil.add(order.getOrderPrice().doubleValue(), order.getOrderPrice().doubleValue()).doubleValue()));
        billInfo.setUserName(customerInfo.getCustomerName());
        billInfo.setUserType(0);
        billInfo.setBillDec("支付自建订单 :" + order.getOrderId());
        billInfoMapper.insert(billInfo);

        return ServerResponse.createBySuccess("支付成功");
    }

    @Override
    public ServerResponse getOrderById(Integer orderId) {
        if (orderId == null)
            return ServerResponse.createByErrorMessage("参数为空");
        SelfOrder order = orderMapper.selectByPrimaryKey(orderId);
        return ServerResponse.createBySuccess(order);
    }

    @Override
    public ServerResponse caughtOrder(Integer orderId, EngineerInfo engineerInfo) {
        if (orderId == null)
            return ServerResponse.createByErrorMessage("订单号为空");

        SelfOrder order = orderMapper.selectByPrimaryKey(orderId);
        if (order == null)
            return ServerResponse.createByErrorMessage("找不到该订单");

        if (order.getOrderState() != Const.SelfOrder.PAID)
            return ServerResponse.createByErrorMessage("该订单状态不可接");

        CustomerInfo customerInfo = customerInfoMapper.selectByPrimaryKey
                (order.getCustomerId());
        if (customerInfo.getEngineerDefriend() != null) {
            List<EngineerDefriendJson> list = JsonUtil.toJsonList(customerInfo.getEngineerDefriend());
            for (EngineerDefriendJson engineerDefriendJson : list) {
                if (engineerDefriendJson.getEngineerId().intValue() == engineerInfo.getEngineerId())
                    return ServerResponse.createByErrorMessage("您已被该客户拉黑");
            }
        }

        EngineerInfo dbEngineerInfo = engineerInfoMapper.selectByPrimaryKey(engineerInfo.getEngineerId());
        List<SelfOrder> orderList = orderMapper.engineerCaughtList(dbEngineerInfo.getEngineerId(), Const.SelfOrder.CAUGHT);
        if (orderList.size() >= 1)
            return ServerResponse.createByErrorMessage("您当前可接订单数已满");

        SelfCategory category = categoryMapper.selectByName(order.getOrderSelfCategory());
        if (category.getRank() <= dbEngineerInfo.getEngineerRank()) {
            order.setEngineerId(dbEngineerInfo.getEngineerId());
            order.setEngineerName(dbEngineerInfo.getEngineerName());
            order.setOrderState(Const.SelfOrder.CAUGHT);

            int row = orderMapper.updateByPrimaryKeySelective(order);
            if (row > 0) {
                Timer timer = new Timer();
                TimerEmailCaughtOrder caughtOrder = new TimerEmailCaughtOrder(customerInfo.getEmail(), "您的自建订单已被接单");
                timer.schedule(caughtOrder, Const.TIMER_FOR_SEND_EMAIL);
                return ServerResponse.createBySuccess("接单成功");
            }
            return ServerResponse.createByErrorMessage("接单失败");
        }
        return ServerResponse.createByErrorMessage("您不满足该订单等级");
    }

    @Override
    public ServerResponse orderUploadFile(Integer orderId, EngineerInfo engineerInfo, MultipartFile file, String path) {
        if (orderId == null || file == null)
            return ServerResponse.createByErrorMessage("参数不能为空");

        SelfOrder order = orderMapper.selectByPrimaryKey(orderId);
        if (order == null || order.getEngineerId().intValue() != engineerInfo.getEngineerId())
            return ServerResponse.createByErrorMessage("找不到该订单");

        if (order.getOrderState() != Const.SelfOrder.CAUGHT)
            return ServerResponse.createByErrorMessage("该订单状态不可上传文件");

        if (file != null) {
            String fileName = file.getOriginalFilename();
            //扩展名
            //abc.jpg
            String fileExtensionName = fileName.substring(fileName.lastIndexOf(".") + 1);
            String uploadFileName = orderId + "-" + order.getCustomerFileRealName() + "." + fileExtensionName;
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
            order.setOrderFile(PropertiesUtil.getProperty("ftp.server.http.prefix") + targetFile.getName());
        }

        order.setOrderState(Const.SelfOrder.SEND);

        int row = orderMapper.updateByPrimaryKeySelective(order);
        if (row <= 0)
            return ServerResponse.createByErrorMessage("上传失败");

        CustomerInfo customerInfo = customerInfoMapper.selectByPrimaryKey
                (order.getCustomerId());
        Timer timer = new Timer();
        TimerEmailCaughtOrder caughtOrder = new TimerEmailCaughtOrder(customerInfo.getEmail(), "您的自建订单已被工程师完成");
        timer.schedule(caughtOrder, Const.TIMER_FOR_SEND_EMAIL);

        return ServerResponse.createBySuccess("上传成功");
    }

    @Override
    public ServerResponse receiveOrder(Integer orderId, CustomerInfo customerInfo) {
        if (orderId == null)
            return ServerResponse.createByErrorMessage("参数不能为空");

        SelfOrder order = orderMapper.selectByPrimaryKey(orderId);
        if (order != null && order.getCustomerId().intValue() == customerInfo.getCustomerId().intValue()) {
            if (order.getOrderState() == Const.SelfOrder.SEND) {
                order.setOrderState(Const.SelfOrder.REIVER);
                int row = orderMapper.updateByPrimaryKeySelective(order);
                if (row > 0) {
                    EngineerInfo engineerInfo = engineerInfoMapper.selectByPrimaryKey
                            (order.getEngineerId());
                    Timer timer = new Timer();
                    TimerEmailCaughtOrder caughtOrder = new TimerEmailCaughtOrder(engineerInfo.getEmail(), "您完成的自建订单已被客户查看，如果七天之内没有确认，系统将直接帮您确认，并将资金直接转入您的账户");
                    timer.schedule(caughtOrder, Const.TIMER_FOR_SEND_EMAIL);

                    List<SelfQuantityInfo> list = quantityInfoMapper.selectByQuantiy(engineerInfo.getEngineerQuantity());
                    SelfQuantityInfo maxQuantity = list.get(0);

                    BigDecimal drawEnginner = null;
                    Timer newTimer = new Timer();
                    TimerChangeSelfOrderFinished timerChangeOrderFinished = new TimerChangeSelfOrderFinished(orderId, orderMapper, engineerInfoMapper, customerInfoMapper, drawEnginner, maxQuantity.getQuantityDraw(), billInfoMapper, incomeInfoMapper);
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

        SelfOrder order = orderMapper.selectByPrimaryKey(orderId);
        if (order == null && order.getCustomerId().intValue() != customerId)
            return ServerResponse.createByErrorMessage("找不到该订单或者该订单不属于该客户");

        if (order.getOrderState() != Const.SelfOrder.REIVER)
            return ServerResponse.createByErrorMessage("该订单状态不可拒绝");

        order.setRefuseDec(order.getRefuseDec() == null ? refuseDec : order.getRefuseDec() + "---" + refuseDec);
        order.setOrderState(Const.SelfOrder.CAUGHT);
        int row = orderMapper.updateByPrimaryKeySelective(order);
        if (row > 0) {
            EngineerInfo engineerInfo = engineerInfoMapper.selectByPrimaryKey(order.getEngineerId());
            Timer timer = new Timer();
            TimerEmailCaughtOrder caughtOrder = new TimerEmailCaughtOrder(engineerInfo.getEmail(), "您的自建订单已被客户拒绝,拒绝原因：" + refuseDec);
            timer.schedule(caughtOrder, Const.TIMER_FOR_SEND_EMAIL);

            return ServerResponse.createBySuccess("拒绝成功");
        }

        return ServerResponse.createByErrorMessage("拒绝失败");
    }

    @Override
    public ServerResponse comfirmOrder(Integer orderId) {
        if (orderId == null)
            return ServerResponse.createByErrorMessage("参数为空");

        SelfOrder order = orderMapper.selectByPrimaryKey(orderId);
        if (order == null)
            return ServerResponse.createByErrorMessage("找不到该订单");

        if (order.getOrderState() != Const.SelfOrder.REIVER)
            return ServerResponse.createByErrorMessage("当前订单状态不可确认");


        order.setOrderState(Const.SelfOrder.FINISHED);

        EngineerInfo engineerInfo = engineerInfoMapper.selectByPrimaryKey(order.getEngineerId());
        List<SelfQuantityInfo> list = quantityInfoMapper.selectByQuantiy(engineerInfo.getEngineerQuantity());
        SelfQuantityInfo maxQuantity = list.get(0);
        BigDecimal price = order.getOrderPrice();
        price = BigDecimalUtil.sub(price.doubleValue(), BigDecimalUtil.mul(price.doubleValue(), maxQuantity.getQuantityDraw().doubleValue()).doubleValue());
        engineerInfo.setEngineerBalance(BigDecimalUtil.add(price.doubleValue(), engineerInfo.getEngineerBalance().doubleValue()));
        engineerInfoMapper.updateByPrimaryKey(engineerInfo);


        //个人账单
        BillInfo billInfo = new BillInfo();
        billInfo.setUserId(engineerInfo.getEngineerId());
        billInfo.setBillMoney(price);
        billInfo.setUserName(engineerInfo.getEngineerName());
        billInfo.setUserType(1);
        billInfo.setBillDec("自建订单收入 :" + order.getOrderId());
        billInfoMapper.insert(billInfo);

        //系统收入
        IncomeInfo incomeInfo = new IncomeInfo();
        incomeInfo.setOrderId(orderId);
        incomeInfo.setIncomeMoney(BigDecimalUtil.sub(order.getOrderPrice().doubleValue(), price.doubleValue()));
        incomeInfoMapper.insert(incomeInfo);


        order.setAdminPrice(incomeInfo.getIncomeMoney());
        orderMapper.updateByPrimaryKeySelective(order);

        Timer timer = new Timer();
        TimerEmailCaughtOrder caughtOrder = new TimerEmailCaughtOrder(engineerInfo.getEmail(), "您的自建订单已被客户确认，金额已经转入您的账户");
        timer.schedule(caughtOrder, Const.TIMER_FOR_SEND_EMAIL);
        return ServerResponse.createBySuccess("确认成功");
    }

    @Override
    public ServerResponse addMoneyToSelfOrderForBalance(BigDecimal money, Integer orderId) {
        if (orderId == null || money == null)
            return ServerResponse.createByErrorMessage("参数为空");

        SelfOrder order = orderMapper.selectByPrimaryKey(orderId);
        if (order == null)
            return ServerResponse.createByErrorMessage("找不到该订单");

        CustomerInfo customerInfo = customerInfoMapper.selectByPrimaryKey(order.getCustomerId());
        if (customerInfo.getCustomerBalance().doubleValue() < money.doubleValue())
            return ServerResponse.createByErrorMessage("账户余额不足");

        order.setOrderPrice(BigDecimalUtil.add(order.getOrderPrice().doubleValue(), money.doubleValue()));
        orderMapper.updateByPrimaryKeySelective(order);

        customerInfo.setCustomerBalance(BigDecimalUtil.sub(customerInfo.getCustomerBalance().doubleValue(), money.doubleValue()));
        customerInfoMapper.updateByPrimaryKeySelective(customerInfo);


        //个人账单
        BillInfo billInfo = new BillInfo();
        billInfo.setUserId(customerInfo.getCustomerId());
        billInfo.setBillMoney(BigDecimalUtil.sub(money.doubleValue(),BigDecimalUtil.add(money.doubleValue()
                ,money.doubleValue()).doubleValue()));
        billInfo.setUserName(customerInfo.getCustomerName());
        billInfo.setUserType(0);
        billInfo.setBillDec("自建订单加钱 :" + order.getOrderId());
        billInfoMapper.insert(billInfo);

        Timer timer = new Timer();
        TimerEmailCaughtOrder caughtOrder = new TimerEmailCaughtOrder(customerInfo.getEmail(), "您的自建订单已被客户加钱");
        timer.schedule(caughtOrder, Const.TIMER_FOR_SEND_EMAIL);


        return ServerResponse.createBySuccess("加钱成功");
    }

    @Override
    public ServerResponse chooseDeduct(Integer orderId, Integer priceDeductId, String orderDeductDec) {
        if (orderId == null || priceDeductId == null || orderDeductDec == null)
            return ServerResponse.createByErrorMessage("参数不能为空");

        SelfOrder order = orderMapper.selectByPrimaryKey(orderId);
        if (order == null)
            return ServerResponse.createByErrorMessage("找不到该订单");

        if (order.getOrderState() != Const.SelfOrder.REIVER)
            return ServerResponse.createByErrorMessage("该订单状态不能选择扣款");
        PriceDeductInfo priceDeductInfo = priceDeductInfoMapper.selectByPrimaryKey(priceDeductId);
        if (priceDeductInfo == null)
            return ServerResponse.createByErrorMessage("找不到扣款等级");


        order.setOrderState(Const.SelfOrder.DEDUCT);
        order.setOrderDeductRank(priceDeductInfo.getPriceDeductRank());
        order.setOrderDeductDec(orderDeductDec);
        int row = orderMapper.updateByPrimaryKeySelective(order);
        if (row > 0) {
            EngineerInfo engineerInfo = engineerInfoMapper.selectByPrimaryKey(order.getEngineerId());
            Timer timer = new Timer();
            TimerEmailCaughtOrder caughtOrder = new TimerEmailCaughtOrder(engineerInfo.getEmail(), "您的自建订单被客户扣款，请您速去确认，如有问题可以点击投诉");
            timer.schedule(caughtOrder, Const.TIMER_FOR_SEND_EMAIL);

            return ServerResponse.createBySuccess("选择扣款成功");
        }
        return ServerResponse.createByErrorMessage("扣款失败");
    }

    @Override
    public ServerResponse engineerComfirmDeductOrComplain(Integer orderId, Integer comfirmOrComplain) {
        if (orderId == null || comfirmOrComplain == null)
            return ServerResponse.createByErrorMessage("参数为空");

        SelfOrder order = orderMapper.selectByPrimaryKey(orderId);
        if (order == null)
            return ServerResponse.createByErrorMessage("找不到该订单");

        if (order.getOrderState() != Const.SelfOrder.DEDUCT)
            return ServerResponse.createByErrorMessage("该订单状态有误");

        if (comfirmOrComplain == 0) {
            order.setOrderState(Const.SelfOrder.FINISHED);

            PriceDeductInfo priceDeductInfo = priceDeductInfoMapper.selectByPriceDeductRank(order.getOrderDeductRank());
            BigDecimal customerDeductPrice = BigDecimalUtil.mul(order.getOrderPrice().doubleValue(), priceDeductInfo.getPriceDeductPercentage().doubleValue());
            CustomerInfo customerInfo = customerInfoMapper.selectByPrimaryKey(order.getCustomerId());
            customerInfo.setCustomerBalance(BigDecimalUtil.add(customerInfo.getCustomerBalance().doubleValue(), customerDeductPrice.doubleValue()));
            customerInfoMapper.updateByPrimaryKeySelective(customerInfo);

            BillInfo billInfo = new BillInfo();
            billInfo.setUserId(customerInfo.getCustomerId());
            billInfo.setBillMoney(customerDeductPrice);
            billInfo.setUserName(customerInfo.getCustomerName());
            billInfo.setUserType(0);
            billInfo.setBillDec("自建订单收入 :" + order.getOrderId());
            billInfoMapper.insert(billInfo);


            EngineerInfo dbengineerInfo = engineerInfoMapper.selectByPrimaryKey(order.getEngineerId());
            List<SelfQuantityInfo> list = quantityInfoMapper.selectByQuantiy(dbengineerInfo.getEngineerQuantity());
            SelfQuantityInfo maxQuantity = list.get(0);
            BigDecimal orderCamPrice = BigDecimalUtil.sub(order.getOrderPrice().doubleValue(), customerDeductPrice.doubleValue());
            orderCamPrice = BigDecimalUtil.sub(orderCamPrice.doubleValue(), BigDecimalUtil.mul(orderCamPrice.doubleValue(), maxQuantity.getQuantityDraw().doubleValue()).doubleValue());
            dbengineerInfo.setEngineerBalance(BigDecimalUtil.add(orderCamPrice.doubleValue(), dbengineerInfo.getEngineerBalance().doubleValue()));
            engineerInfoMapper.updateByPrimaryKey(dbengineerInfo);

            BillInfo billInfoEngineer = new BillInfo();
            billInfoEngineer.setUserId(dbengineerInfo.getEngineerId());
            billInfoEngineer.setBillMoney(orderCamPrice);
            billInfoEngineer.setUserName(dbengineerInfo.getEngineerName());
            billInfoEngineer.setUserType(1);
            billInfoEngineer.setBillDec("自建订单收入 :" + order.getOrderId());
            billInfoMapper.insert(billInfoEngineer);

            BigDecimal allPrice = BigDecimalUtil.add(customerDeductPrice.doubleValue(), orderCamPrice.doubleValue());
            IncomeInfo incomeInfo = new IncomeInfo();
            incomeInfo.setOrderId(orderId);
            incomeInfo.setIncomeMoney(BigDecimalUtil.sub(order.getOrderPrice().doubleValue(), allPrice.doubleValue()));
            incomeInfoMapper.insert(incomeInfo);

            order.setAdminPrice(BigDecimalUtil.sub(order.getOrderPrice().doubleValue(), allPrice.doubleValue()));
            orderMapper.updateByPrimaryKeySelective(order);

            return ServerResponse.createBySuccess("接受成功");
        }

        if (comfirmOrComplain == 1) {
            order.setOrderState(Const.SelfOrder.COMPLAIN);
            int row = orderMapper.updateByPrimaryKeySelective(order);
            if (row > 0)
                return ServerResponse.createBySuccess("投诉成功");
            else
                return ServerResponse.createByErrorMessage("投诉失败");
        }

        return null;
    }

    @Override
    public ServerResponse adminDeal(Integer orderId, BigDecimal customerPrice, BigDecimal engineerPrice) {
        if (orderId == null || customerPrice == null || engineerPrice == null)
            return ServerResponse.createByErrorMessage("参数为空");

        SelfOrder order = orderMapper.selectByPrimaryKey(orderId);
        if (order == null)
            return ServerResponse.createByErrorMessage("找不到该订单");

        if (order.getOrderState() == Const.SelfOrder.FINISHED || order.getOrderState() == Const.SelfOrder.CANNCEL || order.getOrderState() == Const.SelfOrder.CREATE || order.getOrderState() == Const.SelfOrder.PAID)
            return ServerResponse.createByErrorMessage("异常操作");

        BigDecimal allPrice = BigDecimalUtil.add(customerPrice.doubleValue(), engineerPrice.doubleValue());
        if (allPrice.doubleValue() > order.getOrderPrice().doubleValue()) {
            return ServerResponse.createByErrorMessage("价格不合理");
        }

        order.setOrderState(Const.SelfOrder.FINISHED);

        CustomerInfo customerInfo = customerInfoMapper.selectByPrimaryKey(order.getCustomerId());
        customerInfo.setCustomerBalance(BigDecimalUtil.add(customerPrice.doubleValue(), customerInfo.getCustomerBalance().doubleValue()));
        customerInfoMapper.updateByPrimaryKeySelective(customerInfo);

        BillInfo customerBillInfo = new BillInfo();
        customerBillInfo.setUserType(0);
        customerBillInfo.setUserName(customerInfo.getCustomerName());
        customerBillInfo.setBillMoney(customerPrice);
        customerBillInfo.setUserId(customerInfo.getCustomerId());
        customerBillInfo.setBillDec("自建订单收入 :" + order.getOrderId());
        billInfoMapper.insert(customerBillInfo);

        EngineerInfo engineerInfo = engineerInfoMapper.selectByPrimaryKey(order.getEngineerId());
        engineerInfo.setEngineerBalance(BigDecimalUtil.add(engineerInfo.getEngineerBalance().doubleValue(), engineerPrice.doubleValue()));
        engineerInfoMapper.updateByPrimaryKeySelective(engineerInfo);

        BillInfo engineerBillInfo = new BillInfo();
        engineerBillInfo.setUserType(1);
        engineerBillInfo.setUserName(engineerInfo.getEngineerName());
        engineerBillInfo.setBillMoney(engineerPrice);
        engineerBillInfo.setUserId(engineerInfo.getEngineerId());
        engineerBillInfo.setBillDec("自建订单收入 :" + order.getOrderId());
        billInfoMapper.insert(engineerBillInfo);

        IncomeInfo incomeInfo = new IncomeInfo();
        incomeInfo.setIncomeMoney(BigDecimalUtil.sub(order.getOrderPrice().doubleValue(), allPrice.doubleValue()));
        incomeInfo.setOrderId(orderId);
        incomeInfoMapper.insert(incomeInfo);

        order.setAdminPrice(BigDecimalUtil.sub(order.getOrderPrice().doubleValue(), allPrice.doubleValue()));

        orderMapper.updateByPrimaryKeySelective(order);
        return ServerResponse.createBySuccess("操作成功");
    }

    @Override
    public ServerResponse adminCancleSelfOrder(Integer orderId) {
        if (orderId == null)
            return ServerResponse.createByErrorMessage("参数为空");

        SelfOrder order = orderMapper.selectByPrimaryKey(orderId);
        if (order == null)
            return ServerResponse.createByErrorMessage("找不到该订单");

        if (order.getOrderState() == Const.SelfOrder.FINISHED || order.getOrderState() == Const.SelfOrder.CANNCEL ||  order.getOrderState() == Const.SelfOrder.PAID)
            return ServerResponse.createByErrorMessage("异常操作");


        CustomerInfo customerInfo = customerInfoMapper.selectByPrimaryKey(order.getCustomerId());
        customerInfo.setCustomerBalance(BigDecimalUtil.add(order.getOrderPrice().doubleValue(), customerInfo.getCustomerBalance().doubleValue()));
        customerInfoMapper.updateByPrimaryKeySelective(customerInfo);
        BillInfo customerBillInfo = new BillInfo();
        customerBillInfo.setUserType(0);
        customerBillInfo.setUserName(customerInfo.getCustomerName());
        customerBillInfo.setBillMoney(order.getOrderPrice());
        customerBillInfo.setUserId(customerInfo.getCustomerId());
        customerBillInfo.setBillDec("自建订单收入 :" + order.getOrderId());
        billInfoMapper.insert(customerBillInfo);


        order.setOrderState(Const.SelfOrder.CANNCEL);
        orderMapper.updateByPrimaryKeySelective(order);
        return ServerResponse.createBySuccess("操作成功");
    }

    @Override
    public ServerResponse adminCancleToCreate(Integer orderId) {
        if (orderId == null)
            return ServerResponse.createByErrorMessage("参数为空");

        SelfOrder order = orderMapper.selectByPrimaryKey(orderId);
        if (order == null)
            return ServerResponse.createByErrorMessage("找不到该订单");

        if (order.getOrderState() == Const.SelfOrder.FINISHED || order.getOrderState() == Const.SelfOrder.CANNCEL ||  order.getOrderState() == Const.SelfOrder.CREATE)
            return ServerResponse.createByErrorMessage("异常操作");

        order.setOrderState(Const.SelfOrder.PAID);
        order.setEngineerId(null);
        order.setRefuseDec(null);
        order.setEngineerName(null);
        int row = orderMapper.updateByPrimaryKey(order);
        if (row > 0)
            return ServerResponse.createBySuccess("操作成功");

        return ServerResponse.createByErrorMessage("操作失败");
    }

    @Override
    public ServerResponse adminFinishSelfOrder(Integer orderId) {
        if (orderId == null)
            return ServerResponse.createByErrorMessage("参数为空");

        SelfOrder order = orderMapper.selectByPrimaryKey(orderId);
        if (order == null)
            return ServerResponse.createByErrorMessage("找不到该订单");

        if (order.getOrderState() == Const.SelfOrder.FINISHED || order.getOrderState() == Const.SelfOrder.CANNCEL || order.getOrderState() == Const.SelfOrder.CREATE || order.getOrderState() == Const.SelfOrder.PAID)
            return ServerResponse.createByErrorMessage("异常操作");

        order.setOrderState(Const.SelfOrder.FINISHED);

        EngineerInfo engineerInfo = engineerInfoMapper.selectByPrimaryKey(order.getEngineerId());
        List<SelfQuantityInfo> list = quantityInfoMapper.selectByQuantiy(engineerInfo.getEngineerQuantity());
        SelfQuantityInfo maxQuantity = list.get(0);
        BigDecimal price = order.getOrderPrice();
        price = BigDecimalUtil.sub(price.doubleValue(), BigDecimalUtil.mul(price.doubleValue(), maxQuantity.getQuantityDraw().doubleValue()).doubleValue());
        engineerInfo.setEngineerBalance(BigDecimalUtil.add(price.doubleValue(), engineerInfo.getEngineerBalance().doubleValue()));
        engineerInfoMapper.updateByPrimaryKey(engineerInfo);

        //个人账单
        BillInfo billInfo = new BillInfo();
        billInfo.setUserId(engineerInfo.getEngineerId());
        billInfo.setBillMoney(price);
        billInfo.setUserName(engineerInfo.getEngineerName());
        billInfo.setUserType(1);
        billInfo.setBillDec("自建订单收入 :" + order.getOrderId());
        billInfoMapper.insert(billInfo);

        //系统收入
        IncomeInfo incomeInfo = new IncomeInfo();
        incomeInfo.setOrderId(orderId);
        incomeInfo.setIncomeMoney(BigDecimalUtil.sub(order.getOrderPrice().doubleValue(), price.doubleValue()));
        incomeInfoMapper.insert(incomeInfo);


        order.setAdminPrice(incomeInfo.getIncomeMoney());
        orderMapper.updateByPrimaryKeySelective(order);

        Timer timer = new Timer();
        TimerEmailCaughtOrder caughtOrder = new TimerEmailCaughtOrder(engineerInfo.getEmail(), "您的自建订单已被确认，金额已经转入您的账户");
        timer.schedule(caughtOrder, Const.TIMER_FOR_SEND_EMAIL);
        return ServerResponse.createBySuccess("确认成功");
    }

    @Override
    public ServerResponse<PageInfo> customerList(int pageSize, int pageNum, CustomerInfo customerInfo, Integer orderState) {
        PageHelper.startPage(pageNum, pageSize);
        PageHelper.orderBy("order_id desc");
        List<SelfOrder> list = orderMapper.customerListOrder(customerInfo.getCustomerId(), orderState);
        List<SelfOrderAndMessagenumVo> listVo = new ArrayList<>();
        for (SelfOrder order : list){
            SelfOrderAndMessagenumVo selfOrderAndMessagenumVo = new SelfOrderAndMessagenumVo();
            BeanUtils.copyProperties(order,selfOrderAndMessagenumVo);
            Integer num = selfOrderAnsqueMapper.getUnReadNum(order.getOrderId(), 0);
            selfOrderAndMessagenumVo.setUnReadMessage(num);
            listVo.add(selfOrderAndMessagenumVo);
        }
        PageInfo pageInfo = new PageInfo(list);
        pageInfo.setList(listVo);
        return ServerResponse.createBySuccess(pageInfo);
    }

    @Override
    public XSSFWorkbook customerOrEngineerExportExcelInfo(Integer type, Integer id, String startTime, String endTime) {
        if (startTime == null || endTime == null)
            return null;
        if (type == 0) {
            try {
                Date startDate = DateTimeUtil.strToDate(startTime, "yyyy-MM-dd");
                Date endDate = DateTimeUtil.strToDate(endTime, "yyyy-MM-dd");
                List<SelfOrder> orderInfoList = orderMapper.selectByTimeAndType(startDate, endDate, id, null,  Const.SelfOrder.FINISHED);
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
                excel.add(new ExcelBean(" PCB种类", "orderFirstCategory", 0));
                excel.add(new ExcelBean("订单种类", "orderSelfCategory", 0));
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
                List<SelfOrder> orderInfoList = orderMapper.selectByTimeAndType(startDate, endDate, null,id,  Const.Order.HAVE_FINISHED);
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
                excel.add(new ExcelBean(" PCB种类","orderFirstCategory",0));
                excel.add(new ExcelBean("订单种类","orderSelfCategory",0));

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
    public ServerResponse engineerList(int pageSize, int pageNum, EngineerInfo engineerInfo, Integer orderState) {
        PageHelper.startPage(pageNum, pageSize);
        PageHelper.orderBy("order_id desc");
        List<SelfOrder> list = orderMapper.engineerListOrder(engineerInfo.getEngineerId(), orderState);
        List<SelfOrderAndMessagenumVo> listVo = new ArrayList<>();
        for (SelfOrder order : list){
            SelfOrderAndMessagenumVo selfOrderAndMessagenumVo = new SelfOrderAndMessagenumVo();
            BeanUtils.copyProperties(order,selfOrderAndMessagenumVo);
            Integer num = selfOrderAnsqueMapper.getUnReadNum(order.getOrderId(), 1);
            selfOrderAndMessagenumVo.setUnReadMessage(num);
            listVo.add(selfOrderAndMessagenumVo);
        }
        PageInfo pageInfo = new PageInfo(list);
        pageInfo.setList(listVo);
        return ServerResponse.createBySuccess(pageInfo);
    }

    @Override
    public ServerResponse engineerCanCaughtList(int pageSize, int pageNum, EngineerInfo engineerInfo) {

        List<SelfCategory> categoryList = categoryMapper.selectList();
        List<String> categories = new ArrayList<>();
        for (SelfCategory selfCategory : categoryList){
            if (engineerInfo.getEngineerRank() >= selfCategory.getRank())
                categories.add(selfCategory.getSelfCategoryName());
        }
        PageHelper.startPage(pageNum, pageSize);
        PageHelper.orderBy("order_id asc");

        if (categories.size() == 0)
            return ServerResponse.createBySuccess(new PageInfo());

        List<SelfOrder> list = orderMapper.engineerCanCaughtList(engineerInfo.getEngineerClassfy(), categories,Const.SelfOrder.PAID);
        PageInfo pageInfo = new PageInfo(list);

        return ServerResponse.createBySuccess(pageInfo);
    }


    @Override
    public ServerResponse addAnsOrQue(Integer orderId, Integer userId, Integer type, String userName, String orderAnsqueContent) {
        if (orderId == null || orderAnsqueContent == null)
            return ServerResponse.createByErrorMessage("参数为空");

        SelfOrder order = orderMapper.selectByPrimaryKey(orderId);
        if (order == null)
            return ServerResponse.createByErrorMessage("找不到该订单");


        if (type == 0 && userId.intValue() != order.getCustomerId().intValue())
            return ServerResponse.createByErrorMessage("您不属于该订单");

        if (type == 1 && userId.intValue() != order.getEngineerId().intValue()) {
                return ServerResponse.createByErrorMessage("您不属于该订单");
        }

        SelfOrderAnsque orderAnsqueInfo = new SelfOrderAnsque();
        orderAnsqueInfo.setOrderAnsqueContent(orderAnsqueContent);
        orderAnsqueInfo.setSelfOrderId(orderId);
        orderAnsqueInfo.setUserId(userId);
        orderAnsqueInfo.setUserName(userName);
        orderAnsqueInfo.setUserType(type);
        orderAnsqueInfo.setState(0);

        int row = selfOrderAnsqueMapper.insert(orderAnsqueInfo);
        if (row > 0)
            return ServerResponse.createBySuccess("操作成功");
        return ServerResponse.createByErrorMessage("操作失败");
    }

    @Override
    public ServerResponse uploadAnsOrQue(Integer orderId, Integer userId, Integer type, String userName, MultipartFile file, String path) {
        if (orderId == null || file == null)
            return ServerResponse.createByErrorMessage("参数为空");

        SelfOrder order = orderMapper.selectByPrimaryKey(orderId);
        if (order == null)
            return ServerResponse.createByErrorMessage("找不到该订单");

        if (type == 0 && userId.intValue() != order.getCustomerId().intValue())
            return ServerResponse.createByErrorMessage("您不属于该订单");

        if (type == 1 && userId.intValue() != order.getEngineerId().intValue()){
                return ServerResponse.createByErrorMessage("您不属于该订单");

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

        SelfOrderAnsque orderAnsqueInfo = new SelfOrderAnsque();
        orderAnsqueInfo.setOrderAnsqueContent(PropertiesUtil.getProperty("ftp.server.http.prefix") + targetFile.getName());
        orderAnsqueInfo.setSelfOrderId(orderId);
        orderAnsqueInfo.setUserId(userId);
        orderAnsqueInfo.setUserName(userName);
        orderAnsqueInfo.setUserType(type);
        orderAnsqueInfo.setState(0);

        int row = selfOrderAnsqueMapper.insert(orderAnsqueInfo);
        if (row > 0)
            return ServerResponse.createBySuccess("操作成功");
        return ServerResponse.createByErrorMessage("操作失败");
    }

    @Override
    public ServerResponse<PageInfo> listOrderAnsqueInfoByOrderId(int pageNum, int pageSize, Integer orderId, Integer userId, Integer type) {
        if (orderId == null)
            return ServerResponse.createByErrorMessage("参数为空");

        SelfOrder order = orderMapper.selectByPrimaryKey(orderId);
        if (order == null)
            return ServerResponse.createByErrorMessage("找不到该订单");

        if (type == 0 && userId.intValue() != order.getCustomerId().intValue())
            return ServerResponse.createByErrorMessage("您不属于该订单");

        if (type == 1 && userId.intValue() != order.getEngineerId().intValue()) {
            return ServerResponse.createByErrorMessage("您不属于该订单");

        }
        PageHelper.startPage(pageNum, pageSize);
        PageHelper.orderBy("create_time asc");
        List<SelfOrderAnsque> list = selfOrderAnsqueMapper.list(orderId);
        selfOrderAnsqueMapper.updateMessAgeState(orderId,type);
        PageInfo pageInfo = new PageInfo(list);
        return ServerResponse.createBySuccess(pageInfo);
    }

    @Override
    public ServerResponse<PageInfo> listOrderAnsqueInfoByOrderId(int pageNum, int pageSize, Integer orderId) {
        if (orderId == null)
            return ServerResponse.createByErrorMessage("参数为空");

        SelfOrder order = orderMapper.selectByPrimaryKey(orderId);
        if (order == null)
            return ServerResponse.createByErrorMessage("找不到该订单");

        PageHelper.startPage(pageNum, pageSize);
        PageHelper.orderBy("create_time asc");
        List<SelfOrderAnsque> list = selfOrderAnsqueMapper.list(orderId);
        PageInfo pageInfo = new PageInfo(list);
        return ServerResponse.createBySuccess(pageInfo);
    }

    @Override
    public XSSFWorkbook exportExcelInfo(String startTime, String endTime) {
        if (startTime == null || endTime == null)
            return null;
        try {
            //根据ID查找数据
            Date startDate = DateTimeUtil.strToDate(startTime, "yyyy-MM-dd");
            Date endDate = DateTimeUtil.strToDate(endTime, "yyyy-MM-dd");
            List<SelfOrder> orderInfoList = orderMapper.selectByTime(startDate, endDate);

            XSSFWorkbook xssfWorkbook = null;
            List<ExcelBean> excel = new ArrayList<>();
            Map<Integer, List<ExcelBean>> map = new LinkedHashMap<>();
            //设置标题栏
            excel.add(new ExcelBean("订单id", "orderId", 0));
            excel.add(new ExcelBean("订单金额", "orderPrice", 0));
            excel.add(new ExcelBean("平台抽成金额", "adminPrice", 0));
            excel.add(new ExcelBean("客户文件名", "orderCustomerFile", 0));
            excel.add(new ExcelBean("工程师完单文件", "orderFile", 0));
            excel.add(new ExcelBean("客户编号", "customerId", 0));
            excel.add(new ExcelBean("制作工程师编号", "engineerId", 0));
            excel.add(new ExcelBean(" PCB种类", "orderFirstCategory", 0));
            excel.add(new ExcelBean("订单种类", "orderSelfCategory", 0));
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
    public ServerResponse<PageInfo> adminOrderList(int pageSize, int pageNum, Integer state, Integer firstCategory, String selfCategoryName) {
        String orderFirstCategory = null;
        if (firstCategory != null) {
            if (firstCategory == 0)
                orderFirstCategory = "PCB";
            else
                orderFirstCategory = "FPC";
        }
        PageHelper.startPage(pageNum, pageSize);
        PageHelper.orderBy("order_id desc");
        List<SelfOrder> list = orderMapper.adminOrderList(state, orderFirstCategory, selfCategoryName);
        PageInfo pageInfo = new PageInfo(list);
        return ServerResponse.createBySuccess(pageInfo);
    }


    @Override
    public ServerResponse aliCallback(Map<String, String> params) {
        try {
            Integer orderId = Integer.parseInt(params.get("out_trade_no"));
            String tradeNo = params.get("trade_no");
            String tradeStatus = params.get("trade_status");
            SelfOrder order = orderMapper.selectByPrimaryKey(orderId);
            if (order == null) {
                logger.debug("非o2o的订单,回调忽略");
                return ServerResponse.createByErrorMessage("非o2o的订单,回调忽略");
            }
            if (order.getOrderState() >= Const.SelfOrder.PAID) {
                logger.debug("支付宝重复调用");
                return ServerResponse.createByErrorMessage("支付宝重复调用");
            }
            if (Const.AlipayCallback.TRADE_STATUS_TRADE_SUCCESS.equals(tradeStatus)) {
                order.setOrderState(Const.SelfOrder.PAID);
                orderMapper.updateByPrimaryKeySelective(order);
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
            payBillInfo.setBillDec("充值自建订单 :" + order.getOrderId());
            billInfoMapper.insert(payBillInfo);

            BillInfo billInfo = new BillInfo();
            billInfo.setUserId(customerInfo.getCustomerId());
            billInfo.setBillMoney(BigDecimalUtil.sub(order.getOrderPrice().doubleValue(), BigDecimalUtil.add(order.getOrderPrice().doubleValue(), order.getOrderPrice().doubleValue()).doubleValue()));
            billInfo.setUserName(customerInfo.getCustomerName());
            billInfo.setUserType(0);
            billInfo.setBillDec("支付自建订单 :" + order.getOrderId());
            billInfoMapper.insert(billInfo);
        } catch (Exception e) {
            logger.debug("支付宝验证回调异常", e);
        }

        return ServerResponse.createBySuccess();
    }
}
