package com.zhita.controller.operator.demo;



import java.util.UUID;

import com.zhita.controller.operator.TokenClient;



/**
 * 获取token 调用示例
 * 文档地址: http://zxreport.f3322.net:4999/web/#/22?page_id=88
 */
public class AuthTokenDemo {

    public static void main(String[] args) {

        TokenClient tokenClient = new TokenClient();
        //公共参数
        tokenClient.setAppId("AfFGnpOE");                    //(TODO)替换值，写入机构ID
        tokenClient.setAppSecret("Ku4aEJu9GaOHOyqPZE");      //(TODO)替换值，写入机构密钥
        tokenClient.setOnline(false);                        //(TODO)替换值，默认为线上环境（true），需要使用方替换，也可不替换

        System.out.println("返回token值："+tokenClient.tokenBearer());
    }
}
