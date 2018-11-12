package com.dao;

import com.pojo.O2oPayInfo;

public interface O2oPayInfoMapper {
    int deleteByPrimaryKey(Integer payInfoId);

    int insert(O2oPayInfo record);

    int insertSelective(O2oPayInfo record);

    O2oPayInfo selectByPrimaryKey(Integer payInfoId);

    int updateByPrimaryKeySelective(O2oPayInfo record);

    int updateByPrimaryKey(O2oPayInfo record);
}