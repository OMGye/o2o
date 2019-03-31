package com.dao;

import com.pojo.OrderInfo;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface OrderInfoMapper {
    int deleteByPrimaryKey(Integer orderId);

    int insert(OrderInfo record);

    int insertSelective(OrderInfo record);

    OrderInfo selectByPrimaryKey(Integer orderId);

    int updateByPrimaryKeySelective(OrderInfo record);

    int updateByPrimaryKey(OrderInfo record);

    List<OrderInfo> customerListOrder(@Param("customerId")Integer customerId, @Param("orderState")Integer orderState);

    List<OrderInfo> engineerListOrder(@Param("engineerId")Integer engineerId, @Param("orderState")Integer orderState);

    List<OrderInfo> engineerQaeListOrder(@Param("engineerId")Integer engineerId, @Param("orderState")Integer orderState);

    List<OrderInfo> engineerCaughtList(@Param("orderFirstCategory")String orderFirstCategory,
                                       @Param("secondCategories")List<String> secondCategories,
                                       @Param("orderMi")Integer orderMi,
                                       @Param("orderState")Integer orderState);

    List<OrderInfo> engineerQaeCaughtList(@Param("orderFirstCategory") String orderFirstCategory,
                                          @Param("secondCategories") List<String> secondCategories,
                                          @Param("orderQae") Integer orderQae,
                                          @Param("orderState") Integer orderState);

   List<OrderInfo> adminOrderList(@Param("orderState")Integer orderState, @Param("orderFirstCategory")String orderFirstCategory, @Param("orderQae")Integer orderQae);


   List<OrderInfo> selectByTime(@Param("startDate")Date startDate, @Param("endDate")Date endDate);

    List<OrderInfo> selectByTimeAndType(@Param("startDate")Date startDate, @Param("endDate")Date endDate, @Param("customerId")Integer customerId, @Param("engineerId")Integer engineerId, @Param("engineerCheckId")Integer engineerCheckId, @Param("orderState")Integer orderState);

    List<OrderInfo> selectByIdLike(@Param("orderIdString")String orderIdString);

    List<OrderInfo> selectByOtherIdLike(@Param("customerIdLike")String customerIdLike, @Param("engineerIdLIke")String engineerIdLIke, @Param("engineerCheckIdLike")String engineerCheckIdLike);

    BigDecimal hasFinshedPrice();

    BigDecimal allUnfinishedPrice();

    List<OrderInfo> selectByDateUnDownload(@Param("startDate")Date startDate, @Param("endDate")Date endDate);

    int updateDownload(@Param("startDate")Date startDate, @Param("endDate")Date endDate);

    List<OrderInfo>  adminCheckList();
}