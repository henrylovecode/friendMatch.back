package com.henry.usercenter.once;

import com.alibaba.excel.EasyExcel;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * FileName:     ImportXingQiuUser
 * CreateBy:     IntelliJ IDEA
 * Author:       wei
 * Date:         2023-04-12
 * Description :
 */
public class ImportXingQiuUser {
    public static void main(String[] args) {
        String fileName = "D:\\idea-maven-space\\user-center\\src\\main\\resources\\积分榜.xlsx";
        // 这里默认每次会读取100条数据 然后返回过来 直接调用使用数据就行
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 同步读取会自动finish
        List<XinQinTableUserInfo> userInfoList = EasyExcel.read(fileName).head(XinQinTableUserInfo.class).sheet().doReadSync();
        System.out.println("总数：" + userInfoList.size());
        Map<String, List<XinQinTableUserInfo>> listMap = userInfoList.stream()
                .filter(userInfo -> StringUtils.isNotEmpty(userInfo.getUsername()))
                .collect(Collectors.groupingBy(XinQinTableUserInfo::getUsername));

        for (Map.Entry<String , List<XinQinTableUserInfo>> stringListEntry : listMap.entrySet()) {
            if (stringListEntry.getValue().size() >  1){
                System.out.println("username = " +  stringListEntry.getKey());
                System.out.println("1");
            }
        }
        System.out.println("不能重复的昵称 = " + listMap.keySet().size() );


    }
}
