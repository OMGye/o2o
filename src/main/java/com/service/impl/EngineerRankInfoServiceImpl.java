package com.service.impl;

import com.common.Const;
import com.common.ServerResponse;
import com.dao.EngineerRankInfoMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pojo.EngineerRankInfo;
import com.service.EngineerRankInfoService;
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
        if (engineerRankInfo == null && engineerRankInfo.getEngineerRank() == null)
            return ServerResponse.createByErrorMessage("您未设置等级");
        if (engineerRankInfo.getEngineerRankA() == null && engineerRankInfo.getEngineerRankB() == null && engineerRankInfo.getEngineerRankC() == null && engineerRankInfo.getEngineerRankD() == null)
            return ServerResponse.createByErrorMessage("您未设置等级A.B.C.D任意等级");
        engineerRankInfo.setEngineerRankCam(Const.EngineerRankInfo.CAN);
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
        if (engineerRankInfo == null && engineerRankInfo.getEngineerRankId() == null)
            return ServerResponse.createByErrorMessage("您未指定id");
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
}
