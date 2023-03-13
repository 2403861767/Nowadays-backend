package com.seeleaf.model.vo.user;

import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class UserLoginVo implements Serializable {
    private static final long serialVersionUID = -1794648508439561315L;
    private Long id;
    /**
     * 账号
     */
    private String userAccount;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户头像地址
     */
    private String avatarUrl;

    /**
     * 性别 (0为男1位女)
     */
    private Integer gender;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 用户的权限(0为普通用户，1为管理员)
     */
    private Integer userRole;

    /**
     * 是否删除(0为否，1为是)
     */
    @TableLogic
    private Integer isDelete;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;


}
