package com.dao;

import com.pojo.AdminInfo;
import org.apache.ibatis.annotations.Param;

public interface AdminInfoMapper {
    int deleteByPrimaryKey(Integer adminId);

    int insert(AdminInfo record);

    int insertSelective(AdminInfo record);

    AdminInfo selectByPrimaryKey(Integer adminId);

    int updateByPrimaryKeySelective(AdminInfo record);

    int updateByPrimaryKey(AdminInfo record);

    AdminInfo selectByUserNameAndPassword(@Param("adminName")String adminName, @Param("password")String password);
}