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
    public String getStatus(Integer status){
        if (status.equals(NotificationStatusEnum.LENDING.getStatus()))return NotificationStatusEnum.LENDING.getMessage();
        if (status.equals(NotificationStatusEnum.REQUEST_RETURN.getStatus()))return NotificationStatusEnum.REQUEST_RETURN.getMessage();
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


    public PageDTO list(String search, Integer page, Integer size) {

        PageDTO<NotificationDTO> pageDTO=new PageDTO();
        Integer totalCount;
        NotificationDTO notificationDTO=new NotificationDTO();
            totalCount = notificationMapper.notificationCountByStatus(NotificationStatusEnum.REQUEST_RETURN.getStatus());//申请归还的消息总数
        pageDTO.setPageDTO(totalCount,page,size);

        Integer offset=size*(page-1);//偏移量
        List<Notification> notifications=notificationMapper.listByStatus(offset,size,NotificationStatusEnum.REQUEST_RETURN.getStatus());//分页
        List<NotificationDTO> notificationDTOS=ToDTOS(notifications);
        pageDTO.setDataDTOS(notificationDTOS);
        return pageDTO;
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
}
