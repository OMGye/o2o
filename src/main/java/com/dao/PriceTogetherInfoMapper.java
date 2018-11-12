package com.dao;

import com.pojo.EngineerRankInfo;
import com.pojo.PriceTogetherInfo;

import java.util.List;

public interface PriceTogetherInfoMapper {
    int deleteByPrimaryKey(Integer priceTogetherId);

    int insert(PriceTogetherInfo record);

    int insertSelective(PriceTogetherInfo record);

    PriceTogetherInfo selectByPrimaryKey(Integer priceTogetherId);

    int updateByPrimaryKeySelective(PriceTogetherInfo record);

    int updateByPrimaryKey(PriceTogetherInfo record);

    PriceTogetherInfo selectByPriceTogetherInfo(PriceTogetherInfo priceTogetherInfo);

    List<PriceTogetherInfo> selectList();
}