package com.service;

import com.common.ServerResponse;
import com.github.pagehelper.PageInfo;
import com.pojo.CustomerInfo;
import com.pojo.EngineerInfo;
import com.pojo.OrderInfo;
import com.vo.EngineerRankVO;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Created by upupgogogo on 2018/11/20.下午2:51
 */
public interface OrderInfoService {

    ServerResponse createOrder(OrderInfo order, CustomerInfo customerInfo, Integer params[],  Integer rushId, MultipartFile file, String path);

    ServerResponse getOrderById(Integer orderId);

    ServerResponse aliCallback(Map<String,String> params);

    ServerResponse<PageInfo> customerList(int pageSize, int pageNum, CustomerInfo customerInfo, Integer orderState);


    ServerResponse<PageInfo> engineerCaughtList(int pageSize, int pageNum, EngineerRankVO engineerRankVO);


    ServerResponse caughtCamOrder(Integer orderId, EngineerRankVO engineerRankVO, EngineerInfo engineerInfo);

    ServerResponse orderUploadFile(Integer orderId, EngineerInfo engineerInfo, MultipartFile file, String path);

    ServerResponse receiveOrder(Integer orderId, CustomerInfo customerInfo);

    ServerResponse refuseToEnigneer(String refuseDec, Integer orderId, Integer customerId);

    ServerResponse comfirmOrder(Integer orderId, Integer customerId);

    ServerResponse engineerList(int pageSize, int pageNum, EngineerInfo engineerInfo, Integer orderState);

    ServerResponse engineerQaeList(int pageSize, int pageNum, EngineerInfo engineerInfo, Integer orderState);

    ServerResponse orderDeduct(Integer orderId, Integer priceDeductId, String orderDeductDec, CustomerInfo customerInfo);

    ServerResponse<PageInfo> engineerQaeCaughtList(int pageSize, int pageNum, EngineerRankVO engineerRankVO);

    ServerResponse caughtQaeOrder(Integer orderId, EngineerRankVO engineerRankVO, EngineerInfo engineerInfo);

    ServerResponse qaeCheck(Integer orderId, Integer state, String refuseDec, EngineerInfo engineerInfo);

    ServerResponse customerCancleOrder(Integer orderId, CustomerInfo customerInfo);

    ServerResponse engineerCancleOrder(Integer orderId, EngineerInfo engineerInfo);

    ServerResponse engineerCheckCancleOrder(Integer orderId, EngineerInfo engineerInfo);

    ServerResponse engineerComfirmDeductOrComplain(Integer orderId, EngineerInfo engineerInfo, Integer comfirmOrComplain, String dec);

    ServerResponse engineerCheckComfirmDeductOrComplain(Integer orderId, EngineerInfo engineerInfo, Integer comfirmOrComplain, String dec);

    ServerResponse<PageInfo> adminOrderList(int pageSize, int pageNum, Integer state, Integer firstCategory, Integer orderQae);

    ServerResponse dealOrder(Integer orderId, BigDecimal customerPrice, BigDecimal engineerPrice, BigDecimal engineerQaePrice);


    ServerResponse addAnsOrQue(Integer orderId, Integer userId, Integer type, String userName, String orderAnsqueContent);

    ServerResponse uploadAnsOrQue(Integer orderId, Integer userId, Integer type, String userName, MultipartFile file, String path);

    ServerResponse<PageInfo> listOrderAnsqueInfoByOrderId(int pageNum, int pageSize, Integer orderId, Integer userId, Integer type);

    ServerResponse<PageInfo> listOrderAnsqueInfoByOrderId(int pageNum, int pageSize, Integer orderId);

    XSSFWorkbook exportExcelInfo(String startTime, String endTime);

    XSSFWorkbook customerOrEngineerExportExcelInfo(Integer type, Integer id, String startTime, String endTime);


    ServerResponse<PageInfo> selectByIdLike(int pageNum, int pageSize, Integer orderId);

    ServerResponse<PageInfo> selectByOtherIdLike(int pageNum, int pageSize, Integer id, Integer type);


    ServerResponse getAllPrice();

}
