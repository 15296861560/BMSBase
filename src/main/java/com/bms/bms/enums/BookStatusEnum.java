package com.bms.bms.enums;

public enum BookStatusEnum {
    GOOD("GOOD","完好"),
    LENDING("LENDING","借出"),
    LOST("LOST","丢失"),
    BAD("BAD","损坏"),
    APPLY_BORROW("APPLY_BORROW","申请借阅中"),
    APPLY_RETURN("APPLY_RETURN","申请归还中");
    private String status;
    private String message;

    BookStatusEnum(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
