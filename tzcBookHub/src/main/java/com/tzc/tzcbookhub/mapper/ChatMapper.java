package com.tzc.tzcbookhub.mapper;

import com.tzc.tzcbookhub.model.Chat;
import com.tzc.tzcbookhub.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;

@Mapper
public interface ChatMapper {
    @Insert("INSERT INTO chat ( sender_id, receiver_id ) VALUES (#{senderId}, #{receiverId})")
    int saveChat(@Param("senderId") int senderId, @Param("receiverId") int receiverId);

    @Select("""
                SELECT
                	*
                FROM
                	`User`
                WHERE
                	user_id IN ( SELECT receiver_id FROM chat WHERE sender_id = #{userId} UNION SELECT sender_id FROM chat WHERE receiver_id = #{userId} );
            """)
    List<User> selectChatByUserId(@Param("userId") int userId);

    @Select("""
                SELECT
                	*
                FROM
                	chat
                WHERE
                	sender_id = #{senderId}
                	AND receiver_id = #{receiverId}
                	OR sender_id = #{receiverId}
                	AND receiver_id = #{senderId}
            """)
    Chat selectChat(@Param("senderId") int senderId, @Param("receiverId") int receiverId);
}
