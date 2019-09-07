package com.bms.bms.controller;

import com.bms.bms.model.User;
import com.bms.bms.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class RegisterController {
    @Autowired
    RegisterService registerService;

    @GetMapping("/register")
    public String register(Model model){

        return "register";
    }

    @PostMapping("/register")// post方法给你请求
    public String doRegister(
            @RequestParam(value = "id",required = false)Long id,
            @RequestParam(value ="password",required = false)String password,
            HttpServletRequest request,
            HttpServletResponse response,
            Model model){
        if (registerService.register(id,password)){
            User user=registerService.findById(id);
            request.getSession().setAttribute("user",user);
            //将token写入cookie
            response.addCookie(new Cookie("token",user.getToken()));
            model.addAttribute("info","恭喜注册成功");
            //重定向回首页
            return "redirect:/";
        }

        return null;
    }


}
