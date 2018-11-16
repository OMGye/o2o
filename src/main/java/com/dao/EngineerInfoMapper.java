package com.dao;

import com.pojo.EngineerInfo;
import org.apache.ibatis.annotations.Param;

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

    EngineerInfo selectByUserName(@Param("userName") String userName, @Param("engineerId") Integer engineerId, @Param("email") String email);
}