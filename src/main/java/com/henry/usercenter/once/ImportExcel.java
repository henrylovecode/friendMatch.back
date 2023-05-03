package com.henry.usercenter.once;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.listener.PageReadListener;

import java.io.File;
import java.util.List;

/**
 *
 * FileName:     ImportExcel
 * CreateBy:     IntelliJ IDEA
 * Author:       wei
 * Date:         2023-04-11
 * Description :
 *
 */
public class ImportExcel {

    public static void main(String[] args) {


        // 写法1：JDK8+ ,不用额外写一个DemoDataListener
        // since: 3.0.0-beta1
        String fileName = "D:\\idea-maven-space\\user-center\\src\\main\\resources\\testExcel.xlsx";
        // 这里默认每次会读取100条数据 然后返回过来 直接调用使用数据就行
        // 具体需要返回多少行可以在`PageReadListener`的构造函数设置
        readByListener(fileName);
        synchronousRead(fileName);

    }

    /**
     * 监听器读取
     * @param fileName
     */
    public static void  readByListener(String fileName){
        EasyExcel.read(fileName, XinQinTableUserInfo.class, new TableListener()).sheet().doRead();

    }

    /**
     *
     * 同步读取
     * @param fileName
     */
    public static void synchronousRead(String fileName) {
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 同步读取会自动finish
        List<XinQinTableUserInfo> list = EasyExcel.read(fileName).head(XinQinTableUserInfo.class).sheet().doReadSync();
        for (XinQinTableUserInfo xinQinTableUserInfo : list) {
            System.out.println(xinQinTableUserInfo);
        }
    }
}
