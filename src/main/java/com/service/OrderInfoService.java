package com.service;

import com.common.ServerResponse;
import com.pojo.CustomerInfo;
import com.pojo.OrderInfo;

import java.util.Map;

/**
 * Created by upupgogogo on 2018/11/20.下午2:51
 */
public interface OrderInfoService {

    ServerResponse createOrder(OrderInfo order, CustomerInfo customerInfo, Integer params[],  Integer rushId);

    ServerResponse getOrderById(Integer orderId);

    ServerResponse checkState(Integer orderId, Map<String,String> params);
}
