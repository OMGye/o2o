package com.dao;

import com.pojo.BillInfo;
import com.pojo.CustomerInfo;
import com.pojo.EngineerInfo;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface CustomerInfoMapper {
    int deleteByPrimaryKey(Integer customerId);

    int insert(CustomerInfo record);

    int insertSelective(CustomerInfo record);

    CustomerInfo selectByPrimaryKey(Integer customerId);

    int updateByPrimaryKeySelective(CustomerInfo record);

    int updateByPrimaryKeyWithBLOBs(CustomerInfo record);

    int updateByPrimaryKey(CustomerInfo record);

    CustomerInfo selectByUserName(@Param("customerName") String customerName, @Param("customerId") Integer customerId, @Param("email") String email,@Param("phone") String phone, @Param("personCode") String personCode);

    CustomerInfo login(@Param("customerName") String customerName, @Param("password") String password);

    CustomerInfo loginByPhone(@Param("phone") String phone, @Param("password") String password);

    List<CustomerInfo> list(@Param("state") Integer state);


    List<CustomerInfo> selectByIdLike(@Param("customerIdString")String customerIdString);

    BigDecimal allPrice();

}