package com.service.impl;

import com.common.ServerResponse;
import com.dao.PriceDeductInfoMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pojo.PriceDeductInfo;
import com.pojo.PriceTogetherInfo;
import com.service.PriceDeductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by upupgogogo on 2018/11/13.下午5:30
 */
@Service("priceDeductInfoService")
public class PriceDeductInfoServiceImpl implements PriceDeductInfoService {


     @Autowired
     private PriceDeductInfoMapper priceDeductInfoMapper;
    @Override
    public ServerResponse add(PriceDeductInfo priceDeductInfo) {
        if (priceDeductInfo == null || priceDeductInfo.getPriceDeductRank() == null || priceDeductInfo.getPriceDeductPercentage() == null)
            return ServerResponse.createByErrorMessage("部分参数不能为空");
        PriceDeductInfo curPriceDeductInfo = priceDeductInfoMapper.selectByPriceDeductRank(priceDeductInfo.getPriceDeductRank());
        if (curPriceDeductInfo != null)
            return ServerResponse.createByErrorMessage("当前扣款等级已经存在");
        int row = priceDeductInfoMapper.insert(priceDeductInfo);
        if (row > 0)
            return ServerResponse.createBySuccess("新增成功");
        return ServerResponse.createByErrorMessage("新增失败");
    }

    @Override
    public ServerResponse delete(Integer priceDeductId) {
        if(priceDeductId == null)
            return ServerResponse.createByErrorMessage("参数不能为空");
        int row = priceDeductInfoMapper.deleteByPrimaryKey(priceDeductId);
        if (row > 0)
            return ServerResponse.createBySuccess("删除成功");
        return ServerResponse.createByErrorMessage("删除失败");
    }

    @Override
    public ServerResponse update(PriceDeductInfo priceDeductInfo) {
        if (priceDeductInfo == null || priceDeductInfo.getPriceDeductId() == null)
            return ServerResponse.createByErrorMessage("参数不能为空");
        PriceDeductInfo newPriceDeductInfo = new PriceDeductInfo();
        newPriceDeductInfo.setPriceDeductId(priceDeductInfo.getPriceDeductId());
        newPriceDeductInfo.setPriceDeductPercentage(priceDeductInfo.getPriceDeductPercentage());
        int row = priceDeductInfoMapper.updateByPrimaryKeySelective(newPriceDeductInfo);
        if (row > 0)
            return ServerResponse.createBySuccess("修改成功");
        return ServerResponse.createByErrorMessage("修改失败");
    }

    @Override
    public ServerResponse<PageInfo> list(int pageSize, int pageNum) {
        PageHelper.startPage(pageNum,pageSize);
        PageHelper.orderBy("price_deduct_id desc");
        List<PriceDeductInfo> list = priceDeductInfoMapper.selectList();
        PageInfo pageInfo = new PageInfo(list);
        return ServerResponse.createBySuccess(pageInfo);
    }
}
