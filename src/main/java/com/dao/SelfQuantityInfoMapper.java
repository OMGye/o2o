package com.dao;

import com.pojo.QuantityInfo;
import com.pojo.SelfQuantityInfo;

import java.math.BigDecimal;
import java.util.List;

public interface SelfQuantityInfoMapper {
    int deleteByPrimaryKey(Integer selfQuantityId);

    int insert(SelfQuantityInfo record);

    int insertSelective(SelfQuantityInfo record);

    SelfQuantityInfo selectByPrimaryKey(Integer selfQuantityId);

    int updateByPrimaryKeySelective(SelfQuantityInfo record);

    int updateByPrimaryKey(SelfQuantityInfo record);

    SelfQuantityInfo selectByQuantiyRank(Integer quantiyRank);

    List<SelfQuantityInfo> selectList();

    List<SelfQuantityInfo> selectByQuantiy(BigDecimal pence);
}