package com.seeleaf.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.seeleaf.common.ErrorCode;
import com.seeleaf.common.PageParam;
import com.seeleaf.constant.PlanPriorityEnum;
import com.seeleaf.exception.BusinessException;
import com.seeleaf.model.domain.Plan;
import com.seeleaf.model.domain.Tag;
import com.seeleaf.model.domain.User;
import com.seeleaf.model.request.plan.PlanAddRequest;
import com.seeleaf.model.vo.plan.PlanAddVo;
import com.seeleaf.model.vo.plan.PlanPageVo;
import com.seeleaf.model.dto.PlanQueryDto;
import com.seeleaf.service.PlanService;
import com.seeleaf.mapper.PlanMapper;
import com.seeleaf.service.TagService;
import com.seeleaf.utils.BeanCopyUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author 24038
 * @description 针对表【plan】的数据库操作Service实现
 * @createDate 2023-03-13 20:30:08
 */
@Service
@Slf4j
public class PlanServiceImpl extends ServiceImpl<PlanMapper, Plan>
        implements PlanService {

    @Resource
    private TagService tagService;

    @Override
    public PlanAddVo addPlan(PlanAddRequest planAddRequest, User loginUser) {
        if (loginUser == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN, "未登录!");
        }
        if (planAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空!");
        }
//        4.校验用户是否登录（为了set userId）、planTitle
        String planTitle = planAddRequest.getPlanTitle();
        if (StringUtils.isBlank(planTitle)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "计划标题不能为空!");
        }
        Plan plan = new Plan();
        Long userId = loginUser.getId();
        plan.setUserId(userId);
        plan.setPlanTitle(planTitle);
//        5.校验是否有tagName、description、priority并且添加到数据库中
        String description = planAddRequest.getDescription();
        if (StringUtils.isNotBlank(description)) {
            plan.setDescription(description);
        }
        String tagName = planAddRequest.getTagName();
        if (StringUtils.isNotBlank(tagName)) {
            LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Tag::getTagName, tagName);
            Tag tag = tagService.getOne(queryWrapper);
            if (tag == null) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "没有该标签");
            }
            plan.setTagName(tagName);
            plan.setTagId(tag.getId());
        }

        // 看优先是否有,默认为0
        Integer priority = Optional.ofNullable(planAddRequest.getPriority()).orElse(0);
        PlanPriorityEnum priorityEnum = PlanPriorityEnum.getEnumByValue(priority);
        if (priorityEnum == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "优先级设置出问题!");
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

    @Override
    public PlanPageVo pageQueryPlan(PageParam pageParam, User loginUser) {
        //       校验用户是否登录
        //       校验参数不为空
        if (loginUser == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN, "未登录!");
        }
        long pageSize = pageParam.getPageSize();
        long pageNum = pageParam.getPageNum();
        //             根据用户id分页查询
        Long userId = loginUser.getId();
        LambdaQueryWrapper<Plan> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Plan::getUserId, userId);
        queryWrapper.orderByDesc(Plan::getCreateTime);
        Page<Plan> planPage = new Page<>(pageNum, pageSize);
        Page<Plan> pageQuery = this.page(planPage, queryWrapper);
        List<Plan> planList = pageQuery.getRecords();
        List<PlanQueryDto> planQueryDtoList = BeanCopyUtils.copyBeanList(planList, PlanQueryDto.class);
        planQueryDtoList = planQueryDtoList.stream().map(plan -> {
            Long tagId = plan.getTagId();
            if (tagId != null && tagId > 0) {
                Tag tag = tagService.getById(tagId);
                if (tag != null) {
                    plan.setTagName(tag.getTagName());
                }
            }
            return plan;
        }).collect(Collectors.toList());
        PlanPageVo planPageVo = new PlanPageVo();
        planPageVo.setPageSize(pageQuery.getSize());
        planPageVo.setPageNum(pageQuery.getCurrent());
        planPageVo.setTotal(pageQuery.getTotal());
        planPageVo.setPlanList(planQueryDtoList);
        return planPageVo;
    }

    @Override
    public boolean finishPlan(int id) {
        Plan plan = getPlanById(id);
        if (plan.getStatus() == 1){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"已经完成该任务");
        }
        plan.setStatus(1);
        boolean update = this.updateById(plan);
        if (!update){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"系统错误");
        }
        return true;
    }

    @Override
    public boolean deletePlan(int id) {
        Plan plan = getPlanById(id);
        boolean remove = this.removeById(plan);
        if (!remove){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"系统错误");
        }
        return true;
    }

    private Plan getPlanById(int id) {
        LambdaQueryWrapper<Plan> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Plan::getId, id);
        Plan plan = this.getOne(queryWrapper);
        if (plan == null) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"系统错误");
        }
        return plan;
    }
}




