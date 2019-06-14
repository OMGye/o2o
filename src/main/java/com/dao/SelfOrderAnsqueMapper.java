package com.dao;

import com.pojo.OrderAnsqueInfo;
import com.pojo.SelfOrderAnsque;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SelfOrderAnsqueMapper {
    int deleteByPrimaryKey(Integer orderAnsqueId);

    int insert(SelfOrderAnsque record);

    int insertSelective(SelfOrderAnsque record);

    SelfOrderAnsque selectByPrimaryKey(Integer orderAnsqueId);

    int updateByPrimaryKeySelective(SelfOrderAnsque record);

    int updateByPrimaryKey(SelfOrderAnsque record);

    List<OrderAnsqueInfo> list(@Param("orderId")Integer orderId);

    Integer getUnReadNum(@Param("orderId")Integer orderId, @Param("type")Integer type);

    int updateMessAgeState(@Param("orderId")Integer orderId, @Param("type")Integer type);

    List<OrderAnsqueInfo> selectByOrderListDownload(@Param("orderIds")List<Integer> orderIds);
}