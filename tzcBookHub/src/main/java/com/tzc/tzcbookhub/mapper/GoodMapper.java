package com.tzc.tzcbookhub.mapper;

import com.tzc.tzcbookhub.model.Good;
import com.tzc.tzcbookhub.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface GoodMapper {
    @Select("SELECT * FROM Good")
    List<Good> selectAll();

    @Select("SELECT g.* " +
            "FROM Good g " +
            "JOIN User u ON g.seller_id = u.user_id " +
            "ORDER BY u.star DESC")
    List<Good> selectOrderBySellerStar();

    @Select("SELECT * " +
            "FROM Good " +
            "ORDER BY last_update_time DESC;")
    List<Good> selectOrderNewBook();

    @Select("SELECT * FROM Good WHERE good_id = #{id}")
    Good selectById(Integer id);

    @Select("SELECT * FROM Good WHERE seller_id = #{id}")
    List<Good> selectByUserId(Integer id);

    @Insert("INSERT INTO Good " +
            "( good_id,seller_id, good_name, good_info, good_value, good_img )" +
            "VALUES" +
            "(#{goodID}, #{sellerID}, #{goodName}, #{goodInfo}, #{goodValue}, #{goodImg})")
    int saveProduct(Good good);

    @Select("SELECT id FROM Good ORDER BY id DESC LIMIT 1;")
    int selectFinalId();


}
