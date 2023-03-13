package com.seeleaf.model.request.user;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserLoginRequest implements Serializable {
    private static final long serialVersionUID = -1780552098263679820L;
    private String userAccount;
    private String userPassword;
}
