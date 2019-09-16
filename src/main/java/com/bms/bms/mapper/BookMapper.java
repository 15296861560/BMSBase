package com.bms.bms.mapper;

import com.bms.bms.dto.BookQueryDTO;
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

    @Select("select count(*) from book where name regexp #{search}")//查询符合搜索条件的书的总数
    Integer searchCountByName(@Param(value = "search")String search);

    @Select("select * from book where name regexp #{search} order by gmt_create limit #{offset},#{size}")//带条件的分页查询
    List<Book> listSearch(BookQueryDTO bookQueryDTO);

    @Select("select count(*) from book where type=#{search}")//查询某种类型的书的总数
    Integer searchCountByType(String search);

    @Select("select * from book where type=#{search} order by gmt_create limit #{offset},#{size}")
    List<Book> listSearchByType(BookQueryDTO bookQueryDTO);
}
