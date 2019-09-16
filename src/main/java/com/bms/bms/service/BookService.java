package com.bms.bms.service;

import com.bms.bms.dto.BookDTO;
import com.bms.bms.dto.BookDTO;
import com.bms.bms.dto.BookQueryDTO;
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


    public PageDTO list(String search, Integer page, Integer size,String attribute) {

        if (StringUtils.isNotBlank(search)){
            //用空格分隔search
            String[] tags = StringUtils.split(search, " ");
            //用|把刚刚分隔的字符串重新拼接
            String regexTag = Arrays.stream(tags).collect(Collectors.joining("|"));
            search=regexTag;

        }

        BookQueryDTO bookQueryDTO=new BookQueryDTO();
        bookQueryDTO.setAttribute(attribute);
        bookQueryDTO.setSearch(search);
        bookQueryDTO.setTotalCount(getTotalCount(bookQueryDTO));
        bookQueryDTO.setSize(size);
        PageDTO<BookDTO> pageDTO=new PageDTO();
        pageDTO.setPageDTO(bookQueryDTO.getTotalCount(),page,size);
        Integer offset=size*(page-1);//偏移量
        bookQueryDTO.setOffset(offset);
        List<Book> books=getBooks(bookQueryDTO);
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

    private Integer getTotalCount(BookQueryDTO bookQueryDTO){
        Integer totalCount=0;
        if (StringUtils.isBlank(bookQueryDTO.getSearch())) {
            totalCount = bookMapper.bookCountAll();//书的总数
        }else if ("name".equals(bookQueryDTO.getAttribute())){
            totalCount = bookMapper.searchCountByName(bookQueryDTO.getSearch());//符合搜索条件的书的总数
        }else {
            totalCount = bookMapper.searchCountByType(bookQueryDTO.getSearch());//符合搜索条件的书的总数
        }
        return totalCount;
    }

    private List<Book> getBooks(BookQueryDTO bookQueryDTO){
        List<Book> books=new ArrayList();
        if (StringUtils.isBlank(bookQueryDTO.getSearch())) {
            books = bookMapper.listAll(bookQueryDTO.getOffset(),bookQueryDTO.getSize());//普通分页

        }else if ("name".equals(bookQueryDTO.getAttribute())){
            books = bookMapper.listSearch(bookQueryDTO);//带搜索条件的分页
        }else {
            books = bookMapper.listSearchByType(bookQueryDTO);
        }
        return books;
    }




}
