package com.tzc.tzcbookhub.mapper;

import com.tzc.tzcbookhub.model.Message;
import com.tzc.tzcbookhub.model.dto.MessageRequest;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MessageMapper {
    @Insert("INSERT INTO message(sender_id, receiver_id, content, chat_id) VALUES(#{senderId}, #{receiverId}, #{content}, #{chatId})")
    void addMessage(@Param("senderId") int senderId, @Param("receiverId") int receiverId, @Param("content") String content, @Param("chatId") int chatId);

    @Select("SELECT * FROM message WHERE chat_id = #{chatId}")
    List<Message> selectByChatId(@Param("chatId") int chatId);
}
