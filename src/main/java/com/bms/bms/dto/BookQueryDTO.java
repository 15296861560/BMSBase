package com.bms.bms.dto;

import lombok.Data;

@Data
public class BookQueryDTO {
    String attribute;
    String search;
    Integer totalCount;
    Integer offset;
    Integer size;
}
