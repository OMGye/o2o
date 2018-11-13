package com.dao;

import com.pojo.PriceDeductInfo;
import com.pojo.PriceTogetherInfo;

import java.util.List;

public interface PriceDeductInfoMapper {
    int deleteByPrimaryKey(Integer priceDeductId);

    int insert(PriceDeductInfo record);

    int insertSelective(PriceDeductInfo record);

    PriceDeductInfo selectByPrimaryKey(Integer priceDeductId);

    int updateByPrimaryKeySelective(PriceDeductInfo record);

    int updateByPrimaryKey(PriceDeductInfo record);

    PriceDeductInfo selectByPriceDeductRank(int priceDeductRank);

    List<PriceDeductInfo> selectList();
}