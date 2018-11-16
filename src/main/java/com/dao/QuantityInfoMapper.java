package com.dao;

import com.pojo.OtherParamInfo;
import com.pojo.QuantityInfo;

import java.util.List;

public interface QuantityInfoMapper {
    int deleteByPrimaryKey(Integer quantityId);

    int insert(QuantityInfo record);

    int insertSelective(QuantityInfo record);

    QuantityInfo selectByPrimaryKey(Integer quantityId);

    int updateByPrimaryKeySelective(QuantityInfo record);

    int updateByPrimaryKey(QuantityInfo record);

    QuantityInfo selectByQuantiyRank(Integer quantiyRank);

    List<QuantityInfo> selectList();
}