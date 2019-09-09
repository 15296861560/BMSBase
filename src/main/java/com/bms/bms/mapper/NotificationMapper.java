package com.bms.bms.mapper;

import com.bms.bms.model.Notification;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface NotificationMapper {
    @Select("select * from notification where id=#{id}")
    Notification findById(@Param("id") Long id);

    @Insert("insert into notification(id,user_id,book_id,gmt_create,gmt_modified,status) " +
            "values(#{id},#{userId},#{bookId},#{gmtCreate},#{gmtModified},#{status})")
    void createNotification(Notification notification);

    @Select("select * from notification where status=#{status}")
    List<Notification> selectByStatus(@Param("status")Integer status);

    @Select("select count(1) from notification where status=#{status}")
    Integer notificationCountByStatus(@Param("status")Integer status);

    @Select("select * from notification where status=#{status} order by gmt_create limit #{offset},#{size}")//分页查询
    List<Notification> listByStatus(@Param(value = "offset") Integer offset, @Param(value = "size")Integer size,@Param(value = "status")Integer status);
}
