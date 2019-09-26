package com.bms.bms.controller;

import com.alibaba.fastjson.JSONObject;
import com.bms.bms.model.Book;
import com.bms.bms.model.User;
import com.bms.bms.service.BookService;
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

    @Autowired
    BookService bookService;

    @GetMapping("/analyse/reader")
    public String reader(Model model){

        List<User> readerRank=userService.readerList();
//        ArrayList readerName=new ArrayList();
//        for(User user:readerRank){
//            readerName.add(user.getName());
//        }

        model.addAttribute("readerRank",readerRank);
        return "analysis";
    }

    @GetMapping("/analyse/book")
    public String book(Model model){

        List<Book> bookRank=bookService.bookRankList();
//        ArrayList bookName=new ArrayList();
////        for(Book book:bookRank){
////            bookName.add(book.getName());
////        }
//        model.addAttribute("readerName",bookName);
        model.addAttribute("bookRank",bookRank);
        return "analysis-book";
    }

}
