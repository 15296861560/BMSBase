package com.bms.bms.controller;

import com.bms.bms.dto.NotificationDTO;
import com.bms.bms.dto.PageDTO;
import com.bms.bms.enums.NotificationStatusEnum;
import com.bms.bms.model.Admin;
import com.bms.bms.model.Notification;
import com.bms.bms.service.BookService;
import com.bms.bms.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ManageController {
    @Autowired
    NotificationService notificationService;
    @Autowired
    BookService bookService;

    @GetMapping("/manage")
    public String manage(Model model,
                         @RequestParam(name="page",defaultValue = "1")Integer page,//通过@RequestParam注解获取名字为page的参数默认值为1类型为Integer
                         @RequestParam(name="size",defaultValue = "5")Integer size,
                         HttpServletRequest httpServletRequest){
        Admin admin=(Admin)httpServletRequest.getSession().getAttribute("admin");
        if (admin==null){//未登录
            return "redirect:/noLogin";
        }

        PageDTO pageDTO=notificationService.list(page,size,NotificationStatusEnum.REQUEST_BORROW.getStatus());
        model.addAttribute("pageDTO",pageDTO);
        model.addAttribute("section","manage");

        return "manage";
    }

    @GetMapping("/manage/{action}/{notificationId}")
    public String handleBorrow(@PathVariable(name = "action")String action,
                         @PathVariable(name = "notificationId")Long notificationId
    ){
        //处理消息
        if ("agree".equals(action)) {

            notificationService.agree(notificationId);
            
        }
        if ("reject".equals(action))
            notificationService.borrowFail(notificationId);
        return "redirect:/manage";
    }

    @GetMapping("/sendback")
    public String sendback(Model model,
                           @RequestParam(name="page",defaultValue = "1")Integer page,//通过@RequestParam注解获取名字为page的参数默认值为1类型为Integer
                           @RequestParam(name="size",defaultValue = "5")Integer size,
                           HttpServletRequest httpServletRequest){

        Admin admin=(Admin)httpServletRequest.getSession().getAttribute("admin");
        if (admin==null){//未登录
            return "redirect:/noLogin";
        }

        PageDTO pageDTO=notificationService.list(page,size, NotificationStatusEnum.REQUEST_RETURN.getStatus());
        model.addAttribute("pageDTO",pageDTO);
        model.addAttribute("section","sendback");

        return "manage";
    }

    @GetMapping("/sendback/{action}/{notificatinId}")
    public String handle(@PathVariable(name = "action")String action,
                         @PathVariable(name = "notificatinId")Long notificatinId
                         ){
        //改变该消息的状态
        if ("agree".equals(action))
            notificationService.sendbackSuccess(notificatinId);
        if ("reject".equals(action))
            notificationService.setStatusToLending(notificatinId);
        return "redirect:/sendback";
    }

    @GetMapping("/checkBook")
    public String search(Model model,
                         @RequestParam(name="page",defaultValue = "1")Integer page,
                         @RequestParam(name="size",defaultValue = "9")Integer size,
                         @RequestParam(name="search",required = false)String search,
                         HttpServletRequest httpServletRequest){

        Admin admin=(Admin)httpServletRequest.getSession().getAttribute("admin");
        if (admin==null){//未登录
            return "redirect:/noLogin";
        }

        PageDTO pageDTO=bookService.list(search,page,size,"name");
        model.addAttribute("pageDTO",pageDTO);
        model.addAttribute("section","checkBook");
        model.addAttribute("search",search);
        return "manage";
    }





}
