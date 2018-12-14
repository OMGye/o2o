package com.service;

import com.common.ServerResponse;
import com.github.pagehelper.PageInfo;

/**
 * Created by upupgogogo on 2018/12/2.下午3:39
 */
public interface BillInfoService {

    ServerResponse<PageInfo> list(int pageSize, int pageNum, Integer userId, Integer userType);

    ServerResponse<PageInfo> selectByIdLike(int pageNum, int pageSize, Integer billId);
}
