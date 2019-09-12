package com.bms.bms.enums;

public enum BookTypeEnum {
    A("A","马列主义毛邓思想"),
    B("B","哲学"),
    C("C","社会科学总论"),
    D("D","政治、法律"),
    E("E","军事"),
    F("F","经济"),
    G("G","文化、科学、教育、体育"),
    H("H","语言、文字"),
    I("I","文学"),
    J("J","艺术"),
    K("K","历史、地理"),
    N("N","自然科学总论"),
    O("O","数理科学和化学"),
    P("P","天文学、地球科学"),
    Q("Q","生物科学"),
    R("R","医药、卫生"),
    S("S","农业科学"),
    T("T","工业技术"),
    U("U","交通运输"),
    V("V","航空、航天"),
    X("X","环境科学、安全科学"),
    Z("Z","综合性图书");


    private String type;
    private String message;

    BookTypeEnum(String type, String message) {
        this.type = type;
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }
}
