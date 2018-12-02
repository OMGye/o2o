package com.dao;

import com.pojo.BillInfo;
import com.pojo.IncomeInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IncomeInfoMapper {
    int deleteByPrimaryKey(Integer incomeId);

    int insert(IncomeInfo record);

    int insertSelective(IncomeInfo record);

    IncomeInfo selectByPrimaryKey(Integer incomeId);

    int updateByPrimaryKeySelective(IncomeInfo record);

    int updateByPrimaryKey(IncomeInfo record);

    List<IncomeInfo> list(Integer orderId);
}