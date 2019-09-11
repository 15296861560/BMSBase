package com.bms.bms.controller;

import com.bms.bms.dto.NotificationDTO;
import com.bms.bms.dto.PageDTO;
import com.bms.bms.service.BookService;
import com.bms.bms.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ManageController {
    @Autowired
    NotificationService notificationService;
    @Autowired
    BookService bookService;

    @GetMapping("/manage")
    public String manage(Model model){
        return "redirect:/sendback";
    }

    @GetMapping("/sendback")
    public String sendback(Model model,
                           @RequestParam(name="page",defaultValue = "1")Integer page,//通过@RequestParam注解获取名字为page的参数默认值为1类型为Integer
                           @RequestParam(name="size",defaultValue = "5")Integer size,
                           @RequestParam(name="search",required = false)String search){

        PageDTO pageDTO=notificationService.list(search,page,size);
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
            notificationService.sendbackReject(notificatinId);
        return "redirect:/sendback";
    }

    @GetMapping("/checkBook")
    public String search(Model model,
                         @RequestParam(name="page",defaultValue = "1")Integer page,
                         @RequestParam(name="size",defaultValue = "9")Integer size,
                         @RequestParam(name="search",required = false)String search){

        PageDTO pageDTO=bookService.list(search,page,size);
        model.addAttribute("pageDTO",pageDTO);
        model.addAttribute("section","checkBook");
        return "manage";
    }



}
