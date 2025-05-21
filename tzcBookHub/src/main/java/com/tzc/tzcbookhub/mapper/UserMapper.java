package com.tzc.tzcbookhub.mapper;

import com.tzc.tzcbookhub.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select("SELECT * FROM User")
    List<User> selectAll();

    @Select("SELECT * FROM User ORDER BY star DESC;")
    List<User> selectUserByStar();

    @Select("SELECT MAX(user_id) FROM User")
    Integer getMaxUserId();

    @Select("SELECT * FROM User WHERE username = #{username}")
    User findByUsername(String username);

    @Insert("INSERT INTO User (username, user_id, password, email, money, star, avatar, create_time, last_update_time) " +
            "VALUES (#{username}, #{userID}, #{password}, #{email}, #{money}, #{star}, #{avatar}, #{createTime}, #{lastUpdateTime})")
    int insertUser(User user);

}