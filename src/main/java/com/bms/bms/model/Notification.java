package com.bms.bms.model;

import lombok.Data;

@Data
public class Notification {
    private Long id;//消息id
    private Long userId;//借阅人id
    private Long bookId;//所借书id
    private Long gmtCreate;//借阅时间
    private Long gmtModified;//修改（归还）时间
    private Integer status;//状态（0借出，1申请归还，2已归还）
}
