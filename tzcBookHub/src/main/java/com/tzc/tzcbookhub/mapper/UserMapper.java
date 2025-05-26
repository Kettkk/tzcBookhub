package com.tzc.tzcbookhub.mapper;

import com.tzc.tzcbookhub.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select("SELECT * FROM User")
    List<User> selectAll();

    @Select("SELECT * FROM User ORDER BY star DESC;")
    List<User> selectUserByStar();

    @Select("SELECT * FROM User WHERE user_id = #{id}")
    User selectById(Integer id);

    @Select("SELECT MAX(user_id) FROM User")
    Integer getMaxUserId();

    @Select("SELECT * FROM User WHERE username = #{username}")
    User findByUsername(String username);

    @Insert("INSERT INTO User (username, user_id, password, email, money, star, avatar, create_time, last_update_time) " +
            "VALUES (#{username}, #{userID}, #{password}, #{email}, #{money}, #{star}, #{avatar}, #{createTime}, #{lastUpdateTime})")
    int insertUser(User user);

    @Update("UPDATE User SET username = #{username} WHERE user_id = #{userID}")
    void updateUsername(@Param("username") String username, @Param("userID") int userID);

    @Update("UPDATE User SET email = #{email} WHERE user_id = #{userID}")
    void updateEmail(@Param("email") String email, @Param("userID") int userID);

    @Update("UPDATE User SET avatar = #{avatar} WHERE user_id = #{userID}")
    void updateAvatar(@Param("avatar") String avatar, @Param("userID") int userID);

}