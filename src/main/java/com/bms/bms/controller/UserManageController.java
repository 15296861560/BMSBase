package com.bms.bms.controller;

import com.bms.bms.dto.PageDTO;
import com.bms.bms.model.Admin;
import com.bms.bms.service.BookService;
import com.bms.bms.service.NotificationService;
import com.bms.bms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class UserManageController {
    @Autowired
    UserService userService;


    @GetMapping("/checkUser")
    public String searchUser(Model model,
                         @RequestParam(name="page",defaultValue = "1")Integer page,
                         @RequestParam(name="size",defaultValue = "9")Integer size,
                         @RequestParam(name="search",required = false)String search){

        PageDTO pageDTO=userService.list(search,page,size);
        model.addAttribute("pageDTO",pageDTO);
        model.addAttribute("section","checkUser");
        return "manage-user";
    }

    @GetMapping("/manageUser")
    public String manageUser(Model model,
                             @RequestParam(name="page",defaultValue = "1")Integer page,
                             @RequestParam(name="size",defaultValue = "9")Integer size,
                             @RequestParam(name="search",required = false)String search,
                             HttpServletRequest httpServletRequest){

        Admin admin=(Admin)httpServletRequest.getSession().getAttribute("admin");
        if (admin==null){//未登录
            return "redirect:/noLogin";
        }

        PageDTO pageDTO=userService.list(search,page,size);
        model.addAttribute("pageDTO",pageDTO);
        model.addAttribute("section","manageUser");
        return "manage-user";
    }

    @GetMapping("/manageUser/delete/{userId}")
    public String deleteUser(Model model,
                             @RequestParam(name="page",defaultValue = "1")Integer page,
                             @RequestParam(name="size",defaultValue = "9")Integer size,
                             @RequestParam(name="search",required = false)String search,
                             @PathVariable(name = "userId")Long userId){


        userService.deleteById(userId);
        PageDTO pageDTO=userService.list(search,page,size);
        model.addAttribute("pageDTO",pageDTO);
        model.addAttribute("section","manageUser");
        return "manage-user";

    }



}
