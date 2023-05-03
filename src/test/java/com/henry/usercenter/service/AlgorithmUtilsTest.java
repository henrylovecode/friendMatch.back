package com.henry.usercenter.service;

import com.henry.usercenter.utils.AlgorithmUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * FileName:     AlgorithmUtils
 * CreateBy:     IntelliJ IDEA
 * Author:       wei
 * Date:         2023-04-25
 * Description :
 */
@SpringBootTest
public class AlgorithmUtilsTest {

    @Test
    void test(){
        String str1 = "坤坤是鸡";
        String str2 = "坤坤不是鸡";
        String str3 = "坤坤是狗不是鸡";
        int score1 = AlgorithmUtils.minDistance(str1, str2);
        int score2 = AlgorithmUtils.minDistance(str1, str3);
        System.out.println(score1);
        System.out.println(score2);


    }
}
