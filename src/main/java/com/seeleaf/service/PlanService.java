package com.seeleaf.service;

import com.seeleaf.common.PageParam;
import com.seeleaf.model.domain.Plan;
import com.baomidou.mybatisplus.extension.service.IService;
import com.seeleaf.model.domain.User;
import com.seeleaf.model.request.plan.PlanAddRequest;
import com.seeleaf.model.vo.plan.PlanAddVo;
import com.seeleaf.model.vo.plan.PlanPageVo;

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

    /**
     * 分页查询计划
     * @param pageParam
     * @param loginUser
     * @return
     */
    PlanPageVo pageQueryPlan(PageParam pageParam, User loginUser);

    /**
     * 完成计划
     * @param id
     * @return
     */
    boolean finishPlan(int id);

    /**
     * 删除计划
     * @param id
     * @return
     */
    boolean deletePlan(int id);
}
