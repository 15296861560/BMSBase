package com.bms.bms.controller;

import com.bms.bms.enums.BookTypeEnum;
import com.bms.bms.exception.CustomizeErrorCode;
import com.bms.bms.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Controller
public class UploadController {
    @Autowired
    BookService bookService;

    @GetMapping("/upload")
    public String upload(Model model){


        model.addAttribute("bookType", BookTypeEnum.values());
        model.addAttribute("section","upload");
        return "manage";
    }

    @PostMapping("/upload")
    public String doupload(Model model,
                         @RequestParam(value ="name",required = false)String name,
                           @RequestParam(value = "number",required = false)Long number,
                           @RequestParam(value ="type",required = false)String type,
                           @RequestParam(value = "num",required = false)Long num,
                         @RequestParam(value ="brief",required = false)String brief,
                         @RequestParam(value ="file",required = false) MultipartFile file){


        String fileName=file.getOriginalFilename();
        // 要上传的目标文件存放路径
        String localPath = "D:/Test/BMS/src/main/resources/static/images/cover";
        // 上传成功或者失败的提示
        String msg = "";

        if (uploadfile(file, localPath, fileName)){
            // 上传成功，给出页面提示
            msg = "success";
        }else {
            msg = "fail";
        }

        if (msg.equals("fail")){
            model.addAttribute("errorMessage",CustomizeErrorCode.BOOK_UPLOAD_FAIL.getMessage());
            model.addAttribute("errorCode", CustomizeErrorCode.BOOK_UPLOAD_FAIL.getCode());
            return "error";
        }


        String src="/images/cover/"+fileName;
        for(BookTypeEnum bookTypeEnum:BookTypeEnum.values()){
            if (type.equals(bookTypeEnum.getMessage()))
                type=bookTypeEnum.getType();
        }
//        将书籍信息存入数据库
        for (int i = 0; i <num; i++) {//上传num本相同书籍
            bookService.createBook(name,number,type,brief,src);
        }

        model.addAttribute("bookName",name);
//        model.addAttribute("src",src);图片延迟缓存，有时会加载不出，所以取消上传成功后显示图片
        model.addAttribute("msg",msg);
        model.addAttribute("bookType", BookTypeEnum.values());
        model.addAttribute("section","upload");
        return "manage";
    }

    public static boolean uploadfile(MultipartFile file, String path, String fileName){

        // 生成新的文件名
        //String realPath = path + "/" + FileNameUtils.getFileName(fileName);

        //使用原文件名
        String realPath = path + "/" + fileName;

        File dest = new File(realPath);

        //判断文件父目录是否存在
        if(!dest.getParentFile().exists()){
            dest.getParentFile().mkdir();
        }

        try {
            //保存文件
            file.transferTo(dest);
            return true;
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }

    }


}
