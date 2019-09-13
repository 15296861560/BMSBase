package com.bms.bms.mapper;

import com.bms.bms.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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
}
