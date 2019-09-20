package com.bms.bms.controller;

import com.bms.bms.exception.CustomizeErrorCode;
import com.bms.bms.model.User;
import com.bms.bms.service.UserService;
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
    UserService userService;

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
        String result = userService.register(id, password);
        if (result.equals("sussess")){
            User user= userService.findById(id);
            request.getSession().setAttribute("user",user);
            //将token写入cookie
            response.addCookie(new Cookie("token",user.getToken()));
            //重定向回首页
            return "redirect:/book";
        }else if (result.equals(CustomizeErrorCode.REGISTER_FAIL_ID_NOT_FOUND.getMessage())){
            model.addAttribute("errorMessage",result);
            model.addAttribute("errorCode",CustomizeErrorCode.REGISTER_FAIL_ID_NOT_FOUND.getCode());
            return "error";
        }else if (result.equals(CustomizeErrorCode.REGISTER_FAIL_ID_REGISTERED.getMessage())){
            model.addAttribute("errorMessage",result);
            model.addAttribute("errorCode",CustomizeErrorCode.REGISTER_FAIL_ID_REGISTERED.getCode()
            );
            return "error";
        }

        model.addAttribute("errorMessage",CustomizeErrorCode.UNKNOWN_ERROR);
        return "error";
    }


}
