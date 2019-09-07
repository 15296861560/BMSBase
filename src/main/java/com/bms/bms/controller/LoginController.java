package com.bms.bms.controller;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class LoginController {

    @GetMapping("/login/admin")
    public String login_admin(Model model){
        model.addAttribute("img_src","/images/admin.png");
        model.addAttribute("tip","管理员登录");
        model.addAttribute("account","管理员账号:");
        model.addAttribute("pwd","管理员密码:");
        model.addAttribute("action","登录");

        return "login";
    }

    @GetMapping("/login/user")
    public String login_user(Model model){
        model.addAttribute("img_src","/images/user_login.png");
        model.addAttribute("tip","读者登录");
        model.addAttribute("account","读者账号:");
        model.addAttribute("pwd","读者密码:");
        model.addAttribute("action","登录");

        return "login";
    }

}
