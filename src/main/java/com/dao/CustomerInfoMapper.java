package com.dao;

import com.pojo.CustomerInfo;
import com.pojo.EngineerInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CustomerInfoMapper {
    int deleteByPrimaryKey(Integer customerId);

    int insert(CustomerInfo record);

    int insertSelective(CustomerInfo record);

    CustomerInfo selectByPrimaryKey(Integer customerId);

    int updateByPrimaryKeySelective(CustomerInfo record);

    int updateByPrimaryKeyWithBLOBs(CustomerInfo record);

    int updateByPrimaryKey(CustomerInfo record);

    CustomerInfo selectByUserName(@Param("customerName") String customerName, @Param("customerId") Integer customerId, @Param("email") String email);

    CustomerInfo login(@Param("customerName") String customerName, @Param("password") String password);

    List<CustomerInfo> list(@Param("state") Integer state);
}