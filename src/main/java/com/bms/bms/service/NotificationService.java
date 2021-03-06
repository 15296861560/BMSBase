package com.bms.bms.service;

import com.bms.bms.dto.NotificationDTO;
import com.bms.bms.dto.PageDTO;
import com.bms.bms.enums.BookStatusEnum;
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
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

    @Transactional
    public void sendbackSuccess(Long notificatinId) {//归还成功
        //消息处理
        Notification notification=notificationMapper.findById(notificatinId);
        notification.setStatus(NotificationStatusEnum.RETUENED.getStatus());
        notification.setGmtModified(System.currentTimeMillis());
        notificationMapper.upadteNotification(notification);
        //书籍信息变更
        Book book=findBookById(notification.getBookId());
        book.setStatus("GOOD");
        book.setGmtModified(System.currentTimeMillis());
        bookMapper.changeBookStatus(book);
        //用户信息改变
        User user=findUserById(notification.getUserId());
        user.setStatus(user.getStatus()-1);
        user.setGmtModified(System.currentTimeMillis());
        userMapper.update(user);
    }

    @Transactional
    public void setStatusToRequestReturn(Long notificatinId) {
        Notification notification=notificationMapper.findById(notificatinId);
        notification.setStatus(NotificationStatusEnum.REQUEST_RETURN.getStatus());
        notification.setGmtModified(System.currentTimeMillis());
        notificationMapper.upadteNotification(notification);
        //将图书状态修改为申请归还中
        Book book=bookMapper.findById(notification.getBookId());
        book.setStatus(BookStatusEnum.APPLY_RETURN.getStatus());
        bookMapper.changeBookStatus(book);
    }


    public void borrowFail(Long notificatinId) {
        Notification notification=notificationMapper.findById(notificatinId);
        notificationMapper.deleteNotification(notificatinId);
    }

    @Transactional
    public void agree(Long notificationId) {//同意借出
        //改变消息状态
        setStatusToLending(notificationId);
        Notification notification=findById(notificationId);
        //改变书籍状态
        Book book=findBookById(notification.getBookId());
        book.setStatus("LENDING");
        book.setGmtModified(System.currentTimeMillis());
        book.setLendCount(book.getLendCount()+1);
        bookMapper.changeBookStatus(book);
        //改变用户信息
        User user=findUserById(notification.getUserId());
        user.setStatus(user.getStatus()+1);
        user.setBorrowCount(user.getBorrowCount()+1);
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        String history=user.getHistory()+"  "+simpleDateFormat.format(System.currentTimeMillis())+"借阅了<<"+book.getName()+">>;";
        user.setHistory(history);
        user.setGmtModified(System.currentTimeMillis());
        userMapper.update(user);



    }
}
