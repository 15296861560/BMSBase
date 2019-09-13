package com.bms.bms.dto;

import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String name;
    private String academy;
    private String cla;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer status;//状态（已借几本书）
    private Integer borrowCount;//借阅次数
    private String history;//借阅历史
    private String phone;

}
