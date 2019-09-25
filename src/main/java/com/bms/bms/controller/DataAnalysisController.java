package com.bms.bms.controller;

import com.alibaba.fastjson.JSONObject;
import com.bms.bms.model.User;
import com.bms.bms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class DataAnalysisController {

    @Autowired
    UserService userService;

    @GetMapping("/analyse/reader")
    public String reader(Model model){

        List<User> readerRank=userService.readerList();
        ArrayList readerName=new ArrayList();
        for(User user:readerRank){
            readerName.add(user.getName());
        }

        model.addAttribute("readerName",readerName);
        return "analysis";
    }

}
