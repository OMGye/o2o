package com.dao;

import com.pojo.IncomeInfo;

public interface IncomeInfoMapper {
    int deleteByPrimaryKey(Integer incomeId);

    int insert(IncomeInfo record);

    int insertSelective(IncomeInfo record);

    IncomeInfo selectByPrimaryKey(Integer incomeId);

    int updateByPrimaryKeySelective(IncomeInfo record);

    int updateByPrimaryKey(IncomeInfo record);
}