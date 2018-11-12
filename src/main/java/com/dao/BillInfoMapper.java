package com.dao;

import com.pojo.BillInfo;

public interface BillInfoMapper {
    int deleteByPrimaryKey(Integer billId);

    int insert(BillInfo record);

    int insertSelective(BillInfo record);

    BillInfo selectByPrimaryKey(Integer billId);

    int updateByPrimaryKeySelective(BillInfo record);

    int updateByPrimaryKey(BillInfo record);
}