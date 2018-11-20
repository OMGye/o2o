package com.service.impl;

import com.common.ServerResponse;
import com.dao.BasicPriceInfoMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pojo.BasicPriceInfo;
import com.pojo.EngineerRankInfo;
import com.service.BasicPriceInfoService;
import com.util.BigDecimalUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by upupgogogo on 2018/11/12.下午5:06
 */
@Service("basicPriceInfoService")
public class BasicPriceInfoServiceImpl implements BasicPriceInfoService{


    @Autowired
    private BasicPriceInfoMapper basicPriceInfoMapper;

    @Override
    public ServerResponse add(BasicPriceInfo basicPriceInfo) {
        if (basicPriceInfo == null || basicPriceInfo.getFirstCategory() == null || basicPriceInfo.getSecondRank() == null || basicPriceInfo.getThirdCategory() == null || basicPriceInfo.getBasicLayer() == null || basicPriceInfo.getPrice() == null)
            return ServerResponse.createByErrorMessage("部分参数不能为空");
        BasicPriceInfo curBasicPriceInfo = basicPriceInfoMapper.selectByBasicPriceInfo(basicPriceInfo);
        if (curBasicPriceInfo == null){
            int row = basicPriceInfoMapper.insert(basicPriceInfo);
            if (row > 0)
                return ServerResponse.createBySuccess("新增成功");
            else
                return ServerResponse.createByErrorMessage("新增失败");
        }
        return ServerResponse.createByErrorMessage("已存在该基础价格");
    }

    @Override
    public ServerResponse delete(Integer basicPriceId) {
        if (basicPriceId == null)
            return ServerResponse.createByErrorMessage("参数不能为空");
        int row = basicPriceInfoMapper.deleteByPrimaryKey(basicPriceId);
        if (row > 0)
            return ServerResponse.createBySuccess("删除成功");
        return ServerResponse.createByErrorMessage("删除失败");
    }

    @Override
    public ServerResponse update(BasicPriceInfo basicPriceInfo) {
        if (basicPriceInfo == null || basicPriceInfo.getBasicPriceId() == null)
            return ServerResponse.createByErrorMessage("参数不能为空");
        BasicPriceInfo newBasicPriceInfo = new BasicPriceInfo();
        newBasicPriceInfo.setBasicPriceId(basicPriceInfo.getBasicPriceId());
        newBasicPriceInfo.setPrice(basicPriceInfo.getPrice());
        int row = basicPriceInfoMapper.updateByPrimaryKeySelective(newBasicPriceInfo);
        if (row > 0)
            return ServerResponse.createBySuccess("修改成功");
        return ServerResponse.createByErrorMessage("修改失败");
    }

    @Override
    public ServerResponse<PageInfo> list(int pageSize, int pageNum) {
        PageHelper.startPage(pageNum,pageSize);
        PageHelper.orderBy("basic_price_id desc");
        List<BasicPriceInfo> list = basicPriceInfoMapper.selectList();
        PageInfo pageInfo = new PageInfo(list);
        return ServerResponse.createBySuccess(pageInfo);
    }

    @Override
    public ServerResponse getById(Integer basicPriceId) {
        if (basicPriceId == null)
            return ServerResponse.createByErrorMessage("参数不能为空");
        BasicPriceInfo basicPriceInfo = basicPriceInfoMapper.selectByPrimaryKey(basicPriceId);
        return ServerResponse.createBySuccess(basicPriceInfo);
    }

    @Override
    public ServerResponse getPrice(BasicPriceInfo basicPriceInfo) {
        if (basicPriceInfo == null || basicPriceInfo.getFirstCategory() == null || basicPriceInfo.getSecondRank() == null || basicPriceInfo.getBasicLayer() == null || basicPriceInfo.getThirdCategory() == null)
            return ServerResponse.createByErrorMessage("部分参数不能为空");

        BasicPriceInfo curBasicPriceInfo = basicPriceInfoMapper.selectByBasicPriceInfo(basicPriceInfo);
        if (curBasicPriceInfo != null) {
            return ServerResponse.createBySuccess(curBasicPriceInfo.getPrice());

        }
        return ServerResponse.createByErrorMessage("找不到该基础价格");
    }
}
