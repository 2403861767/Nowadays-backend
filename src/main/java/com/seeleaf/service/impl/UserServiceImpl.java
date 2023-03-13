package com.seeleaf.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.seeleaf.common.ErrorCode;
import com.seeleaf.constant.UserConstant;
import com.seeleaf.exception.BusinessException;
import com.seeleaf.model.domain.User;
import com.seeleaf.model.request.user.UserRegisterRequest;
import com.seeleaf.model.request.user.UserUpdateRequest;
import com.seeleaf.model.vo.user.UserLoginVo;
import com.seeleaf.service.UserService;
import com.seeleaf.mapper.UserMapper;

import com.seeleaf.utils.BeanCopyUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.seeleaf.constant.UserConstant.USER_LOGIN_STATE;

/**
 * @author 24038
 * @description 针对表【user】的数据库操作Service实现
 * @createDate 2023-02-27 16:42:46
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

    @Resource
    private UserMapper userMapper;

    /**
     * 盐值，用于混淆密码
     */
    private static final String SALT = "seeleaf";

    // 注册
    @Override
    public long UserRegister(UserRegisterRequest userRegisterRequest) {
        String userName = userRegisterRequest.getUserName();
        String userAccount = userRegisterRequest.getUserAccount();
        String checkPassword = userRegisterRequest.getCheckPassword();
        String userPassword = userRegisterRequest.getUserPassword();
        String email = userRegisterRequest.getEmail();
        String avatarUrl = userRegisterRequest.getAvatarUrl();
        String phone = userRegisterRequest.getPhone();
        Integer gender = userRegisterRequest.getGender();

        // 1. 校验
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword, userName, phone, email)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        if (userAccount.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户账号过短");
        }
        if (userPassword.length() < 8 || checkPassword.length() < 8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户密码过短");
        }
        if (gender == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 账户不能包含特殊字符
        String validPattern = "[`~!@#$%^&*()+=|{}':;',//[//].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        if (matcher.find()) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 密码和校验密码相同
        if (!userPassword.equals(checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "两次密码不一致！");
        }

        // 账户不能重复
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        long count = userMapper.selectCount(queryWrapper);
        if (count > 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号重复");
        }

        // 对密码进行加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());

        // 插入数据库中
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(encryptPassword);
        user.setUserName(userName);
        user.setGender(gender);
        user.setEmail(email);
        user.setPhone(phone);
        if (StringUtils.isNotBlank(avatarUrl)) {
            user.setAvatarUrl(avatarUrl);
        }
        boolean save = this.save(user);
        if (!save) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }

        return user.getId();
    }

    // 登录
    @Override
    public UserLoginVo UserLogin(String userAccount, String userPassword, HttpServletRequest request) {
        // 校验
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账户名或者密码为空");
        }
        if (userAccount.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账户名小于4位");
        }
        if (userPassword.length() < 8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "密码小于8位");
        }
        // 账户不能包含特殊字符
        String validPattern = "[`~!@#$%^&*()+=|{}':;',//[//].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        if (matcher.find()) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "非法的账户名");
        }
        // 2. 加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        // 查询用户是否存在
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        queryWrapper.eq("userPassword", encryptPassword);
        User user = userMapper.selectOne(queryWrapper);
        // 用户不存在
        if (user == null) {
            log.info("user login failed, userAccount cannot match userPassword");
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号或者密码错误");
        }
        // 封装返回类
        UserLoginVo userLoginVo = BeanCopyUtils.copyBean(user, UserLoginVo.class);
        request.getSession().setAttribute(USER_LOGIN_STATE, userLoginVo);
        return userLoginVo;
    }

    // 登出
    @Override
    public int userLogout(HttpServletRequest request) {
        request.getSession().removeAttribute(USER_LOGIN_STATE);
        return 1;
    }

    // 拿到当前用户
    @Override
    public User getCurrentUer(HttpServletRequest request) {
        UserLoginVo userObj = (UserLoginVo) request.getSession().getAttribute(USER_LOGIN_STATE);
//        UserLoginVo currentUser = (UserLoginVo) userObj;
        if (userObj == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN,"未登录!");
        }
        Long id = userObj.getId();
        // 确保每次的用户都是最新的状态
        User currentUser = userMapper.selectById(id);
        if (currentUser == null) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"系统内部出错");
        }
        UserLoginVo currentUserVo = new UserLoginVo();
        User loginUser = null;
        try {
            BeanUtils.copyProperties(currentUser, currentUserVo);
            loginUser = new User();
            BeanUtils.copyProperties(currentUserVo, loginUser);
        } catch (Exception e) {
            log.info("拿到当前用户出错" + e);
        }
        return loginUser;
    }

    @Override
    public Boolean isAdmin(User loginUser) {
        Integer userRole = loginUser.getUserRole();
        return Objects.equals(userRole, UserConstant.USER_ADMIN);
    }

    @Override
    public long updateUser(UserUpdateRequest updateRequest, User loginUser) {
        long updateUserId = updateRequest.getId();
        if (updateUserId <= 0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"参数错误!");
        }
        // 只要管理员和自己能更改
        if (!isAdmin(loginUser) && (updateUserId!=loginUser.getId())){
            throw new BusinessException(ErrorCode.NO_AUTH, "无权限!");
        }
        // 允许更新

        // 如果是管理员要更改用户,那么要看这个用户是否存在
        User oldUser = userMapper.selectById(updateUserId);
        if (oldUser == null) {
            throw new BusinessException(ErrorCode.NULL_ERROR,"没有该用户!");
        }
        User user = new User();
        BeanUtils.copyProperties(updateRequest,user);
        return userMapper.updateById(user) ;
    }
}




