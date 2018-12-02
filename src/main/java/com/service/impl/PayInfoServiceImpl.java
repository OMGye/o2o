package com.service.impl;

import com.common.ServerResponse;
import com.dao.PayInfoMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pojo.BillInfo;
import com.pojo.PayInfo;
import com.service.PayInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by upupgogogo on 2018/12/2.下午3:51
 */
@Service("payInfoService")
public class PayInfoServiceImpl implements PayInfoService{

    @Autowired
    private PayInfoMapper payInfoMapper;
    @Override
    public ServerResponse<PageInfo> list(int pageSize, int pageNum, Integer userId, String userName) {
        if (userName != null)
            userName = "%" + userName + "%";
        PageHelper.startPage(pageNum,pageSize);
        PageHelper.orderBy("create_time desc");
        List<PayInfo> list = payInfoMapper.list(userId,userName);
        PageInfo pageInfo = new PageInfo(list);
        return ServerResponse.createBySuccess(pageInfo);
    }
}
