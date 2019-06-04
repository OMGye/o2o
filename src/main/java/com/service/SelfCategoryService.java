package com.service;

import com.common.ServerResponse;
import com.github.pagehelper.PageInfo;
import com.pojo.QuantityInfo;
import com.pojo.SelfCategory;

/**
 * Created by upupgogogo on 2019/5/19.下午2:28
 */
public interface SelfCategoryService {

    ServerResponse add(SelfCategory selfCategory);

    ServerResponse delete(Integer selfCategoryId);

    ServerResponse update(SelfCategory selfCategory);

    ServerResponse<PageInfo> list(int pageSize, int pageNum);

    ServerResponse getById(Integer selfCategoryId);
}
