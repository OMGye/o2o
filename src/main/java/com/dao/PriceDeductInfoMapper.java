package com.dao;

import com.pojo.PriceDeductInfo;

public interface PriceDeductInfoMapper {
    int deleteByPrimaryKey(Integer priceDeductId);

    int insert(PriceDeductInfo record);

    int insertSelective(PriceDeductInfo record);

    PriceDeductInfo selectByPrimaryKey(Integer priceDeductId);

    int updateByPrimaryKeySelective(PriceDeductInfo record);

    int updateByPrimaryKey(PriceDeductInfo record);
}