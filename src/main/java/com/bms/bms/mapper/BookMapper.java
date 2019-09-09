package com.bms.bms.mapper;

import com.bms.bms.model.Book;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


@Mapper
public interface BookMapper {
    @Select("select * from book where id=#{id}")
    Book findById(@Param("id") Long id);
}
