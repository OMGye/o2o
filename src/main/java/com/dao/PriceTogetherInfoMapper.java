package com.dao;

import com.pojo.PriceTogetherInfo;

public interface PriceTogetherInfoMapper {
    int deleteByPrimaryKey(Integer priceTogetherId);

    int insert(PriceTogetherInfo record);

    int insertSelective(PriceTogetherInfo record);

    PriceTogetherInfo selectByPrimaryKey(Integer priceTogetherId);

    int updateByPrimaryKeySelective(PriceTogetherInfo record);

    int updateByPrimaryKey(PriceTogetherInfo record);
}