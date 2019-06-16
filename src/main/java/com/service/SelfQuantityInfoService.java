package com.service;

import com.common.ServerResponse;
import com.github.pagehelper.PageInfo;
import com.pojo.QuantityInfo;
import com.pojo.SelfQuantityInfo;

/**
 * Created by upupgogogo on 2018/11/14.下午2:41
 */
public interface SelfQuantityInfoService {

    ServerResponse add(SelfQuantityInfo quantityInfo);

    ServerResponse delete(Integer quantityId);

    ServerResponse update(SelfQuantityInfo quantityInfo);

    ServerResponse<PageInfo> list(int pageSize, int pageNum);

    ServerResponse getById(Integer quantityId);
}
