package com.seeleaf.service;

import com.seeleaf.model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.seeleaf.model.request.user.UserRegisterRequest;
import com.seeleaf.model.request.user.UserUpdateRequest;
import com.seeleaf.model.vo.user.UserLoginVo;

import javax.servlet.http.HttpServletRequest;

/**
* @author 24038
* @description 针对表【user】的数据库操作Service
* @createDate 2023-02-27 16:42:46
*/
public interface UserService extends IService<User> {
    /***
     *  注册接口
     * @param userRegisterRequest   注册所需的条目
     * @return 新用户的id
     */
    long UserRegister(UserRegisterRequest userRegisterRequest);

    /**
     *  登录接口
     * @param userAccount 用户账号
     * @param userPassword 用户密码
     * @return UserLoginVo
     */
    UserLoginVo UserLogin(String userAccount,String userPassword, HttpServletRequest request);

    /**
     * 登出接口
     * @param request
     * @return 1 表示成功
     */
    int userLogout(HttpServletRequest request);

    /**
     * 拿到当前用户
     * @param request
     * @return
     */
    User  getCurrentUer(HttpServletRequest request);

    /**
     * 是否为管理员
     * @param loginUser 当前登录用户
     * @return 是否是管理员
     */
    Boolean isAdmin(User loginUser);

    /**
     * 更新用户
     * @param updateRequest 更改的数据
     * @param loginUser 当前用户
     * @return 更新的id
     */
    long updateUser(UserUpdateRequest updateRequest,User loginUser);

}
