package com.service;

import com.common.ServerResponse;
import com.github.pagehelper.PageInfo;
import com.pojo.O2oInfo;
import com.pojo.QuantityInfo;

/**
 * Created by upupgogogo on 2018/11/14.下午2:41
 */
public interface QuantityInfoService {

    ServerResponse add(QuantityInfo quantityInfo);

    ServerResponse delete(Integer quantityId);

    ServerResponse update(QuantityInfo quantityInfo);

    ServerResponse<PageInfo> list(int pageSize, int pageNum);
}
