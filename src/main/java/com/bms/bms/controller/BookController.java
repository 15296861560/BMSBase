package com.bms.bms.controller;

import com.bms.bms.dto.BookDTO;
import com.bms.bms.dto.PageDTO;
import com.bms.bms.enums.BookStatusEnum;
import com.bms.bms.enums.BookTypeEnum;
import com.bms.bms.model.Book;
import com.bms.bms.model.Notification;
import com.bms.bms.model.User;
import com.bms.bms.service.BookService;
import com.bms.bms.service.NotificationService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class BookController {

    @Autowired
    BookService bookService;
    @Autowired
    NotificationService notificationService;

    @GetMapping("/book")
    public String book(Model model,
                       @RequestParam(name="page",defaultValue = "1")Integer page,
                       @RequestParam(name="size",defaultValue = "9")Integer size,
                       @RequestParam(name="search",required = false)String search,
                       @RequestParam(name="attribute",defaultValue = "name")String attribute){

        PageDTO pageDTO=bookService.list(search,page,size,attribute);
        model.addAttribute("pageDTO",pageDTO);
        model.addAttribute("classify", BookTypeEnum.values());
        model.addAttribute("attribute", attribute);
        model.addAttribute("search", search);
        if ("type".equals(attribute)) model.addAttribute("classic", BookTypeEnum.valueOf(search).getMessage());
        return "book";
    }

    @GetMapping("/book/{bookId}")
    public String bookDisplay(Model model,
                       @PathVariable(name = "bookId")Long bookId){
        Book book=bookService.findBookById(bookId);
        BookDTO bookDTO=new BookDTO();
        BeanUtils.copyProperties(book,bookDTO);//把book的所有属性拷贝到bookDTO上面
        bookDTO.setStatus(BookStatusEnum.valueOf(book.getStatus()).getMessage());
        bookDTO.setType(BookTypeEnum.valueOf(book.getType()).getMessage());
        model.addAttribute("bookDTO",bookDTO);
        return "singleBook";
    }

    @GetMapping("/book/apply/{bookId}")
    public String apply(Model model,
                              HttpServletRequest request,
                              @PathVariable(name = "bookId")Long bookId){
        User user = (User) request.getSession().getAttribute("user");
        if (user==null){//未登录
            return "redirect:/noLogin";
        }
        //创建借阅消息
        Notification notification=new Notification();
        notification.setUserId(user.getId());
        notification.setBookId(bookId);
        notification.setGmtCreate(System.currentTimeMillis());
        notification.setGmtModified(System.currentTimeMillis());
        notification.setStatus(2);
        notificationService.createNotify(notification);
        //将图书状态变为申请借阅中
        bookService.changeBookStatus(bookId,BookStatusEnum.APPLY_BORROW.getStatus());
        model.addAttribute("tip","申请成功请等待管理员同意");
        model.addAttribute("src","/book");
        return "tip";
    }
}
