package com.bms.bms.mapper;

import com.bms.bms.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


@Mapper
public interface UserMapper {
    @Select("select * from user where id=#{id}")
    User findById(@Param("id") Long id);

    @Insert("insert into user(id,name,academy,cla,gmtCreate,gmtModified,token,status,borrowCount,password,history,phone) " +
            "values(#{id},#{name},#{academy},#{cla},#{gmtCreate},#{gmtModified},#{token},#{status},#{borrowCount},#{password},#{history},#{phone})")
    void createUser(User user);
}
