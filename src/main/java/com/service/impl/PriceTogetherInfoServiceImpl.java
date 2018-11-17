package com.service.impl;

import com.common.ServerResponse;
import com.dao.PriceTogetherInfoMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pojo.EngineerRankInfo;
import com.pojo.PriceDeductInfo;
import com.pojo.PriceTogetherInfo;
import com.service.PriceTogetherInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by upupgogogo on 2018/11/12.下午7:25
 */
@Service("priceTogetherInfoService")
public class PriceTogetherInfoServiceImpl implements PriceTogetherInfoService {

    @Autowired
    private PriceTogetherInfoMapper priceTogetherInfoMapper;


    @Override
    public ServerResponse add(PriceTogetherInfo priceTogetherInfo) {
        if (priceTogetherInfo == null || priceTogetherInfo.getPriceTogetherNum() == null || priceTogetherInfo.getPriceTogetherName() == null || priceTogetherInfo.getPriceTogetherPercentage() == null)
            return ServerResponse.createByErrorMessage("部分参数不能为空");
        PriceTogetherInfo curPriceTogetherInfo = priceTogetherInfoMapper.selectByPriceTogetherInfo(priceTogetherInfo);
        if (curPriceTogetherInfo == null){
            int row = priceTogetherInfoMapper.insert(priceTogetherInfo);
            if (row > 0)
                return ServerResponse.createBySuccess("新增成功");
            else
                return ServerResponse.createByErrorMessage("新增失败");
        }
        return ServerResponse.createByErrorMessage("改拼款参数已经存在");
    }

    @Override
    public ServerResponse delete(Integer priceTogetherId) {
        if(priceTogetherId == null)
            return ServerResponse.createByErrorMessage("参数不能为空");
        int row = priceTogetherInfoMapper.deleteByPrimaryKey(priceTogetherId);
        if (row > 0)
            return ServerResponse.createBySuccess("删除成功");
        return ServerResponse.createByErrorMessage("删除失败");
    }

    @Override
    public ServerResponse update(PriceTogetherInfo priceTogetherInfo) {
        if (priceTogetherInfo == null ||priceTogetherInfo.getPriceTogetherId() == null)
            return ServerResponse.createByErrorMessage("参数不能为空");
        PriceTogetherInfo newPriceTogetherInfo = new PriceTogetherInfo();
        newPriceTogetherInfo.setPriceTogetherId(priceTogetherInfo.getPriceTogetherId());
        newPriceTogetherInfo.setPriceTogetherPercentage(priceTogetherInfo.getPriceTogetherPercentage());
        int row = priceTogetherInfoMapper.updateByPrimaryKeySelective(newPriceTogetherInfo);
        if (row > 0)
            return ServerResponse.createBySuccess("修改成功");
        return ServerResponse.createByErrorMessage("修改失败");
    }

    @Override
    public ServerResponse<PageInfo> list(int pageSize, int pageNum) {
        PageHelper.startPage(pageNum,pageSize);
        PageHelper.orderBy("price_together_id desc");
        List<PriceTogetherInfo> list = priceTogetherInfoMapper.selectList();
        PageInfo pageInfo = new PageInfo(list);
        return ServerResponse.createBySuccess(pageInfo);
    }

    @Override
    public ServerResponse getById(Integer priceTogetherId) {
        if (priceTogetherId == null)
            return ServerResponse.createByErrorMessage("参数不能为空");
        PriceTogetherInfo priceTogetherInfo = priceTogetherInfoMapper.selectByPrimaryKey(priceTogetherId);
        return ServerResponse.createBySuccess(priceTogetherId);
    }
}
