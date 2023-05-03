package com.henry.usercenter.model.request;

/**
 * FileName:     TeamJoinRequest
 * CreateBy:     IntelliJ IDEA
 * Author:       wei
 * Date:         2023-04-22
 * Description :
 */

import lombok.Data;

import java.io.Serializable;

/**
 * 用户加入队伍请求体
 *
 * @author yupi
 */
@Data
public class TeamJoinRequest implements Serializable {

    private static final long serialVersionUID = 3191241716373120793L;

    /**
     * id
     */
    private Long teamId;

    /**
     * 密码
     */
    private String password;
}
