package com.bms.bms.service;

import com.bms.bms.dto.NotificationDTO;
import com.bms.bms.dto.PageDTO;
import com.bms.bms.enums.NotificationStatusEnum;
import com.bms.bms.mapper.BookMapper;
import com.bms.bms.mapper.NotificationMapper;
import com.bms.bms.mapper.UserMapper;
import com.bms.bms.model.Book;
import com.bms.bms.model.Notification;
import com.bms.bms.model.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService {
    @Autowired
    private NotificationMapper notificationMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private BookMapper bookMapper;

    public User findUserById(Long id){
        return userMapper.findById(id);
    }
    public Book findBookById(Long id){
        return bookMapper.findById(id);
    }
    public Notification findById(Long id){
        return notificationMapper.findById(id);
    }
    public String getStatus(Integer status){
        if (status.equals(NotificationStatusEnum.LENDING.getStatus()))return NotificationStatusEnum.LENDING.getMessage();
        if (status.equals(NotificationStatusEnum.REQUEST_RETURN.getStatus()))return NotificationStatusEnum.REQUEST_RETURN.getMessage();
        if (status.equals(NotificationStatusEnum.REQUEST_BORROW.getStatus()))return NotificationStatusEnum.REQUEST_BORROW.getMessage();
        if (status.equals(NotificationStatusEnum.RETUENED.getStatus()))return NotificationStatusEnum.RETUENED.getMessage();
        return "没有该状态";
    }

    public void createNotify(Notification notification){
        notificationMapper.createNotification(notification);
    }

    public List<NotificationDTO> listByNotificationId(Integer status) {
        List<Notification> notifications=notificationMapper.selectByStatus(status);//申请归还的消息
        return ToDTOS(notifications);

    }


    public PageDTO list(Integer page, Integer size,Integer status) {

        PageDTO<NotificationDTO> pageDTO=new PageDTO();
        Integer totalCount;
            totalCount = notificationMapper.notificationCountByStatus(status);//状态为status的消息总数
        pageDTO.setPageDTO(totalCount,page,size);

        Integer offset=size*(page-1);//偏移量
        List<Notification> notifications=notificationMapper.listByStatus(offset,size,status);//分页
        List<NotificationDTO> notificationDTOS=ToDTOS(notifications);
        pageDTO.setDataDTOS(notificationDTOS);
        return pageDTO;
    }

    public List<Notification> listByUserId(Long userId){

        List<Notification> notifications=notificationMapper.listByUserId(userId);//查询某用户的所有消息记录
        return notifications;


    }

    //        将notifications转化为notificationDTOS
    private List<NotificationDTO> ToDTOS(List<Notification> notifications){

        List<NotificationDTO> notificationDTOS=new ArrayList();
        for(Notification notification:notifications){
            NotificationDTO notificationDTO=new NotificationDTO();
            notificationDTO.setId(notification.getId());
            notificationDTO.setUser(userMapper.findById(notification.getUserId()));
            notificationDTO.setBook(bookMapper.findById(notification.getBookId()));
            notificationDTO.setGmtCreate(notification.getGmtCreate());
            notificationDTO.setStatus(getStatus(notification.getStatus()));
            notificationDTOS.add(notificationDTO);
        }
        return notificationDTOS;

    }

    public void setStatusToLending(Long notificatinId) {
        Notification notification=notificationMapper.findById(notificatinId);
        notification.setStatus(NotificationStatusEnum.LENDING.getStatus());
        notification.setGmtModified(System.currentTimeMillis());
        notificationMapper.upadteNotification(notification);
    }

        public void sendbackSuccess(Long notificatinId) {
        Notification notification=notificationMapper.findById(notificatinId);
        notification.setStatus(NotificationStatusEnum.RETUENED.getStatus());
        notification.setGmtModified(System.currentTimeMillis());
        notificationMapper.upadteNotification(notification);
    }

    public void setStatusToRequestReturn(Long notificatinId) {
        Notification notification=notificationMapper.findById(notificatinId);
        notification.setStatus(NotificationStatusEnum.REQUEST_RETURN.getStatus());
        notification.setGmtModified(System.currentTimeMillis());
        notificationMapper.upadteNotification(notification);
    }


    public void borrowFail(Long notificatinId) {
        Notification notification=notificationMapper.findById(notificatinId);
        notificationMapper.deleteNotification(notificatinId);
    }

}
