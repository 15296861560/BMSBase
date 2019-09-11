package com.bms.bms.controller;

import com.bms.bms.enums.BookTypeEnum;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UploadController {

    @GetMapping("/upload")
    public String upload(Model model){

        model.addAttribute("bookType", BookTypeEnum.values());
        model.addAttribute("section","upload");
        return "manage";
    }
}
