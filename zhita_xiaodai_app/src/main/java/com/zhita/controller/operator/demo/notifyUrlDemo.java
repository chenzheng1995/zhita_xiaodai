package com.zhita.controller.operator.demo;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.zhita.service.manage.operator.OperatorService;

@Controller
@RequestMapping(value="/notifyUrl")
public class notifyUrlDemo {
    /**
     * 回调接口示例
     * @link http://localhost:8080/callback
     * @param request request
     * @return 回调成功信息，默认返回success
     */
	
	@Autowired 
	OperatorService operatorService;
	
    @RequestMapping(value = "/callback", consumes = "multipart/form-data", method = RequestMethod.POST)
    public String callback(MultipartHttpServletRequest request) {
	 String search_id = request.getParameter("search_id");
     String outUniqueId = request.getParameter("outUniqueId");
     String userId = request.getParameter("userId");
     String state = request.getParameter("state");
     String account = request.getParameter("account");
     String accountType = request.getParameter("accountType");
     System.out.println("接收到的参数：\nsearch_id为："
             + search_id + "\noutUniqueId为：" + outUniqueId
             + "\nuserId为：" + userId + "\nstate为：" + state
             + "\naccount为：" + account + "\naccountType为：" + accountType);
     
  	 Map<String, Object> map = new HashMap<String, Object>();
	 int number = operatorService.updateSearch_id(search_id,userId);
     if (number == 1) {                  	
         map.put("msg", "数据插入成功");
         map.put("Code", "200");
     } else {
         map.put("msg", "数据插入失败");
         map.put("Code", "405");
     }
     
     return "success";
}
    
    
    
    @RequestMapping(value = "/moxie", consumes = "multipart/form-data", method = RequestMethod.GET)
    public String moxie(MultipartHttpServletRequest request) {
	 String mxcode = request.getParameter("mxcode");
     String taskId = request.getParameter("taskId");
     String taskType = request.getParameter("taskType");
     String account = request.getParameter("account");
     String userId = request.getParameter("userId");
     String message = request.getParameter("message");
     String loginDone = request.getParameter("loginDone");
     System.out.println("接收到的参数：mxcode为："+ mxcode + "taskId为：" + taskId+ "taskType为：" + taskType + "account为：" + account+ "userId为：" + userId + "message为：" + message+ "loginDone为：" + loginDone);
//     
//  	 Map<String, Object> map = new HashMap<String, Object>();
//	 int number = operatorService.updateSearch_id(search_id,userId);
//     if (number == 1) {                  	
//         map.put("msg", "数据插入成功");
//         map.put("Code", "200");
//     } else {
//         map.put("msg", "数据插入失败");
//         map.put("Code", "405");
//     }
     
     return "success";
}
}