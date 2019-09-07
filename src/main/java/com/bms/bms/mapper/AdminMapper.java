package com.bms.bms.mapper;

import com.bms.bms.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


@Mapper
public interface AdminMapper {
    @Select("select * from admin where id=#{id}")
    User findById(@Param("id") Long id);
}
