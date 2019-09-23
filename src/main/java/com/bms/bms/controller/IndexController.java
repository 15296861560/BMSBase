package com.bms.bms.controller;

import com.bms.bms.exception.CustomizeErrorCode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/")
    public String index(){


        return "index";
    }

    @GetMapping("/noLogin")
    public String noLogin(Model model){

        model.addAttribute("errorMessage", CustomizeErrorCode.NO_LOGIN.getMessage());
        model.addAttribute("errorCode", CustomizeErrorCode.NO_LOGIN.getCode());
        return "error";
    }

}
