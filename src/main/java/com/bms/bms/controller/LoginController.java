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
        model.addAttribute("account","管理员账号");
        model.addAttribute("account","管理员密码");


        return "login";
    }

    @GetMapping("/login/user")
    public String login_user(Model model){

        return "login";
    }
}
