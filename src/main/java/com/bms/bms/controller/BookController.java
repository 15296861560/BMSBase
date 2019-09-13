package com.bms.bms.controller;

import com.bms.bms.enums.BookTypeEnum;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BookController {

    @GetMapping("/book")
    public String book(Model model){

        model.addAttribute("classify", BookTypeEnum.values());
        return "book";
    }
}
