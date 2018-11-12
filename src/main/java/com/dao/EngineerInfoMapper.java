package com.dao;

import com.pojo.EngineerInfo;

public interface EngineerInfoMapper {
    int deleteByPrimaryKey(Integer engineerId);

    int insert(EngineerInfo record);

    int insertSelective(EngineerInfo record);

    EngineerInfo selectByPrimaryKey(Integer engineerId);

    int updateByPrimaryKeySelective(EngineerInfo record);

    int updateByPrimaryKey(EngineerInfo record);
}