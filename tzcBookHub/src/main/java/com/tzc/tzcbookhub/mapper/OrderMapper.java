package com.tzc.tzcbookhub.mapper;

import com.tzc.tzcbookhub.model.Order;
import com.tzc.tzcbookhub.model.dto.BoughtSoldGoodResponse;
import com.tzc.tzcbookhub.model.dto.BuyGoodRequest;
import com.tzc.tzcbookhub.model.dto.OrderInfoResponse;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;
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

    @Update("UPDATE `Order` SET order_statu = #{status}, last_update_time = #{lastUpdateTime}  WHERE order_id = #{orderId}")
    void updateStatus(@Param("orderId") Integer orderId, @Param("lastUpdateTime") LocalDateTime lastUpdateTime, @Param("status") String status);

    @Select("SELECT * FROM `Order` WHERE order_id = #{orderId}")
    Order selectById(Integer orderId);


    @Select("""
            SELECT
            	o.order_id,
            	u.username as seller_name,
            	g.good_name,
            	o.order_statu,
            	o.create_time
            FROM
            	`Order` o,
            	`User` u,
            	Good g
            WHERE
            	o.consumer_id = #{consumerId}
            	AND o.seller_id = u.user_id
            	AND o.good_id = g.good_id
            """)
    List<OrderInfoResponse> getOrderInfoByConsumerId(@Param("consumerId") int consumerId);

    @Update("""
            UPDATE `User`
            JOIN (
                SELECT u.user_id, u.star
                FROM `User` u
                JOIN `Order` o ON u.user_id = o.seller_id
                WHERE o.order_id = #{orderId}
            ) AS temp ON `User`.user_id = temp.user_id
            SET `User`.star = (temp.star + #{star}) / 2;
            """)
    void updateSellerStar(@Param("orderId") int orderId, @Param("star") int star);


    // 买到的商品
    @Select("""
            SELECT
            	g.good_name,
            	g.good_info,
            	g.good_value,
            	o.create_time,
            	o.last_update_time,
            	g.good_img
            FROM
            	Good g,
            	`Order` o
            WHERE
            	g.good_id = o.good_id
            	AND o.consumer_id = #{consumerId}
            	AND o.order_statu = '已完成'
            """)
    List<BoughtSoldGoodResponse> selectBoughtGoods(@Param("consumerId") int consumerId);


    // 卖出的商品
    @Select("""
            SELECT
            	g.good_name,
            	g.good_info,
            	g.good_value,
            	g.create_time,
            	o.last_update_time,
            	g.good_img
            FROM
            	Good g,
            	`Order` o
            WHERE
            	g.good_id = o.good_id
            	AND o.seller_id = #{sellerId}
            	AND o.order_statu = '已完成'
            """)
    List<BoughtSoldGoodResponse> selectSoldGoods(@Param("sellerId") int sellerId);

}