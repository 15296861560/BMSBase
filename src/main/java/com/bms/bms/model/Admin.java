package com.bms.bms.model;

import lombok.Data;

@Data
public class Admin {
    private Long id;
    private String password;
    private String name;
    private Integer level;
    private Long gmtCreate;
    private Long gmtModified;
}
