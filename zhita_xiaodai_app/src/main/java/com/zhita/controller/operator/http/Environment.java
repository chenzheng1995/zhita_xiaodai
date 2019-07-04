package com.zhita.controller.operator.http;

public class Environment {

    /** 正式环境 */
    public static final String ONLINE = "http://47.99.100.65:9020" ;
    /** 沙箱环境 */
    public static final String TEST = "http://47.103.98.102:9020";

    public static String url(boolean isOnline,String URI){

        return  new StringBuilder(isOnline?ONLINE:TEST).append(URI).toString();
    }
}
