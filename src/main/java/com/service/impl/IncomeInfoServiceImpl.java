package com.service.impl;

import com.common.ServerResponse;
import com.dao.IncomeInfoMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pojo.BillInfo;
import com.pojo.IncomeInfo;
import com.service.IncomeInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by upupgogogo on 2018/12/2.下午4:02
 */
@Service("incomeInfoService")
public class IncomeInfoServiceImpl implements IncomeInfoService{

    @Autowired
    private IncomeInfoMapper incomeInfoMapper;

    @Override
    public ServerResponse<PageInfo> list(int pageSize, int pageNum, Integer orderId) {
        PageHelper.startPage(pageNum,pageSize);
        PageHelper.orderBy("create_time desc");
        List<IncomeInfo> list = incomeInfoMapper.list(orderId);
        PageInfo pageInfo = new PageInfo(list);
        return ServerResponse.createBySuccess(pageInfo);
    }
}
