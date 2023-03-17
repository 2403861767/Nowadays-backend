package com.seeleaf.model.vo.plan;

import com.seeleaf.model.dto.PlanQueryDto;
import lombok.Data;

import java.util.List;

@Data
public class PlanPageVo {
    private List<PlanQueryDto> planList;
    private long pageNum;
    private long pageSize;
    private long total;
}
