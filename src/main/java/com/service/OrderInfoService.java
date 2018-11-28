package com.service;

import com.common.ServerResponse;
import com.github.pagehelper.PageInfo;
import com.pojo.CustomerInfo;
import com.pojo.EngineerInfo;
import com.pojo.OrderInfo;
import com.vo.EngineerRankVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * Created by upupgogogo on 2018/11/20.下午2:51
 */
public interface OrderInfoService {

    ServerResponse createOrder(OrderInfo order, CustomerInfo customerInfo, Integer params[],  Integer rushId);

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

    ServerResponse orderDeduct(Integer orderId, Integer priceDeductId);

    ServerResponse<PageInfo> engineerQaeCaughtList(int pageSize, int pageNum, EngineerRankVO engineerRankVO);



}
