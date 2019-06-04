package com.dao;

import com.pojo.SelfRank;

public interface SelfRankMapper {
    int deleteByPrimaryKey(Integer selfRankId);

    int insert(SelfRank record);

    int insertSelective(SelfRank record);

    SelfRank selectByPrimaryKey(Integer selfRankId);

    int updateByPrimaryKeySelective(SelfRank record);

    int updateByPrimaryKey(SelfRank record);
}