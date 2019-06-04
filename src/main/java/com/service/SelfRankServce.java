package com.service;

import com.common.ServerResponse;
import com.github.pagehelper.PageInfo;
import com.pojo.SelfCategory;
import com.pojo.SelfRank;

/**
 * Created by upupgogogo on 2019/5/19.下午2:46
 */
public interface SelfRankServce {

    ServerResponse add(SelfRank selfRank);

    ServerResponse delete(Integer selfRankId);

    ServerResponse update(SelfRank selfRank);

    ServerResponse<PageInfo> list(int pageSize, int pageNum);

    ServerResponse getById(Integer selfRankId);
}
