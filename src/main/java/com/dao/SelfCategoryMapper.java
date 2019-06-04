package com.dao;

import com.pojo.SelfCategory;

import java.util.List;

public interface SelfCategoryMapper {
    int deleteByPrimaryKey(Integer selfCategoryId);

    int insert(SelfCategory record);

    int insertSelective(SelfCategory record);

    SelfCategory selectByPrimaryKey(Integer selfCategoryId);

    int updateByPrimaryKeySelective(SelfCategory record);

    int updateByPrimaryKey(SelfCategory record);

    List<SelfCategory> selectList();

    SelfCategory selectByName(String name);
}