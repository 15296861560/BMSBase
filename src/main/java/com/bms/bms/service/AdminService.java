package com.bms.bms.service;

import com.bms.bms.mapper.AdminMapper;
import com.bms.bms.mapper.UserMapper;
import com.bms.bms.model.Admin;
import com.bms.bms.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private UserMapper userMapper;

    public boolean confirm(Long id,String password){
        Admin admin=adminMapper.findById(id);
        if (admin==null)return false;
        if (admin.getPassword().equals(password))return true;
        else return false;
    }


    public Admin findById(Long id){
        return adminMapper.findById(id);
    }
}
