package com.zhita.controller.operator.demo;



import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import com.zhita.controller.operator.TokenClient;




/**
 * 获取token 调用示例
 * 文档地址: http://zxreport.f3322.net:4999/web/#/22?page_id=88
 */
public class AuthTokenDemo {

	 public String toNotify(){

        TokenClient tokenClient = new TokenClient();
        //公共参数
//        tokenClient.setAppId("AfFGnpOE");                    //(TODO)替换值，写入机构ID
//        tokenClient.setAppSecret("Ku4aEJu9GaOHOyqPZE");      //(TODO)替换值，写入机构密钥
        tokenClient.setAppId("app190812164735176026");                    //(TODO)替换值，写入机构ID
        tokenClient.setAppSecret("QKP43WQG8U261HHRLYX9IINB7VUUV5LT");      //(TODO)替换值，写入机构密钥        
        tokenClient.setOnline(true);                        //(TODO)替换值，默认为线上环境（true），需要使用方替换，也可不替换
        
		return tokenClient.tokenBearer();
    }
}
