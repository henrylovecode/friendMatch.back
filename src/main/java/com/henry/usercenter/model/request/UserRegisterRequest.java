package com.henry.usercenter.model.request;

/**
 * FileName:     UserRegisterRequest
 * CreateBy:     IntelliJ IDEA
 * Author:       wei
 * Date:         2022-09-02
 * Description :
 */



import lombok.Data;

import java.io.Serializable;

/**
 * 用户注册请求体
 */
@Data
public class UserRegisterRequest  implements Serializable{

    private  static  final  long serialVersionUID = 123456755654489L;

    private String userAccount;

    private String userPassword;

    private String checkPassword;

    private String planetCode;




}
