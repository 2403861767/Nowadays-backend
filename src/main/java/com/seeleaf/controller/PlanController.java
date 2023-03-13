package com.seeleaf.controller;

import com.seeleaf.common.BaseResponse;
import com.seeleaf.common.ErrorCode;
import com.seeleaf.common.ResultUtils;
import com.seeleaf.exception.BusinessException;
import com.seeleaf.model.domain.User;
import com.seeleaf.model.request.plan.PlanAddRequest;
import com.seeleaf.model.vo.plan.PlanAddVo;
import com.seeleaf.service.PlanService;
import com.seeleaf.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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

}
