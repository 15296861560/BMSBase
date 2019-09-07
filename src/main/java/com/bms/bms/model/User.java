package com.bms.bms.model;

import lombok.Data;

@Data
public class User {
    private Long id;
    private String name;
    private String academy;
    private String cla;
    private Long gmtCreate;
    private Long gmtModified;
    private String token;//身份标识
    private Integer status;//状态（已借几本书）
    private Integer borrowCount;//借阅次数
    private String password;
    private String history;//借阅历史
    private String phone;

}
