package com.bms.bms.service;

import com.bms.bms.dto.BookDTO;
import com.bms.bms.dto.BookDTO;
import com.bms.bms.dto.PageDTO;
import com.bms.bms.enums.BookStatusEnum;
import com.bms.bms.enums.BookTypeEnum;
import com.bms.bms.enums.NotificationStatusEnum;
import com.bms.bms.mapper.BookMapper;
import com.bms.bms.mapper.NotificationMapper;
import com.bms.bms.mapper.UserMapper;
import com.bms.bms.model.Book;
import com.bms.bms.model.Notification;
import com.bms.bms.model.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {
    @Autowired
    private NotificationMapper notificationMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private BookMapper bookMapper;

    public Book findBookById(Long id){
        return bookMapper.findById(id);
    }

    public void createBook(String name, Long number, String type, String brief, String src){
        Book book=new Book();
        book.setName(name);
        book.setNumber(number);
        book.setType(type);
        book.setBrief(brief);
        book.setCover(src);
        book.setGmtCreate(System.currentTimeMillis());
        book.setGmtModified(System.currentTimeMillis());
        book.setLendCount(0);
        book.setStatus("GOOD");
        bookMapper.createBook(book);
    }

    public List<BookDTO> listByBookId(Integer status) {
        List<Book> books=bookMapper.selectAll();//所有书籍
        return ToDTOS(books);

    }


    public PageDTO list(String search, Integer page, Integer size) {

        if (StringUtils.isNotBlank(search)){
            //用空格分隔search
            String[] tags = StringUtils.split(search, " ");
            //用|把刚刚分隔的字符串重新拼接
            String regexTag = Arrays.stream(tags).collect(Collectors.joining("|"));
            search=regexTag;

        }

        PageDTO<BookDTO> pageDTO=new PageDTO();
        Integer totalCount;
        if (StringUtils.isNotBlank(search)) {
            totalCount = bookMapper.searchCount(search);//符合搜索条件的书的总数
        }else {
            totalCount = bookMapper.bookCountAll();//书的总数

        }
        pageDTO.setPageDTO(totalCount,page,size);
        Integer offset=size*(page-1);//偏移量
        List<Book> books=new ArrayList();
        if (StringUtils.isNotBlank(search)) {
            books = bookMapper.listSearch(offset, size,search);//带搜索条件的分页
        }else {
           books = bookMapper.listAll(offset, size);//普通分页
        }
        List<BookDTO> bookDTOS=ToDTOS(books);
        pageDTO.setDataDTOS(bookDTOS);
        return pageDTO;
    }

    //        将model转化为DTO
    private List<BookDTO> ToDTOS(List<Book> books){

        List<BookDTO> bookDTOS=new ArrayList();
        for(Book book:books){
            BookDTO bookDTO=new BookDTO();
            BeanUtils.copyProperties(book,bookDTO);//把book的所有属性拷贝到bookDTO上面
            bookDTO.setStatus(BookStatusEnum.valueOf(book.getStatus()).getMessage());
            bookDTO.setType(BookTypeEnum.valueOf(book.getType()).getMessage());
            bookDTOS.add(bookDTO);
        }
        return bookDTOS;

    }




}
