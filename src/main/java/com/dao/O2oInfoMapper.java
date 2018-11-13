package com.dao;

import com.pojo.O2oInfo;

import java.util.List;

public interface O2oInfoMapper {
    int deleteByPrimaryKey(Integer o2oId);

    int insert(O2oInfo record);

    int insertSelective(O2oInfo record);

    O2oInfo selectByPrimaryKey(Integer o2oId);

    int updateByPrimaryKeySelective(O2oInfo record);

    int updateByPrimaryKey(O2oInfo record);

    O2oInfo selectO2oParamName(String o2oParamName);

    List<O2oInfo> selectList();
}