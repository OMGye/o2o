package com.service;

import com.common.ServerResponse;
import com.github.pagehelper.PageInfo;
import com.pojo.EngineerRankInfo;
import com.pojo.O2oInfo;

/**
 * Created by upupgogogo on 2018/11/13.下午6:08
 */
public interface O2oInfoService {
    ServerResponse add(O2oInfo o2oInfo);

    ServerResponse delete(Integer o2oId);

    ServerResponse update(O2oInfo o2oInfo);

    ServerResponse<PageInfo> list(int pageSize, int pageNum);
}
