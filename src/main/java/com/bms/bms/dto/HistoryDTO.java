package com.bms.bms.dto;

import lombok.Data;

@Data
public class HistoryDTO {
    private Long id;//消息id
    private Long bookId;//所借书id
    private Long bookNumber;//所借书编号
    private String bookName;//所借书名
    private String bookCover;//所借书封面
    private Long gmtCreate;//借阅时间
    private Long gmtModified;//修改（归还）时间
    private String status;//状态（0借阅中，3已归还）
}
