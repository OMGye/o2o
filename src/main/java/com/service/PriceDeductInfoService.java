package com.service;

import com.common.ServerResponse;
import com.github.pagehelper.PageInfo;
import com.pojo.PriceDeductInfo;
import com.pojo.PriceTogetherInfo;

/**
 * Created by upupgogogo on 2018/11/13.下午5:19
 */
public interface PriceDeductInfoService {

    ServerResponse add(PriceDeductInfo priceDeductInfo);

    ServerResponse delete(Integer priceDeductId);

    ServerResponse update(PriceDeductInfo priceDeductInfo);

    ServerResponse<PageInfo> list(int pageSize, int pageNum);

    ServerResponse getById(Integer priceDeductId);
}
