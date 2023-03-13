package com.seeleaf.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.seeleaf.common.ErrorCode;
import com.seeleaf.constant.PlanPriorityEnum;
import com.seeleaf.exception.BusinessException;
import com.seeleaf.model.domain.Plan;
import com.seeleaf.model.domain.User;
import com.seeleaf.model.request.plan.PlanAddRequest;
import com.seeleaf.model.vo.plan.PlanAddVo;
import com.seeleaf.service.PlanService;
import com.seeleaf.mapper.PlanMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

/**
* @author 24038
* @description 针对表【plan】的数据库操作Service实现
* @createDate 2023-03-13 20:30:08
*/
@Service
@Slf4j
public class PlanServiceImpl extends ServiceImpl<PlanMapper, Plan>
    implements PlanService{

    @Override
    public PlanAddVo addPlan(PlanAddRequest planAddRequest, User loginUser) {
        if (loginUser == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN, "未登录!");
        }
        if (planAddRequest == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"参数为空!");
        }
//        4.校验用户是否登录（为了set userId）、planTitle
        String planTitle = planAddRequest.getPlanTitle();
        if (StringUtils.isBlank(planTitle)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"计划标题不能为空!");
        }
        Plan plan = new Plan();
        Long userId = loginUser.getId();
        plan.setUserId(userId);
        plan.setPlanTitle(planTitle);
//        5.校验是否有tagId、description、priority并且添加到数据库中
        String description = planAddRequest.getDescription();
        if (StringUtils.isNotBlank(description)){
            plan.setDescription(description);
        }
        Long tagId = planAddRequest.getTagId();
        if (tagId!=null && tagId >0 ){
            plan.setTagId(tagId);
        }
        // 看优先是否有,默认为0
        Integer priority = Optional.ofNullable(planAddRequest.getPriority()).orElse(0);
        PlanPriorityEnum priorityEnum = PlanPriorityEnum.getEnumByValue(priority);
        if (priorityEnum == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"优先级设置出问题!");
        }
        plan.setPriority(priority);
        boolean result = this.save(plan);
        if (!result) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "新增失败");
        }
        PlanAddVo planAddVo = new PlanAddVo();
        BeanUtils.copyProperties(plan, planAddVo);
        planAddVo.setCreateTime(new Date());
        planAddVo.setStatus(0);
        return planAddVo;
    }
}




