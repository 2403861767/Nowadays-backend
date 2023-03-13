package com.seeleaf.controller;

import com.seeleaf.common.BaseResponse;
import com.seeleaf.common.ErrorCode;
import com.seeleaf.common.ResultUtils;
import com.seeleaf.exception.BusinessException;
import com.seeleaf.model.domain.User;
import com.seeleaf.model.request.user.UserLoginRequest;
import com.seeleaf.model.request.user.UserRegisterRequest;
import com.seeleaf.model.request.user.UserUpdateRequest;
import com.seeleaf.model.vo.user.UserLoginVo;
import com.seeleaf.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/user")
@Api("用户相关接口")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation("注册用户接口")
    @PostMapping("/register")
    public BaseResponse<Long> registerUser(@RequestBody UserRegisterRequest userRegisterRequest){
        if (userRegisterRequest == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        long userId = userService.UserRegister(userRegisterRequest);
        return ResultUtils.success(userId);
    }

    @ApiOperation("用户登录接口")
    @PostMapping("/login")
    public BaseResponse<UserLoginVo> LoginUser(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request){
        if (userLoginRequest == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"账号或者密码为空");
        }
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        UserLoginVo userLoginVo = userService.UserLogin(userAccount, userPassword, request);
        return ResultUtils.success(userLoginVo);
    }

    @ApiOperation("用户登出接口")
    @PostMapping("/logout")
    public BaseResponse<Integer> userLogout(HttpServletRequest request){
        if (request== null){
            throw  new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        int result = userService.userLogout(request);
        return ResultUtils.success(result);
    }

    @ApiOperation("获取当前用户接口")
    @GetMapping("/current")
    public BaseResponse<User> getCurrentUser(HttpServletRequest request){
        User currentUser = userService.getCurrentUer(request);
        return ResultUtils.success(currentUser);
    }

    @ApiOperation("用户修改接口")
    @PutMapping("/update")
    public BaseResponse<Long> updateUser(@RequestBody UserUpdateRequest userUpdateRequest,HttpServletRequest request){
        User loginUser = userService.getCurrentUer(request);
        long result = userService.updateUser(userUpdateRequest, loginUser);
        return ResultUtils.success(result);
    }

}
