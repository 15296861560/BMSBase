package com.bms.bms.controller;

import com.bms.bms.dto.HistoryDTO;
import com.bms.bms.dto.PageDTO;
import com.bms.bms.dto.UserDTO;
import com.bms.bms.enums.NotificationStatusEnum;
import com.bms.bms.model.Book;
import com.bms.bms.model.Notification;
import com.bms.bms.model.User;
import com.bms.bms.service.NotificationService;
import com.bms.bms.service.UserService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ProfileController {

    @Autowired
    UserService userService;
    @Autowired
    NotificationService notificationService;

    @GetMapping("/profile")
    public String displayProfile(Model model,
                                 HttpServletRequest httpServletRequest){
        User user=(User)httpServletRequest.getSession().getAttribute("user");
        UserDTO userDTO=new UserDTO();
        userDTO=userService.userToDTO(user);

        model.addAttribute("userDTO",userDTO);

        return "profile";
    }

    @GetMapping("/history")
    public String displayHistory(Model model,
                                 HttpServletRequest httpServletRequest){
        User user=(User)httpServletRequest.getSession().getAttribute("user");
        Long userId=user.getId();
        List<Notification> notifications=notificationService.listByUserId(userId);
        List<HistoryDTO> historyDTOS=new ArrayList<>();
        for (Notification notification:notifications){//循环将notification转换为historyDTO并将其加入列表中
            HistoryDTO historyDTO = new HistoryDTO();
            historyDTO.setId(notification.getId());
            historyDTO.setBookId(notification.getBookId());
            Book book=notificationService.findBookById(notification.getBookId());
            historyDTO.setBookNumber(book.getNumber());
            historyDTO.setBookName(book.getName());
            historyDTO.setBookCover(book.getCover());
            historyDTO.setGmtCreate(notification.getGmtCreate());
            historyDTO.setGmtModified(notification.getGmtModified());
            for (NotificationStatusEnum notificationStatusEnum:NotificationStatusEnum.values()) {
                if(notificationStatusEnum.getStatus().equals(notification.getStatus()))
                    historyDTO.setStatus(notificationStatusEnum.getMessage());
            }
            historyDTOS.add(historyDTO);
        }


        model.addAttribute("historyDTOS",historyDTOS);

        return "history";
    }



}
