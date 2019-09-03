package com.bms.bms.enums;

public enum BookTypeEnum {
    A(1,"马列主义毛邓思想"),
    B(2,"哲学"),
    C(3,"社会科学总论"),
    D(4,"政治、法律"),
    E(5,"军事"),
    F(6,"经济"),
    G(7,"文化、科学、教育、体育"),
    H(8,"语言、文字"),
    I(9,"文学"),
    J(10,"艺术"),
    K(11,"历史、地理"),
    N(12,"自然科学总论"),
    O(13,"数理科学和化学"),
    P(14,"天文学、地球科学"),
    Q(15,"生物科学"),
    R(16,"医药、卫生"),
    S(17,"农业科学"),
    T(18,"工业技术"),
    U(19,"交通运输"),
    V(20,"航空、航天"),
    X(21,"环境科学、安全科学"),
    Z(22," 综合性图书");


    private Integer type;
    private String message;

    BookTypeEnum(Integer type, String message) {
        this.type = type;
        this.message = message;
    }

    public Integer getType(String message) {
        return type;
    }

    public String getMessage() {
        return message;
    }
}
