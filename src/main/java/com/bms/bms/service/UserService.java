package com.bms.bms.service;

import com.bms.bms.exception.CustomizeErrorCode;
import com.bms.bms.mapper.StudentMapper;
import com.bms.bms.mapper.UserMapper;
import com.bms.bms.model.Student;
import com.bms.bms.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private UserMapper userMapper;

    public String register(Long id,String password){
        //检查是否已注册
        User user=userMapper.findById(id);
        if (user==null) {
//        检查学校是否有该学生存在
            Student student = studentMapper.findById(id);
            if (student != null) {
                createUser(student, password);
                return "success";
            }
            else {
//                该学号不存在
                return CustomizeErrorCode.REGISTER_FAIL_ID_NOT_FOUND.getMessage();
            }
        }else {
//            已注册过了
            return CustomizeErrorCode.REGISTER_FAIL_ID_REGISTERED.getMessage();
        }

    }

    public boolean confirm(Long id,String password){
        User user=userMapper.findById(id);
        if (user==null)return false;
        if (user.getPassword().equals(password))return true;
        else return false;
    }


    //    创建用户
    private void createUser(Student student,String password) {
        User user=new User();
        String token= UUID.randomUUID().toString();
        BeanUtils.copyProperties(student,user);//把student的所有属性拷贝到user上面
        user.setGmtCreate(System.currentTimeMillis());
        user.setGmtModified(System.currentTimeMillis());
        user.setToken(token);
        user.setPassword(password);
        user.setStatus(0);
        user.setBorrowCount(0);
        user.setHistory("");
        userMapper.createUser(user);

    }

    public User findById(Long id){
        return userMapper.findById(id);
    }
}
