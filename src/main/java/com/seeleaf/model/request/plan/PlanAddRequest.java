package com.seeleaf.model.request.plan;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class PlanAddRequest implements Serializable {

    private static final long serialVersionUID = 2449304005201271153L;
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
     * 优先级，0表示正常，1表示优先，2表示紧急
     */
    private Integer priority;
}

