package com.dao;

import com.pojo.BillInfo;
import com.pojo.PayInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PayInfoMapper {
    int deleteByPrimaryKey(Integer payInfoId);

    int insert(PayInfo record);

    int insertSelective(PayInfo record);

    PayInfo selectByPrimaryKey(Integer payInfoId);

    int updateByPrimaryKeySelective(PayInfo record);

    int updateByPrimaryKey(PayInfo record);

    List<PayInfo> list(@Param("userId") Integer userId, @Param("userName")String userName);
}