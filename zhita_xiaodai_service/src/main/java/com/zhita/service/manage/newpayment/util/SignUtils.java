package com.zhita.service.manage.newpayment.util;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author chenyun
 * @create 2018-09-19 16:57
 */
public class SignUtils {


    public static boolean checkParam(Map<String,String> params,String key){
        boolean result = false;
        if(params.containsKey("sign")){
            String sign = params.get("sign");
            params.remove("sign");
            StringBuilder buf = new StringBuilder((params.size() +1) * 10);
            SignUtils.buildPayParams(buf,params,false);
            String preStr = buf.toString();
            String signRecieve = MD5.sign(preStr, "&key=" + key, "utf-8");
            result = sign.equalsIgnoreCase(signRecieve);
        }
        return result;
    }


    public static String getSign(Map<String,String> params, String key){
        if(params.containsKey("sign")){
            params.remove("sign");
        }
        StringBuilder buf = new StringBuilder((params.size() +1) * 10);
        SignUtils.buildPayParams(buf,params,false);
        String preStr = buf.toString();
        String signRecieve = MD5.sign(preStr, "&key=" + key, "utf-8");
        return signRecieve;

    }

    public static void buildPayParams(StringBuilder sb,Map<String, String> payParams,boolean encoding){
        List<String> keys = new ArrayList<String>(payParams.keySet());
        Collections.sort(keys);
        for(String key : keys){
            sb.append(key).append("=");
            if(encoding){
                sb.append(urlEncode(payParams.get(key)));
            }else{
                sb.append(payParams.get(key));
            }
            sb.append("&");
        }
        sb.setLength(sb.length() - 1);
    }

    public static String urlEncode(String str){
        try {
            return URLEncoder.encode(str, "UTF-8");
        } catch (Throwable e) {
            return str;
        }
    }
}
