package com.service;

import com.common.ServerResponse;
import com.github.pagehelper.PageInfo;
import com.pojo.CustomerInfo;
import com.pojo.EngineerInfo;
import com.pojo.SelfOrder;
import com.vo.EngineerRankVO;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Created by upupgogogo on 2019/6/7.下午7:34
 */
public interface SelfOrderService {

    ServerResponse createOrder(SelfOrder order, CustomerInfo customerInfo, MultipartFile file, String path);

    ServerResponse payForBalance(Integer orderId, Integer customerId);

    ServerResponse getOrderById(Integer orderId);

    ServerResponse caughtOrder(Integer orderId, EngineerInfo engineerInfo);

    ServerResponse orderUploadFile(Integer orderId, EngineerInfo engineerInfo, MultipartFile file, String path);

    ServerResponse receiveOrder(Integer orderId, CustomerInfo customerInfo);

    ServerResponse refuseToEnigneer(String refuseDec, Integer orderId, Integer customerId);

    ServerResponse comfirmOrder(Integer orderId);


    ServerResponse addMoneyToSelfOrderForBalance(BigDecimal money, Integer orderId);

    ServerResponse chooseDeduct(Integer orderId, Integer priceDeductId, String orderDeductDec);

    ServerResponse engineerComfirmDeductOrComplain(Integer orderId, Integer comfirmOrComplain);

    ServerResponse adminDeal(Integer orderId, BigDecimal customerPrice, BigDecimal engineerPrice);

    ServerResponse adminCancleSelfOrder(Integer orderId);

    ServerResponse adminCancleToCreate(Integer orderId);

    ServerResponse adminFinishSelfOrder(Integer orderId);

    ServerResponse<PageInfo> customerList(int pageSize, int pageNum, CustomerInfo customerInfo, Integer orderState);

    XSSFWorkbook customerOrEngineerExportExcelInfo(Integer type, Integer id, String startTime, String endTime);

}
