package com.service;

import com.common.ServerResponse;
import com.github.pagehelper.PageInfo;

/**
 * Created by upupgogogo on 2018/12/2.下午4:01
 */
public interface IncomeInfoService {

    ServerResponse<PageInfo> list(int pageSize, int pageNum, Integer orderId);
}
