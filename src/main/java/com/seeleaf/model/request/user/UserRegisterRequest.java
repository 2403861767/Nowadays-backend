package com.seeleaf.model.request.user;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserRegisterRequest implements Serializable {

    private static final long serialVersionUID = 5768077297427425113L;

    private String userAccount;
    private String userName;
    private String userPassword;
    private String checkPassword;
    private String email;
    private String avatarUrl;
    private String phone;
    private Integer gender;
}
