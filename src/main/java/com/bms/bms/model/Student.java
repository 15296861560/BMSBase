package com.bms.bms.model;

import lombok.Data;

@Data
public class Student {
    private Long id;//学号
    private String name;//姓名
    private String academy;//学院
    private String cla;//班级
    private Long gmtCreate;
    private Long gmtModified;
}
