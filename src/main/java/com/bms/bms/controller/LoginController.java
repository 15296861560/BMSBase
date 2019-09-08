package com.bms.bms.controller;

import com.bms.bms.model.User;
import com.bms.bms.service.AdminService;
import com.bms.bms.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LoginController {
    @Autowired
    AdminService adminService;
    @Autowired
    UserService userService;

    @GetMapping("/login/admin")
    public String login_admin(Model model){
        model.addAttribute("img_src","/images/admin.png");
        model.addAttribute("tip","管理员登录");
        model.addAttribute("account","管理员账号:");
        model.addAttribute("pwd","管理员密码:");
        model.addAttribute("action","登录");

        return "login";
    }

    @PostMapping("/login/admin")// post方法给你请求
    public String doLoginAdmin(
            @RequestParam(value = "id",required = false)Long id,
            @RequestParam(value ="password",required = false)String password,
            HttpServletRequest request,
            HttpServletResponse response,
            Model model){
        if (adminService.confirm(id,password))
        return "redirect:/teacher";
        else {
//            登录失败
            return null;
        }
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


    @PostMapping("/login/user")// post方法给你请求
    public String doLoginUser(
            @RequestParam(value = "id",required = false)Long id,
            @RequestParam(value ="password",required = false)String password,
            HttpServletRequest request,
            HttpServletResponse response,
            Model model){
        if (userService.confirm(id,password)){
            User user= userService.findById(id);
            request.getSession().setAttribute("user",user);
            //将token写入cookie
            response.addCookie(new Cookie("token",user.getToken()));
            return "redirect:/book";

        }
        else {
//            登录失败
            return null;
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request,HttpServletResponse response){
        request.getSession().removeAttribute("user");//移除session中的user
        //删除cookie中的token
        Cookie cookie = new Cookie("token", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";
    }

}
