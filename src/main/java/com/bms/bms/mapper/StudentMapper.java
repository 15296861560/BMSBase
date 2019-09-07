package com.bms.bms.mapper;

import com.bms.bms.model.Student;
import org.apache.ibatis.annotations.*;


@Mapper
public interface StudentMapper {
    @Select("select * from student where id=#{id}")
    Student findById(@Param("id") Long id);
}
