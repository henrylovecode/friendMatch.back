package com.henry.usercenter.model.request;

import lombok.Data;

import java.io.Serializable;

/**
 * FileName:     PageRequse
 * CreateBy:     IntelliJ IDEA
 * Author:       wei
 * Date:         2023-04-20
 * Description :
 */
@Data
public class PageRequest implements Serializable {



    private static  final  long   serialVersionUID = -515155161616161L;
    /**
     * 页面大小
     */
    protected int pageSize = 10;

    /**
     * 当前第几页
     */
    protected int pageNum = 1;


}
