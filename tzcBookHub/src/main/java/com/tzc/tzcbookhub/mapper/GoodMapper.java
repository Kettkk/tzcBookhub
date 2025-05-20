package com.tzc.tzcbookhub.mapper;

import com.tzc.tzcbookhub.model.Good;
import com.tzc.tzcbookhub.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface GoodMapper {
    @Select("SELECT * FROM Good")
    List<Good> selectAll();

}
