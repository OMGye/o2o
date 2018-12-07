package com.service;

import com.common.ServerResponse;
import com.pojo.ProposeInfo;

/**
 * Created by upupgogogo on 2018/12/7.下午3:27
 */
public interface ProposeInfoService {


    ServerResponse add(ProposeInfo proposeInfo, Integer type, Integer userId);

    ServerResponse list(int pageSize, int pageNum);

    ServerResponse delete(Integer proposeId, Integer userId, Integer type);

    ServerResponse getById(Integer proposeId);
}
