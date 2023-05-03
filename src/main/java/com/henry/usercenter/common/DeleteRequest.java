package com.henry.usercenter.common;

/**
 * FileName:     DeleteRequest
 * CreateBy:     IntelliJ IDEA
 * Author:       wei
 * Date:         2023-04-25
 * Description :
 */

import lombok.Data;

import java.io.Serializable;

/**
 * 通用删除请求
 *
 * @author yupi
 */
@Data
public class DeleteRequest implements Serializable {

    private static final long serialVersionUID = -5860707094194210842L;

    private long id;
}
