package com.dao;

import com.pojo.ProposeInfo;

public interface ProposeInfoMapper {
    int deleteByPrimaryKey(Integer proposeId);

    int insert(ProposeInfo record);

    int insertSelective(ProposeInfo record);

    ProposeInfo selectByPrimaryKey(Integer proposeId);

    int updateByPrimaryKeySelective(ProposeInfo record);

    int updateByPrimaryKey(ProposeInfo record);
}