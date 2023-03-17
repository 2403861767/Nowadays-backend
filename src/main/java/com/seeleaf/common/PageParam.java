package com.seeleaf.common;

import lombok.Data;

@Data
public class PageParam {
    private long pageSize = 10;
    private long pageNum = 1;
}
