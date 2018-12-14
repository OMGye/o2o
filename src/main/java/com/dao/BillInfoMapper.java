package com.dao;

import com.pojo.BillInfo;
import com.pojo.EngineerInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BillInfoMapper {
    int deleteByPrimaryKey(Integer billId);

    int insert(BillInfo record);

    int insertSelective(BillInfo record);

    BillInfo selectByPrimaryKey(Integer billId);

    int updateByPrimaryKeySelective(BillInfo record);

    int updateByPrimaryKey(BillInfo record);

    List<BillInfo> list(@Param("userId") Integer userId, @Param("userType")Integer userType);

    List<BillInfo> selectByIdLike(@Param("billIdString")String billIdString);
}