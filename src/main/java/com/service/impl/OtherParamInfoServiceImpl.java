package com.service.impl;

import com.common.ServerResponse;
import com.dao.OtherParamInfoMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pojo.O2oInfo;
import com.pojo.OtherParamInfo;
import com.pojo.PriceDeductInfo;
import com.service.OtherParamInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by upupgogogo on 2018/11/13.下午5:53
 */
@Service("otherParamInfoService")
public class OtherParamInfoServiceImpl implements OtherParamInfoService {

    @Autowired
    private OtherParamInfoMapper otherParamInfoMapper;

    @Override
    public ServerResponse add(OtherParamInfo otherParamInfo) {
        if (otherParamInfo == null || otherParamInfo.getParamName() == null || otherParamInfo.getParamPercentage() == null)
            return ServerResponse.createByErrorMessage("部分参数不能为空");
        OtherParamInfo curOtherParamInfo = otherParamInfoMapper.selectByParamName(otherParamInfo.getParamName());
        if (curOtherParamInfo != null)
            return ServerResponse.createByErrorMessage("已存在该参数名");
        int row = otherParamInfoMapper.insert(otherParamInfo);
        if (row > 0)
            return ServerResponse.createBySuccess("新增成功");

        return ServerResponse.createByErrorMessage("新增失败");
    }

    @Override
    public ServerResponse delete(Integer paramId) {
        if (paramId == null)
            ServerResponse.createByErrorMessage("参数不能为空");
        int row = otherParamInfoMapper.deleteByPrimaryKey(paramId);
        if (row > 0)
            return ServerResponse.createBySuccess("删除成功");
        return ServerResponse.createByErrorMessage("删除失败");
    }

    @Override
    public ServerResponse update(OtherParamInfo otherParamInfo) {
        if (otherParamInfo == null || otherParamInfo.getParamId() == null)
            ServerResponse.createByErrorMessage("参数不能为空");
        OtherParamInfo newOtherParamInfo = new OtherParamInfo();
        newOtherParamInfo.setParamId(otherParamInfo.getParamId());
        newOtherParamInfo.setParamPercentage(otherParamInfo.getParamPercentage());
        newOtherParamInfo.setParamDec(otherParamInfo.getParamDec());
        int row = otherParamInfoMapper.updateByPrimaryKeySelective(newOtherParamInfo);
        if (row > 0)
            return ServerResponse.createBySuccess("修改成功");
        return ServerResponse.createByErrorMessage("修改失败");

    }

    @Override
    public ServerResponse<PageInfo> list(int pageSize, int pageNum) {
        PageHelper.startPage(pageNum,pageSize);
        PageHelper.orderBy("param_id desc");
        List<OtherParamInfo> list = otherParamInfoMapper.selectList();
        PageInfo pageInfo = new PageInfo(list);
        return ServerResponse.createBySuccess(pageInfo);
    }

    @Override
    public ServerResponse getById(Integer paramId) {
        if (paramId == null)
            return ServerResponse.createByErrorMessage("参数不能为空");
        OtherParamInfo otherParamInfo = otherParamInfoMapper.selectByPrimaryKey(paramId);
        return ServerResponse.createBySuccess(otherParamInfo);
    }
}
