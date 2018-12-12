package com.dao;

import com.pojo.DrawCashInfo;
import com.pojo.OrderInfo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface DrawCashInfoMapper {
    int deleteByPrimaryKey(Integer drawCashId);

    int insert(DrawCashInfo record);

    int insertSelective(DrawCashInfo record);

    DrawCashInfo selectByPrimaryKey(Integer drawCashId);

    int updateByPrimaryKeySelective(DrawCashInfo record);

    int updateByPrimaryKey(DrawCashInfo record);

    List<DrawCashInfo> list(@Param("userId") Integer userId, @Param("type")Integer type, @Param("state")Integer state);

    List<DrawCashInfo> selectByTime(@Param("startDate")Date startDate, @Param("endDate")Date endDate);
}