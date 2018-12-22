package com.dao;

import com.pojo.CustomerInfo;
import com.pojo.EngineerInfo;
import com.pojo.OrderInfo;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface EngineerInfoMapper {
    int deleteByPrimaryKey(Integer engineerId);

    int insert(EngineerInfo record);

    int insertSelective(EngineerInfo record);

    EngineerInfo selectByPrimaryKey(Integer engineerId);

    int updateByPrimaryKeySelective(EngineerInfo record);

    int updateByPrimaryKey(EngineerInfo record);

    List<EngineerInfo> list(@Param("state") Integer state);

    EngineerInfo login(@Param("userName") String userName, @Param("password") String password);

    EngineerInfo selectByUserName(@Param("userName") String userName, @Param("engineerId") Integer engineerId, @Param("email") String email, @Param("phone") String phone, @Param("personCode") String personCode);

    EngineerInfo loginByPhone(@Param("phone") String phone, @Param("password") String password);

    List<EngineerInfo> selectByIdLike(@Param("engineerIdString")String engineerIdString);

    BigDecimal allPrice();
}