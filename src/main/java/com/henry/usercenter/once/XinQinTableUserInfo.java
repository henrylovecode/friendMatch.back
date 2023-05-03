package com.henry.usercenter.once;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class XinQinTableUserInfo {

    /**
     * id

     */
    @ExcelProperty("成员编号")
    private Long id;

    /**
     * 用户昵称
     */
    @ExcelProperty("成员昵称")
    private String username;
}