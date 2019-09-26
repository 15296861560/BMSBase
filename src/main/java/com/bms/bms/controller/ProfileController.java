package com.bms.bms.controller;

import com.alibaba.fastjson.JSONObject;
import com.bms.bms.dto.HistoryDTO;
import com.bms.bms.dto.ResultDTO;
import com.bms.bms.dto.UserDTO;
import com.bms.bms.enums.NotificationStatusEnum;
import com.bms.bms.exception.CustomizeErrorCode;
import com.bms.bms.model.Book;
import com.bms.bms.model.Notification;
import com.bms.bms.model.User;
import com.bms.bms.provider.ZhenziProvider;
import com.bms.bms.service.NotificationService;
import com.bms.bms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ProfileController {

    @Autowired
    UserService userService;
    @Autowired
    NotificationService notificationService;
    @Autowired
    ZhenziProvider zhenziProvider;

    @GetMapping("/profile")
    public String displayProfile(Model model,
                                 HttpServletRequest httpServletRequest){
        User user=(User)httpServletRequest.getSession().getAttribute("user");
        if (user==null){//未登录
            return "redirect:/noLogin";
        }
        user=userService.findById(user.getId());//更新user信息
        UserDTO userDTO=userService.userToDTO(user);

        model.addAttribute("userDTO",userDTO);

        return "profile";
    }

    @GetMapping("/profile/history")
    public String displayHistory(Model model,
                                 HttpServletRequest httpServletRequest){
        User user=(User)httpServletRequest.getSession().getAttribute("user");
        Long userId=user.getId();
        List<Notification> notifications=notificationService.listByUserId(userId);
        List<HistoryDTO> historyDTOS=new ArrayList<>();
        for (Notification notification:notifications){//循环将notification转换为historyDTO并将其加入列表中
            HistoryDTO historyDTO = new HistoryDTO();
            historyDTO.setId(notification.getId());
            historyDTO.setBookId(notification.getBookId());
            Book book=notificationService.findBookById(notification.getBookId());
            historyDTO.setBookNumber(book.getNumber());
            historyDTO.setBookName(book.getName());
            historyDTO.setBookCover(book.getCover());
            historyDTO.setGmtCreate(notification.getGmtCreate());
            historyDTO.setGmtModified(notification.getGmtModified());
            for (NotificationStatusEnum notificationStatusEnum:NotificationStatusEnum.values()) {
                if(notificationStatusEnum.getStatus().equals(notification.getStatus()))
                    historyDTO.setStatus(notificationStatusEnum.getMessage());
            }
            historyDTOS.add(historyDTO);
        }


        model.addAttribute("historyDTOS",historyDTOS);

        return "history";
    }

    @GetMapping("/profile/apply/{notificationId}")
    public String apply(Model model,
                        @PathVariable(name = "notificationId")Long notificationId){

        notificationService.setStatusToRequestReturn(notificationId);
        model.addAttribute("tip","提交申请归还成功请等待管理员同意");
        model.addAttribute("src","/profile/history");
        return "tip";
    }

    @GetMapping("/profile/phone")
    public String bindingPhone(Model model,
                               HttpServletRequest request){


        return "phone";
    }

    @ResponseBody//把页面转化成其它结构
    @RequestMapping(value = "/profile/phone",method = RequestMethod.POST)
    public Object post(@RequestBody String data,
                       HttpServletRequest request){
        JSONObject dataJson = JSONObject.parseObject(data);
        String phone=dataJson.getString("phone");
        try {
            if (zhenziProvider.sendVerifyCode(request,phone))
                return ResultDTO.okOf();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultDTO.errorOf(CustomizeErrorCode.VERIFYCODE_SEND_FAIL);
    }

    @ResponseBody
    @RequestMapping(value = "/profile/phone/verify",method = RequestMethod.POST)
    public Object verify(@RequestBody JSONObject dataJson,
                       HttpServletRequest request){
        String phone=dataJson.getString("phone");
        String verifyCode=dataJson.getString("verifyCode");
        //获取存在session的验证信息
        JSONObject verify=(JSONObject)request.getSession().getAttribute("verify");
        String phone2=verify.getString("phone");
        String verifyCode2=verify.getString("verifyCode");
        //进行验证
        if (phone.equals(phone2)&&verifyCode.equals(verifyCode2)){
//            验证成功绑定手机号
            User userSession=(User)request.getSession().getAttribute("user");
            User user=userService.findById(userSession.getId());
            userService.bindingPhone(user,phone);
            return ResultDTO.okOf();
        }
        else return ResultDTO.errorOf(CustomizeErrorCode.VERIFYCODE_VERIFY_FAIL);
    }



}
