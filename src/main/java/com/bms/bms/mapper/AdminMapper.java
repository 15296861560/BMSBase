package com.bms.bms.mapper;

import com.bms.bms.model.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


@Mapper
public interface AdminMapper {
    @Select("select * from admin where id=#{id}")
    Admin findById(@Param("id") Long id);
}
