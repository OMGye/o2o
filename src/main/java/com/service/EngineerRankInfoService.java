package com.service;

import com.common.ServerResponse;
import com.github.pagehelper.PageInfo;
import com.pojo.EngineerRankInfo;

/**
 * Created by upupgogogo on 2018/11/12.下午3:25
 */
public interface EngineerRankInfoService {

    ServerResponse add(EngineerRankInfo engineerRankInfo);

    ServerResponse delete(Integer engineerRankId);

    ServerResponse update(EngineerRankInfo engineerRankInfo);

    ServerResponse<PageInfo> list(int pageSize, int pageNum);

    ServerResponse getById(Integer engineerRankId);

    ServerResponse getEngineerRank(Integer engineerRankId);
}
