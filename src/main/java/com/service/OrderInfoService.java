package com.service;

import com.common.ServerResponse;
import com.github.pagehelper.PageInfo;
import com.pojo.CustomerInfo;
import com.pojo.OrderInfo;
import com.vo.EngineerRankVO;

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
}
