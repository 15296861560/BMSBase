package com.bms.bms.enums;

public enum NotificationStatusEnum {
    LENDING(0,"借阅中"),
    REQUEST_RETURN(1,"申请归还"),
    REQUEST_BORROW(2,"申请借阅"),
    RETUENED(3,"已归还");
    private Integer status;
    private String message;

    NotificationStatusEnum(Integer status, String message) {
        this.status = status;
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

}
