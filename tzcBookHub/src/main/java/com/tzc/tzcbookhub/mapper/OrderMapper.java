package com.tzc.tzcbookhub.mapper;

import com.tzc.tzcbookhub.model.Order;
import com.tzc.tzcbookhub.model.dto.BuyGoodRequest;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OrderMapper {
    // This method saves a new order to the database.
    @Insert("""
    INSERT INTO `Order` (
        order_id, seller_id, consumer_id, good_id, order_statu, create_time, last_update_time
    )
    SELECT 
        IFNULL(MAX(order_id), 0) + 1, 
        #{sellerID}, 
        #{userId}, 
        #{goodId}, 
        '未完成', 
        #{createTime}, 
        #{lastUpdateTime}
    FROM `Order`
""")
    int saveOrder(BuyGoodRequest buyGoodRequest);

    @Select("SELECT * FROM `Order`")
    List<Order> selectAllOrders();

    @Update("UPDATE `Order` SET order_statu = #{status} WHERE order_id = #{orderId}")
    void updateStatus(@Param("orderId") Integer orderId, @Param("status") String status);

    @Select("SELECT * FROM `Order` WHERE order_id = #{orderId}")
    Order selectById(Integer orderId);



}