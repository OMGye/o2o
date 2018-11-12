package com.service;

import com.common.ServerResponse;
import com.github.pagehelper.PageInfo;
import com.pojo.EngineerRankInfo;
import com.pojo.PriceTogetherInfo;

/**
 * Created by upupgogogo on 2018/11/12.下午7:22
 */
public interface PriceTogetherInfoService {

    ServerResponse add(PriceTogetherInfo priceTogetherInfo);

    ServerResponse delete(Integer priceTogetherId);

    ServerResponse update(PriceTogetherInfo priceTogetherInfo);

    ServerResponse<PageInfo> list(int pageSize, int pageNum);
}
