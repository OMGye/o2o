package com.service;

import com.common.ServerResponse;
import com.github.pagehelper.PageInfo;

/**
 * Created by upupgogogo on 2018/12/2.下午3:49
 */
public interface PayInfoService {

    ServerResponse<PageInfo> list(int pageSize, int pageNum, Integer userId, String userName);
}
