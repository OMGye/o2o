package com.dao;

import com.pojo.OtherParamInfo;
import com.pojo.PriceDeductInfo;

import java.util.List;

public interface OtherParamInfoMapper {
    int deleteByPrimaryKey(Integer paramId);

    int insert(OtherParamInfo record);

    int insertSelective(OtherParamInfo record);

    OtherParamInfo selectByPrimaryKey(Integer paramId);

    int updateByPrimaryKeySelective(OtherParamInfo record);

    int updateByPrimaryKey(OtherParamInfo record);

    OtherParamInfo selectByParamName(String paraName);

    List<OtherParamInfo> selectList();
}