package com.bms.bms.controller;

import com.bms.bms.dto.NotificationDTO;
import com.bms.bms.dto.PageDTO;
import com.bms.bms.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ManageController {
    @Autowired
    NotificationService notificationService;

    @GetMapping("/manage")
    public String manage(Model model){
        return "redirect:/sendback";
    }

    @GetMapping("/sendback")
    public String sendback(Model model,
                           @RequestParam(name="page",defaultValue = "1")Integer page,//通过@RequestParam注解获取名字为page的参数默认值为1类型为Integer
                           @RequestParam(name="size",defaultValue = "5")Integer size,
                           @RequestParam(name="search",required = false)String search){

//        List<NotificationDTO> notificationDTOS=notificationService.listByNotificationId(1);
        PageDTO pageDTO=notificationService.list(search,page,size);
//        model.addAttribute("notificationDTOS",notificationDTOS);
        model.addAttribute("pageDTO",pageDTO);
        model.addAttribute("section","sendback");

        return "manage";
    }
}
