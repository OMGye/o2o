package com.service;

import com.common.ServerResponse;
import com.github.pagehelper.PageInfo;
import com.pojo.OtherParamInfo;
import com.pojo.PriceDeductInfo;

/**
 * Created by upupgogogo on 2018/11/13.下午5:52
 */
public interface OtherParamInfoService {

    ServerResponse add(OtherParamInfo otherParamInfo);

    ServerResponse delete(Integer paramId);

    ServerResponse update(OtherParamInfo otherParamInfo);

    ServerResponse<PageInfo> list(int pageSize, int pageNum);
}
