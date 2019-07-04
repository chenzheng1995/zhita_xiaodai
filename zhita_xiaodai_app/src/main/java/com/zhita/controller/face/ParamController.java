package com.zhita.controller.face;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

public class ParamController {
    /**
    * notify_url 使用 JavaSpring MVC 框架接受post过来的表单参数
    */
    @RequestMapping(method = RequestMethod.POST, value = "/notify")
    @ResponseBody

    public String toNotify(HttpServletRequest request){
        //获取notify过来的json
        String data = request.getParameter("data");
        System.out.println(String.format("%s",data));
        return String.format("%s",data);
    }
}