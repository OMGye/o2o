package com.service.impl;

import com.common.Const;
import com.common.ServerResponse;
import com.dao.EngineerRankInfoMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pojo.BasicPriceInfo;
import com.pojo.EngineerRankInfo;
import com.service.EngineerRankInfoService;
import org.apache.poi.hssf.util.HSSFColor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by upupgogogo on 2018/11/12.下午3:29
 */
@Service("engineerRankInfoService")
public class EngineerRankInfoServiceImpl implements EngineerRankInfoService{

    @Autowired
    private EngineerRankInfoMapper rankInfoMapper;
    @Override
    public ServerResponse add(EngineerRankInfo engineerRankInfo) {
        if (engineerRankInfo == null || engineerRankInfo.getEngineerRank() == null)
            return ServerResponse.createByErrorMessage("您未设置等级");
        if (engineerRankInfo.getEngineerRankA() == Const.EngineerRankInfo.CANNOT && engineerRankInfo.getEngineerRankB() == Const.EngineerRankInfo.CANNOT && engineerRankInfo.getEngineerRankC() == Const.EngineerRankInfo.CANNOT && engineerRankInfo.getEngineerRankD() == Const.EngineerRankInfo.CANNOT)
            return ServerResponse.createByErrorMessage("您未设置等级A.B.C.D任意等级");
        engineerRankInfo.setEngineerRankCam(Const.EngineerRankInfo.CAN);
        EngineerRankInfo curEngineerRankInfo  = rankInfoMapper.selectByEngineerRank(engineerRankInfo.getEngineerRank());
        if (curEngineerRankInfo != null)
            return ServerResponse.createByErrorMessage("该工程师等级已存在");
        int row = rankInfoMapper.insert(engineerRankInfo);
        if (row > 0)
            return ServerResponse.createBySuccess("新增成功");

        return ServerResponse.createByErrorMessage("新增失败");
    }

    @Override
    public ServerResponse delete(Integer engineerRankId) {
        if (engineerRankId == null)
            return ServerResponse.createByErrorMessage("您未指定id");
        int row = rankInfoMapper.deleteByPrimaryKey(engineerRankId);
        if (row > 0)
            return ServerResponse.createBySuccess("删除成功");
        return ServerResponse.createByErrorMessage("删除失败");
    }

    @Override
    public ServerResponse update(EngineerRankInfo engineerRankInfo) {
        if (engineerRankInfo == null || engineerRankInfo.getEngineerRankId() == null)
            return ServerResponse.createByErrorMessage("您未指定id");
        engineerRankInfo.setEngineerRankCam(Const.EngineerRankInfo.CAN);
        engineerRankInfo.setEngineerRank(null);
        int row = rankInfoMapper.updateByPrimaryKeySelective(engineerRankInfo);
        if (row > 0)
            return ServerResponse.createBySuccess("修改成功");
        return ServerResponse.createByErrorMessage("修改失败");
    }

    @Override
    public ServerResponse<PageInfo> list(int pageSize, int pageNum) {
        PageHelper.startPage(pageNum,pageSize);
        PageHelper.orderBy("engineer_rank asc");
        List<EngineerRankInfo> list = rankInfoMapper.selectList();
        PageInfo pageInfo = new PageInfo(list);
        return ServerResponse.createBySuccess(pageInfo);
    }

    @Override
    public ServerResponse getById(Integer engineerRankId) {
        if (engineerRankId == null)
            return ServerResponse.createByErrorMessage("参数不能为空");
        EngineerRankInfo engineerRankInfo = rankInfoMapper.selectByPrimaryKey(engineerRankId);
        return ServerResponse.createBySuccess(engineerRankInfo);
    }
}
