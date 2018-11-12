package com.dao;

import com.pojo.CustomerInfo;

public interface CustomerInfoMapper {
    int deleteByPrimaryKey(Integer customerId);

    int insert(CustomerInfo record);

    int insertSelective(CustomerInfo record);

    CustomerInfo selectByPrimaryKey(Integer customerId);

    int updateByPrimaryKeySelective(CustomerInfo record);

    int updateByPrimaryKeyWithBLOBs(CustomerInfo record);

    int updateByPrimaryKey(CustomerInfo record);
}