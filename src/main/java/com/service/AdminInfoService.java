package com.service;

import com.common.ServerResponse;
import com.pojo.AdminInfo;

/**
 * Created by upupgogogo on 2018/11/12.下午4:07
 */
public interface AdminInfoService {

    ServerResponse<AdminInfo> login(AdminInfo adminInfo);
}
