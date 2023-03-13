package com.seeleaf.model.request.user;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserUpdateRequest implements Serializable {
    private static final long serialVersionUID = -1494013367752287271L;
    private long id;
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
}
