package com.seeleaf.service;

import com.seeleaf.model.domain.Plan;
import com.baomidou.mybatisplus.extension.service.IService;
import com.seeleaf.model.domain.User;
import com.seeleaf.model.request.plan.PlanAddRequest;
import com.seeleaf.model.vo.plan.PlanAddVo;

/**
* @author 24038
* @description 针对表【plan】的数据库操作Service
* @createDate 2023-03-13 20:30:08
*/
public interface PlanService extends IService<Plan> {

    /**
     * 新增计划
     * @param planAddRequest
     * @param loginUser
     * @return
     */
    PlanAddVo addPlan(PlanAddRequest planAddRequest, User loginUser);
}
