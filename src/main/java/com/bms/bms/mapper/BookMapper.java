package com.bms.bms.mapper;

import com.bms.bms.model.Book;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface BookMapper {
    @Select("select * from book where id=#{id}")
    Book findById(@Param("id") Long id);

    @Insert("insert into book(id,number,name,type,gmt_create,gmt_modified,status,lend_count,cover,brief) " +
            "values(#{id},#{number},#{name},#{type},#{gmtCreate},#{gmtModified},#{status},#{lendCount},#{cover},#{brief})")
    void createBook(Book book);

    @Select("select * from book")
    List<Book> selectAll();

    @Select("select * from book  order by gmt_create limit #{offset},#{size}")//分页查询
    List<Book> listAll(@Param(value = "offset") Integer offset, @Param(value = "size")Integer size);

    @Select("select count(1) from book")
    Integer bookCountAll();
}