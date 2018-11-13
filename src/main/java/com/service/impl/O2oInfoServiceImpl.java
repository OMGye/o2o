package com.service.impl;

import com.common.ServerResponse;
import com.dao.O2oInfoMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pojo.O2oInfo;
import com.pojo.OtherParamInfo;
import com.service.O2oInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by upupgogogo on 2018/11/13.下午6:09
 */
@Service("o2oInfoService")
public class O2oInfoServiceImpl implements O2oInfoService {

    @Autowired
    private O2oInfoMapper o2oInfoMapper;
    @Override
    public ServerResponse add(O2oInfo o2oInfo) {
        if (o2oInfo == null || o2oInfo.getO2oParamName() == null || o2oInfo.getO2oParamValue() == null)
            return ServerResponse.createByErrorMessage("参数不能为空");
        O2oInfo curO2oInfo = o2oInfoMapper.selectO2oParamName(o2oInfo.getO2oParamName());
        if (curO2oInfo != null)
            return ServerResponse.createByErrorMessage("已存在该参数");
        int row = o2oInfoMapper.insert(o2oInfo);
        if (row > 0)
            return ServerResponse.createBySuccess("新增成功");
        return ServerResponse.createByErrorMessage("新增失败");
    }

    @Override
    public ServerResponse delete(Integer o2oId) {
        if (o2oId == null)
            return ServerResponse.createByErrorMessage("参数不能为空");
        int row = o2oInfoMapper.deleteByPrimaryKey(o2oId);
        if (row > 0)
            return ServerResponse.createBySuccess("删除成功");
        return ServerResponse.createByErrorMessage("删除失败");
    }

    @Override
    public ServerResponse update(O2oInfo o2oInfo) {
        if (o2oInfo == null || o2oInfo.getO2oId() == null)
            return ServerResponse.createByErrorMessage("参数不能为空");
        O2oInfo newO2oInfo = new O2oInfo();
        newO2oInfo.setO2oId(o2oInfo.getO2oId());
        newO2oInfo.setO2oParamValue(o2oInfo.getO2oParamValue());
        int row = o2oInfoMapper.updateByPrimaryKeySelective(newO2oInfo);
        if (row > 0)
            return ServerResponse.createBySuccess("修改成功");
        return ServerResponse.createByErrorMessage("修改失败");
    }

    @Override
    public ServerResponse<PageInfo> list(int pageSize, int pageNum) {
        PageHelper.startPage(pageNum,pageSize);
        PageHelper.orderBy("o2o_id desc");
        List<O2oInfo> list = o2oInfoMapper.selectList();
        PageInfo pageInfo = new PageInfo(list);
        return ServerResponse.createBySuccess(pageInfo);
    }
}
