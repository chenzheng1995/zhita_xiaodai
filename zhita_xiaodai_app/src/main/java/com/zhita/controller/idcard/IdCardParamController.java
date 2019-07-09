package com.zhita.controller.idcard;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

@Controller
@RequestMapping(value="/idcardParam")
public class IdCardParamController {
    /**
    * notify_url 使用 JavaSpring MVC 框架接受post过来的表单参数
    */
    @RequestMapping(method = RequestMethod.POST, value = "/notify")
    @ResponseBody

    public String toNotify(HttpServletRequest request){
        //获取notify过来的json
    	JSONObject jsonObject =null;
        String data = request.getParameter("data");
//        System.out.println(String.format("%s",data));
        String result = String.format("%s",data);
        jsonObject = JSONObject.parseObject(result);
        int result_code =Integer.parseInt(jsonObject.get("result_code").toString());
        if(result_code==1001) {

        }
        return String.format("%s",data);
    }
}