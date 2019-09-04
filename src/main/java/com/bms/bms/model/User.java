package com.bms.bms.model;

import lombok.Data;

@Data
public class User {
    private Long id;
    private String name;
    private Integer status;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer borrowCount;//借阅次数
    private String academy;
    private String cla;
    private String password;
    private String history;//借阅历史
    private String phone;

}
