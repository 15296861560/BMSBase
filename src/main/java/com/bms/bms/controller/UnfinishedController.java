package com.bms.bms.controller;

import com.bms.bms.exception.CustomizeErrorCode;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UnfinishedController {

    @GetMapping("/advice")
    public String advice(Model model){

        model.addAttribute("tip", CustomizeErrorCode.UNFINISHED.getMessage());
        model.addAttribute("src","/");
        return "tip";
    }

    @GetMapping("/about")
    public String about(Model model){

        model.addAttribute("tip", CustomizeErrorCode.UNFINISHED.getMessage());
        model.addAttribute("src","/");
        return "tip";
    }

}
