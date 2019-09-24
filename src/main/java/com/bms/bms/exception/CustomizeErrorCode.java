package com.bms.bms.exception;

public enum  CustomizeErrorCode {

    REGISTER_FAIL_ID_NOT_FOUND(1001,"注册失败,我们学校没有该学号的学生!"),
    REGISTER_FAIL_ID_REGISTERED(1002,"注册失败，该学号已注册，请直接登录!"),
    LOGIN_FAIL(1003,"登录失败，用户账号或密码错误，请重新尝试!"),
    NO_LOGIN(1004,"您还未登录无法进行该操作，请登录后重试！"),
    BOOK_NOT_FOUND(2001,"您找的书籍不存在，请换一个试试吧!"),
    BOOK_UPLOAD_FAIL(2002,"图书上传失败!"),
    VERIFYCODE_SEND_FAIL(3001,"验证码发送失败，请重新尝试!"),
    VERIFYCODE_VERIFY_FAIL(3002,"验证失败，请重新尝试!"),
    UNKNOWN_ERROR(4001,"未知错误!");

    private String message;
    private Integer code;



    public String getMessage(){
        return message;
    }

    public Integer getCode() {
        return code;
    }


    CustomizeErrorCode(Integer code,String message) {
        this.message = message;
        this.code = code;
    }
}

