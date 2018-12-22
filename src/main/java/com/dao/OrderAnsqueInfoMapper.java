package com.dao;

import com.pojo.OrderAnsqueInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderAnsqueInfoMapper {
    int deleteByPrimaryKey(Integer orderAnsqueId);

    int insert(OrderAnsqueInfo record);

    int insertSelective(OrderAnsqueInfo record);

    OrderAnsqueInfo selectByPrimaryKey(Integer orderAnsqueId);

    int updateByPrimaryKeySelective(OrderAnsqueInfo record);

    int updateByPrimaryKey(OrderAnsqueInfo record);

    List<OrderAnsqueInfo> list(@Param("orderId")Integer orderId);

    Integer getUnReadNum(@Param("orderId")Integer orderId, @Param("type")Integer type);
}