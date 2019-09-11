package com.zhita.controller.about_us;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.zhita.dao.manage.FeedbackMapper;
import com.zhita.service.manage.aboutus.IntAboutusService;
import com.zhita.util.Base64ToInputStream;
import com.zhita.util.OssUtil;

@Controller
@RequestMapping("/aboutus")
public class AboutUsController {

	    
	    @Autowired
	    IntAboutusService intAboutusService;
	    
	    @Autowired
	    FeedbackMapper feedbackMapper;
		
	    
	    //关于我们
	    @RequestMapping("/getaboutus")
	    @ResponseBody
	    @Transactional
	    public Map<String, Object> getaboutus(int companyId){
	    	Map<String, Object> map =intAboutusService.getaboutus(companyId);
	    	map.put("Ncode","2000");
	    	map.put("iOS","http://dhjios.rong51dai.com");
	    	map.put("Android","https://htmlk.oss-cn-qingdao.aliyuncs.com/xiaodai/apk/daihuijia.apk");
			return map;

	    }
	    
	    
	    
	    //意见反馈
	    @RequestMapping("/setfeedback")
	    @ResponseBody
	    @Transactional
	    public Map<String, Object> setfeedback(Integer userId,String[] base64,String advice) throws Exception{
	    	Map<String, Object> map = new HashMap<String, Object>();
	    	String url1 ="";
	    	String url2 ="";
	    	String url3 ="";
        	String time = System.currentTimeMillis()+"";
            for(int i=0;i<base64.length;i++) {
    		    Base64ToInputStream base64ToInputStream = new Base64ToInputStream();
    		    InputStream stream = base64ToInputStream.BaseToInputStream(base64[i]);
    		    System.out.println(base64[i]);
    		    String path1 = "xiaodai_feedback/"+userId+time+"image1"+".jpg";
    		    String path2 = "xiaodai_feedback/"+userId+time+"image2"+".jpg";
    		    String path3 = "xiaodai_feedback/"+userId+time+"image3"+".jpg";
    			OssUtil ossUtil = new OssUtil();
    			if(i==0) {
    				url1 = ossUtil.uploadFile(stream, path1);
    			}
    			if(i==1) {
    				url2 = ossUtil.uploadFile(stream, path2);
    			}
    			if(i==2) {
    				url3 = ossUtil.uploadFile(stream, path3);
    			}
            }

            int num = feedbackMapper.setfeedback(userId,advice,url1,url2,url3,time);
            if(num==1) {
            	map.put("Ncode","2000");
            	map.put("code","200");
            	map.put("msg","数据插入成功");
            }
            if(num==0) {
            	map.put("Ncode","405");
            	map.put("code","405");
            	map.put("msg","数据插入失败");
            }
            
			return map;

	    }
	
	    
//	    //意见反馈
//	    @RequestMapping("/setfeedback")
//	    @ResponseBody
//	    @Transactional
//	    public Map<String, Object> setfeedback(int userId,String[] base64,String advice) throws Exception{
//	    	Map<String, Object> map = new HashMap<String, Object>();
//	    	String url1 ="";
//	    	String url2 ="";
//	    	String url3 ="";
//        	String time = System.currentTimeMillis()+"";
//            for(int i=0;i<base64.length;i++) {
//    		    Base64ToInputStream base64ToInputStream = new Base64ToInputStream();
//    		    InputStream stream = base64ToInputStream.BaseToInputStream(base64[i]);
//    		    String path1 = "xiaodai_feedback/"+userId+time+"image1"+".jpg";
//    		    String path2 = "xiaodai_feedback/"+userId+time+"image2"+".jpg";
//    		    String path3 = "xiaodai_feedback/"+userId+time+"image3"+".jpg";
//    			OssUtil ossUtil = new OssUtil();
//    			if(i==0) {
//    				url1 = ossUtil.uploadFile(stream, path1);
//    			}
//    			if(i==1) {
//    				url2 = ossUtil.uploadFile(stream, path2);
//    			}
//    			if(i==2) {
//    				url3 = ossUtil.uploadFile(stream, path3);
//    			}
//            }
//
//            int num = feedbackMapper.setfeedback(userId,advice,url1,url2,url3,time);
//            if(num==1) {
//            	map.put("Ncode","2000");
//            	map.put("code","200");
//            	map.put("msg","数据插入成功");
//            }
//            if(num==0) {
//            	map.put("Ncode","405");
//            	map.put("code","405");
//            	map.put("msg","数据插入失败");
//            }
//            
//			return map;
//
//	    }
}
