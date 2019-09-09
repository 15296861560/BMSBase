package com.bms.bms.dto;

import com.bms.bms.model.Book;
import com.bms.bms.model.User;
import lombok.Data;

@Data
public class NotificationDTO {
    private Long id;
    private User user;//借阅人
    private Book book;//所借书
    private Long gmtCreate;//借阅时间
    private String status;//状态（申请借阅，申请归还）
}
