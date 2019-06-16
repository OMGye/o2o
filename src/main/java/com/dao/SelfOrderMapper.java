package com.dao;

import com.pojo.IncomeInfo;
import com.pojo.OrderInfo;
import com.pojo.SelfOrder;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface SelfOrderMapper {
    int deleteByPrimaryKey(Integer orderId);

    int insert(SelfOrder record);

    int insertSelective(SelfOrder record);

    SelfOrder selectByPrimaryKey(Integer orderId);

    int updateByPrimaryKeySelective(SelfOrder record);

    int updateByPrimaryKey(SelfOrder record);

    List<SelfOrder> engineerCaughtList(@Param("engineerId") Integer engineerId, @Param("orderState") Integer orderState);

    List<SelfOrder> customerListOrder(@Param("customerId")Integer customerId, @Param("orderState")Integer orderState);

    List<SelfOrder> engineerListOrder(@Param("engineerId")Integer engineerId, @Param("orderState")Integer orderState);

    List<SelfOrder> selectByTimeAndType(@Param("startDate")Date startDate, @Param("endDate")Date endDate, @Param("customerId")Integer customerId, @Param("engineerId")Integer engineerId,@Param("orderState")Integer orderState);

    List<SelfOrder> engineerCanCaughtList(@Param("orderFirstCategory") String orderFirstCategory,
                                          @Param("selfCategories") List<String> selfCategories,
                                          @Param("orderState") Integer orderState);


    List<SelfOrder> selectByTime(@Param("startDate")Date startDate, @Param("endDate")Date endDate);

    List<SelfOrder> adminOrderList(@Param("orderState")Integer orderState, @Param("orderFirstCategory")String orderFirstCategory, @Param("orderSelfCategory")String orderSelfCategory);

}