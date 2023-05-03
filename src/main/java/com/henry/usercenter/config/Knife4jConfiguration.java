package com.henry.usercenter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;


import java.util.ArrayList;

/**
 * FileName:     SwaggerConfig
 * CreateBy:     IntelliJ IDEA
 * Author:       wei
 * Date:         2023-04-11
 * Description :
 */
@Configuration //配置类
@EnableSwagger2WebMvc
public class Knife4jConfiguration {


    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                //通过.select()方法，去配置扫描接口
                .select()
                //RequestHandlerSelectors配置如何扫描接口
                .apis(RequestHandlerSelectors.basePackage("com.henry.usercenter.controller"))
                // 配置如何通过path过滤,即这里只扫描请求以/koko开头的接口
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * api信息
     * @return
     */
    private ApiInfo apiInfo() {
        Contact contact = new Contact("koko", "http://xxx.xxx.com/联系人访问链接", "联系人邮箱");
        return new ApiInfo(
                "Henry用户中心", // 标题
                "Henry用户中心接口文档", // 描述
                "v1.0", // 版本
                "https://github.com/13396022881wei", // 组织链接
                contact, // 联系人信息
                "Apach 2.0 许可", // 许可
                "许可链接", // 许可连接
                new ArrayList<>()// 扩展
        );
    }




}
