package com.henry.usercenter.model.request;

import lombok.Data;

import java.util.Date;

/**
 * FileName:     TeamUpdateRequest
 * CreateBy:     IntelliJ IDEA
 * Author:       wei
 * Date:         2023-04-22
 * Description :
 */
@Data
public class TeamUpdateRequest {

    private static final long serialVersionUID = 3191241716373120793L;

    /**
     * id
     */
    private Long id;

    /**
     * 队伍名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 过期时间
     */
    private Date expireTime;

    /**
     * 0 - 公开，1 - 私有，2 - 加密
     */
    private Integer status;

    /**
     * 密码
     */
    private String password;

}
