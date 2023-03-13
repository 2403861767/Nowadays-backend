package com.seeleaf.model.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName plan
 */
@TableName(value ="plan")
@Data
public class Plan implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
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
     * 是否删除(0为否，1为是)
     */
    @TableLogic
    private Integer isDelete;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}