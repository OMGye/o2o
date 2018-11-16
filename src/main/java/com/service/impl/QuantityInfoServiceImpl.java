package com.service.impl;

import com.common.ServerResponse;
import com.dao.QuantityInfoMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pojo.O2oInfo;
import com.pojo.QuantityInfo;
import com.service.QuantityInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by upupgogogo on 2018/11/14.下午2:44
 */
@Service("quantityInfoService")
public class QuantityInfoServiceImpl implements QuantityInfoService {

    @Autowired
    private QuantityInfoMapper quantityInfoMapper;

    @Override
    public ServerResponse add(QuantityInfo quantityInfo) {
        if (quantityInfo == null || quantityInfo.getQuantiyRank() == null || quantityInfo.getQuantityDraw() == null || quantityInfo.getQuantityPercentage() == null)
            return ServerResponse.createByErrorMessage("参数不能为空");
        QuantityInfo curQuantityInfo = quantityInfoMapper.selectByQuantiyRank(quantityInfo.getQuantiyRank());
        if (curQuantityInfo != null)
            return ServerResponse.createByErrorMessage("已存在该抽成等级");
        int row = quantityInfoMapper.insert(quantityInfo);
        if (row > 0)
            return ServerResponse.createBySuccess("新增成功");
        return ServerResponse.createByErrorMessage("新增失败");
    }

    @Override
    public ServerResponse delete(Integer quantityId) {
        if (quantityId == null)
            return ServerResponse.createByErrorMessage("参数不能为空");
        int row = quantityInfoMapper.deleteByPrimaryKey(quantityId);
        if (row > 0)
            return ServerResponse.createBySuccess("删除成功");
        return ServerResponse.createByErrorMessage("删除失败");
    }

    @Override
    public ServerResponse update(QuantityInfo quantityInfo) {
        if (quantityInfo == null || quantityInfo.getQuantityId() == null)
            return ServerResponse.createByErrorMessage("参数不能为空");
        QuantityInfo newQuantityInfo = new QuantityInfo();
        newQuantityInfo.setQuantityId(quantityInfo.getQuantityId());
        newQuantityInfo.setQuantityPercentage(quantityInfo.getQuantityPercentage());
        newQuantityInfo.setQuantityDraw(quantityInfo.getQuantityDraw());
        int row = quantityInfoMapper.updateByPrimaryKeySelective(newQuantityInfo);
        if (row > 0)
            return ServerResponse.createBySuccess("修改成功");
        return ServerResponse.createByErrorMessage("修改失败");
    }

    @Override
    public ServerResponse<PageInfo> list(int pageSize, int pageNum) {
        PageHelper.startPage(pageNum,pageSize);
        PageHelper.orderBy("quantiy_rank desc");
        List<QuantityInfo> list = quantityInfoMapper.selectList();
        PageInfo pageInfo = new PageInfo(list);
        return ServerResponse.createBySuccess(pageInfo);
    }
}
