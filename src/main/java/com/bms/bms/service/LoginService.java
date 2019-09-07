package com.bms.bms.service;

import com.bms.bms.mapper.AdminMapper;
import com.bms.bms.mapper.StudentMapper;
import com.bms.bms.mapper.UserMapper;
import com.bms.bms.model.Student;
import com.bms.bms.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class LoginService {
    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private UserMapper userMapper;

    public boolean register(Long id,String password){
            return false;

    }

    //    创建用户
    private void createUser(Student student,String password) {
        User user=null;
        String token= UUID.randomUUID().toString();
        BeanUtils.copyProperties(student,user);//把student的所有属性拷贝到user上面
        user.setGmtCreate(System.currentTimeMillis());
        user.setGmtModified(System.currentTimeMillis());
        user.setToken(token);
        user.setPassword(password);
        user.setStatus(0);
        user.setBorrowCount(0);
        user.setHistory("");

    }

    public User findById(Long id){
        return userMapper.findById(id);
    }
}
