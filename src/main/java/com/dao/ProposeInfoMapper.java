package com.dao;

import com.pojo.DrawCashInfo;
import com.pojo.ProposeInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProposeInfoMapper {
    int deleteByPrimaryKey(Integer proposeId);

    int insert(ProposeInfo record);

    int insertSelective(ProposeInfo record);

    ProposeInfo selectByPrimaryKey(Integer proposeId);

    int updateByPrimaryKeySelective(ProposeInfo record);

    int updateByPrimaryKey(ProposeInfo record);

    List<ProposeInfo> list();
}