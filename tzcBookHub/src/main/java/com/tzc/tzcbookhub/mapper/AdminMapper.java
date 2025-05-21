package com.tzc.tzcbookhub.mapper;

import com.tzc.tzcbookhub.model.Admin;
import com.tzc.tzcbookhub.model.User;
import com.tzc.tzcbookhub.model.dto.GoodVO;
import com.tzc.tzcbookhub.model.dto.OrderVO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AdminMapper {
    @Select("SELECT * FROM admin WHERE username = #{username}")
    Admin selectByName(@Param("username") String username);

    @Insert("INSERT INTO admin (username, password) VALUES (#{username}, #{password})")
    int insert(@Param("username") String username, @Param("password") String password);

    @Select("SELECT Good.*, `User`.username AS seller_name FROM Good, `User` WHERE Good.seller_id = `User`.user_id")
    List<GoodVO> selectAllGoodVO();

    @Select("SELECT " +
            "o.order_id, " +
            "s.username AS seller_name, " +
            "c.username AS consumer_name, " +
            "g.good_name, " +
            "g.good_img, " +
            "o.order_statu, " +
            "o.create_time, " +
            "o.last_update_time " +
            "FROM `Order` o " +
            "JOIN `User` s ON o.seller_id = s.user_id " +
            "JOIN `User` c ON o.consumer_id = c.user_id " +
            "JOIN `Good` g ON o.good_id = g.good_id")
    List<OrderVO> selectAllOrderVO();

    @Select("SELECT * FROM User")
    List<User> selectAllUser();

    @Delete("DELETE User, Good\n" +
            "FROM User\n" +
            "LEFT JOIN Good ON User.user_id = Good.seller_id\n" +
            "WHERE User.user_id = #{user_id};")
    int deleteUserById(@Param("user_id") int userId);

    @Update("UPDATE `User` SET username = #{username}, password = #{password}, email = #{email}, money = #{money}, star = #{star} WHERE user_id = #{userID}")
    int updateUser(User user);

}
