package com.bms.bms.mapper;

import com.bms.bms.model.User;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;
import java.util.List;


@Mapper
public interface UserMapper {
    @Select("select * from user where id=#{id}")
    User findById(@Param("id") Long id);

    @Insert("insert into user(id,name,academy,cla,gmt_create,gmt_modified,token,status,borrow_count,password,history,phone) " +
            "values(#{id},#{name},#{academy},#{cla},#{gmtCreate},#{gmtModified},#{token},#{status},#{borrowCount},#{password},#{history},#{phone})")
    void createUser(User user);

    @Select("select * from user where token=#{token}")
    User findByToken(@Param("token") String token);

    @Select("select count(1) from user")
    Integer userCountAll();

    @Select("select * from user limit #{offset},#{size}")
    List<User> listAll(@Param(value = "offset") Integer offset, @Param(value = "size")Integer size);

    @Update("update user set name=#{name},academy=#{academy},cla=#{cla},gmt_modified=#{gmtModified},status=#{status}," +
            "borrow_count=#{borrowCount},password=#{password},history=#{history},phone=#{phone} where id=#{id}")
    void update(User user);

    @Delete("delete from user where id=#{userId}")
    void deleteById(@Param(value = "userId")Long userId);

    @Select("select count(1) from user where name regexp #{search}")
    Integer userCountBySearchName(@Param(value = "search")String search);

    @Select("select * from user where name regexp #{search} limit #{offset},#{size}")
    List<User> listBySearch(@Param(value = "offset")Integer offset,  @Param(value = "size")Integer size,  @Param(value = "search")String search);

    @Select("select * from user order by borrow_count desc limit 10")
    ArrayList<User> readerList();
}
