package com.seeleaf.controller;

import com.seeleaf.common.BaseResponse;
import com.seeleaf.common.ErrorCode;
import com.seeleaf.common.PageParam;
import com.seeleaf.common.ResultUtils;
import com.seeleaf.exception.BusinessException;
import com.seeleaf.model.domain.User;
import com.seeleaf.model.request.plan.PlanAddRequest;
import com.seeleaf.model.vo.plan.PlanAddVo;
import com.seeleaf.model.vo.plan.PlanPageVo;
import com.seeleaf.service.PlanService;
import com.seeleaf.service.TagService;
import com.seeleaf.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/plan")
@Api("计划相关接口")
public class PlanController {
    @Resource
    private UserService userService;
    @Resource
    private PlanService planService;



    @ApiOperation("添加计划接口")
    @PostMapping("/add")
    public BaseResponse<PlanAddVo> addPlan(@RequestBody PlanAddRequest planAddRequest, HttpServletRequest request) {
        if (planAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        User loginUser = userService.getCurrentUer(request);
        PlanAddVo planAddVo = planService.addPlan(planAddRequest, loginUser);
        return ResultUtils.success(planAddVo);
    }

    @ApiOperation("分页查询计划接口")
    @PostMapping("/page")
    public BaseResponse<PlanPageVo> pagePlan(@RequestBody PageParam pageParam, HttpServletRequest request){
        if (pageParam == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"参数为空");
        }
        User loginUser = userService.getCurrentUer(request);
        PlanPageVo planPage =planService.pageQueryPlan(pageParam,loginUser);
        return ResultUtils.success(planPage);
    }

}
