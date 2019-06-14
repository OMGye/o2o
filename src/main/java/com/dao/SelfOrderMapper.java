package com.dao;

import com.pojo.OrderInfo;
import com.pojo.SelfOrder;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface SelfOrderMapper {
    int deleteByPrimaryKey(Integer orderId);

    int insert(SelfOrder record);

    int insertSelective(SelfOrder record);

    SelfOrder selectByPrimaryKey(Integer orderId);

    int updateByPrimaryKeySelective(SelfOrder record);

    int updateByPrimaryKey(SelfOrder record);

    List<SelfOrder> engineerCaughtList(@Param("engineerId") Integer engineerId, @Param("orderState") Integer orderState);

    List<SelfOrder> customerListOrder(@Param("customerId")Integer customerId, @Param("orderState")Integer orderState);

    List<SelfOrder> selectByTimeAndType(@Param("startDate")Date startDate, @Param("endDate")Date endDate, @Param("customerId")Integer customerId, @Param("engineerId")Integer engineerId,@Param("orderState")Integer orderState);

}