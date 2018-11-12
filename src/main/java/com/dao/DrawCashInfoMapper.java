package com.dao;

import com.pojo.DrawCashInfo;

public interface DrawCashInfoMapper {
    int deleteByPrimaryKey(Integer drawCashId);

    int insert(DrawCashInfo record);

    int insertSelective(DrawCashInfo record);

    DrawCashInfo selectByPrimaryKey(Integer drawCashId);

    int updateByPrimaryKeySelective(DrawCashInfo record);

    int updateByPrimaryKey(DrawCashInfo record);
}