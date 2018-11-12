package com.dao;

import com.pojo.OrderAnsqueInfo;

public interface OrderAnsqueInfoMapper {
    int deleteByPrimaryKey(Integer orderAnsqueId);

    int insert(OrderAnsqueInfo record);

    int insertSelective(OrderAnsqueInfo record);

    OrderAnsqueInfo selectByPrimaryKey(Integer orderAnsqueId);

    int updateByPrimaryKeySelective(OrderAnsqueInfo record);

    int updateByPrimaryKey(OrderAnsqueInfo record);
}