package com.tzc.tzcbookhub.mapper;

import com.tzc.tzcbookhub.model.Good;
import com.tzc.tzcbookhub.model.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface GoodMapper {
    @Select("SELECT * " +
            "FROM Good " +
            "WHERE good_id NOT IN (SELECT good_id FROM `Order`)")
    List<Good> selectAll();

    @Select("SELECT g.* " +
            "FROM Good g " +
            "JOIN User u ON g.seller_id = u.user_id " +
            "WHERE g.good_id NOT IN (SELECT good_id FROM `Order`) " +
            "ORDER BY u.star DESC")
    List<Good> selectOrderBySellerStar();

    @Select("SELECT * " +
            "FROM Good " +
            "WHERE good_id NOT IN (SELECT good_id FROM `Order`) " +
            "ORDER BY last_update_time DESC")
    List<Good> selectOrderNewBook();

    @Select("SELECT * FROM Good WHERE good_id = #{id}")
    Good selectById(Integer id);

    @Select("SELECT * " +
            "FROM Good " +
            "WHERE seller_id = #{id} " +
            "AND good_id NOT IN (SELECT good_id FROM `Order`)")
    List<Good> selectByUserId(Integer id);

    @Insert("INSERT INTO Good " +
            "( good_id,seller_id, good_name, good_info, good_value, good_img, create_time, last_update_time )" +
            "VALUES" +
            "(#{goodID}, #{sellerID}, #{goodName}, #{goodInfo}, #{goodValue}, #{goodImg}, #{createTime}, #{lastUpdateTime})")
    int saveProduct(Good good);

    @Select("SELECT id FROM Good ORDER BY id DESC LIMIT 1;")
    int selectFinalId();

    @Select("SELECT good_value FROM Good WHERE good_id = #{goodId}")
    Double getGoodValue(int goodId);

    @Select("SELECT * FROM Good WHERE good_name LIKE CONCAT('%', #{keyword}, '%') AND good_id NOT IN (SELECT good_id FROM `Order`)")
    List<Good> searchByKeyword(String keyword);


}
