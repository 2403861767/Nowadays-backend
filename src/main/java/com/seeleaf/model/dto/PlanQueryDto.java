package com.seeleaf.model.dto;

import lombok.Data;

import java.util.Date;

@Data
public class PlanQueryDto {
    // id、planTitle、description、priority、tagName、status、createTime、total、paseSize、pageNum
    private Long id;

    /**
     * 所属的标签
     */
    private Long tagId;

    /**
     * 计划标题
     */
    private String planTitle;

    /**
     * 描述
     */
    private String description;

    /**
     * 计划此时的状态，0表示未完成，1表示已经完成该计划
     */
    private Integer status;

    /**
     * 优先级，0表示正常，1表示优先，2表示紧急
     */
    private Integer priority;

    /**
     * 创建人id
     */
    private Long userId;

    /**
     * 创建时间
     */
    private Date createTime;


    private String tagName;


}
