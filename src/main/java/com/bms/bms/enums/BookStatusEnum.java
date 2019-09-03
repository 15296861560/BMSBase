package com.bms.bms.enums;

public enum BookStatusEnum {
    GOOD(1,"完好"),
    LENDING(2,"借出"),
    LOST(3,"丢失"),
    BAD(4,"损坏");
    private Integer status;
    private String message;

    BookStatusEnum(Integer status, String message) {
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
