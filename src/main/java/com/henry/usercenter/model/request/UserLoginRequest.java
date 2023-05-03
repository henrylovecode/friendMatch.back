package com.henry.usercenter.model.request;

import lombok.Data;

import java.io.Serializable;

/**
 * FileName:     UserLoginRequest
 * CreateBy:     IntelliJ IDEA
 * Author:       wei
 * Date:         2022-09-02
 * Description :
 */
@Data
public class UserLoginRequest implements Serializable {

    private  static  final  long serialVersionUID = 123456755654489L;

    private String userAccount;

    private String userPassword;

}
