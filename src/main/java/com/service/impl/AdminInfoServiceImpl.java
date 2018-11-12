package com.service.impl;

import com.common.ServerResponse;
import com.dao.AdminInfoMapper;
import com.pojo.AdminInfo;
import com.service.AdminInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by upupgogogo on 2018/11/12.下午4:10
 */
@Service("adminInfoService")
public class AdminInfoServiceImpl implements AdminInfoService {

    @Autowired
    private AdminInfoMapper adminInfoMapper;

    @Override
    public ServerResponse<AdminInfo> login(AdminInfo adminInfo) {
        if (adminInfo == null || adminInfo.getAdminName() == null || adminInfo.getPassword() == null)
            return ServerResponse.createByErrorMessage("您未输入账户或密码");
        AdminInfo curAdminInfo = adminInfoMapper.selectByUserNameAndPassword(adminInfo.getAdminName(),adminInfo.getPassword());
        if (curAdminInfo == null)
            return ServerResponse.createByErrorMessage("账号或密码错误");

        return ServerResponse.createBySuccess(curAdminInfo);
    }
}
