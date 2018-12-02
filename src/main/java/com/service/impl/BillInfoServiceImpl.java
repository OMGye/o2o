package com.service.impl;

import com.common.ServerResponse;
import com.dao.BillInfoMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pojo.BasicPriceInfo;
import com.pojo.BillInfo;
import com.service.BillInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by upupgogogo on 2018/12/2.下午3:40
 */
@Service("billInfoService")
public class BillInfoServiceImpl implements BillInfoService{

    @Autowired
    private BillInfoMapper billInfoMapper;

    @Override
    public ServerResponse<PageInfo> list(int pageSize, int pageNum, Integer userId, Integer userType) {
        PageHelper.startPage(pageNum,pageSize);
        PageHelper.orderBy("create_time desc");
        List<BillInfo> list = billInfoMapper.list(userId,userType);
        PageInfo pageInfo = new PageInfo(list);
        return ServerResponse.createBySuccess(pageInfo);
    }
}
