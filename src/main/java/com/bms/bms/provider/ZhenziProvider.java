package com.bms.bms.provider;

import com.alibaba.fastjson.JSONObject;
import com.zhenzi.sms.ZhenziSmsClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Random;

@Component
public class ZhenziProvider {

    @Value("${zhenzi.appId}")
    private String appId;
    @Value("${zhenzi.appSecret}")
    private String appSecret;
    @Value("${zhenzi.apiUrl}")
    private String apiUrl;

    public Boolean sendVerifyCode(HttpServletRequest request,String phone) throws Exception {
//        String phone = request.getParameter("phone");
        JSONObject json = null;
        //生成6位验证码
        String verifyCode = String.valueOf(new Random().nextInt(899999) + 100000);
        //发送短信
        ZhenziSmsClient client = new ZhenziSmsClient(apiUrl, appId, appSecret);
        //短信模板
        String messageModel="您的验证码是"+verifyCode+"，5分钟内有效！";
        String result = client.send(phone, messageModel);
        //String result = "{\"code\":0}";//client.send(phone, "您的验证码为:" + verifyCode + "，该码有效期为5分钟，该码只能使用一次!");
        json = JSONObject.parseObject(result);
        if (json.getIntValue("code") != 0) {//发送短信失败
//            发送短信失败情况
            System.out.println(json.getIntValue("data"));
            return false;
        }
        //将验证码存到session中,同时存入创建时间
        HttpSession session = request.getSession();
        json = new JSONObject();
        json.put("phone", phone);
        json.put("verifyCode", verifyCode);
        json.put("createTime", System.currentTimeMillis());
        // 将认证码存入SESSION
        session.setAttribute("verify", json);
        //发送验证码成功
        return true;



        }

}
