package com.dao;

import com.pojo.QuantityInfo;

public interface QuantityInfoMapper {
    int deleteByPrimaryKey(Integer quantityId);

    int insert(QuantityInfo record);

    int insertSelective(QuantityInfo record);

    QuantityInfo selectByPrimaryKey(Integer quantityId);

    int updateByPrimaryKeySelective(QuantityInfo record);

    int updateByPrimaryKey(QuantityInfo record);
}