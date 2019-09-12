package com.bms.bms.model;

import lombok.Data;

@Data
public class Book {
    private Long id;//每本书唯一编号
    private Long number;//名字相同时用number来区别是不是“同一本”书
    private String name;//书名
    private String type;// 类型
    private String status;//状态(完好、借出、丢失、损坏）
    private Long gmtCreate;//入库时间
    private Long gmtModified;//修改时间
    private Integer lendCount;//借出次数
    private String cover;//封面
    private String brief;//简介
}
