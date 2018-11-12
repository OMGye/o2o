package com.dao;

import com.pojo.EngineerRankInfo;

import java.util.List;

public interface EngineerRankInfoMapper {
    int deleteByPrimaryKey(Integer engineerRankId);

    int insert(EngineerRankInfo record);

    int insertSelective(EngineerRankInfo record);

    EngineerRankInfo selectByPrimaryKey(Integer engineerRankId);

    int updateByPrimaryKeySelective(EngineerRankInfo record);

    int updateByPrimaryKey(EngineerRankInfo record);

    List<EngineerRankInfo> selectList();
}