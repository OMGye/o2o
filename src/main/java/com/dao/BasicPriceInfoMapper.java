package com.dao;

import com.pojo.BasicPriceInfo;

public interface BasicPriceInfoMapper {
    int deleteByPrimaryKey(Integer basicPriceId);

    int insert(BasicPriceInfo record);

    int insertSelective(BasicPriceInfo record);

    BasicPriceInfo selectByPrimaryKey(Integer basicPriceId);

    int updateByPrimaryKeySelective(BasicPriceInfo record);

    int updateByPrimaryKey(BasicPriceInfo record);
}