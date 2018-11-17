package com.service;

import com.common.ServerResponse;
import com.github.pagehelper.PageInfo;
import com.pojo.BasicPriceInfo;
import com.pojo.EngineerRankInfo;

/**
 * Created by upupgogogo on 2018/11/12.下午5:03
 */
public interface BasicPriceInfoService {

    ServerResponse add(BasicPriceInfo basicPriceInfo);

    ServerResponse delete(Integer basicPriceId);

    ServerResponse update(BasicPriceInfo basicPriceInfo);

    ServerResponse<PageInfo> list(int pageSize, int pageNum);

    ServerResponse getById(Integer basicPriceId);
}
