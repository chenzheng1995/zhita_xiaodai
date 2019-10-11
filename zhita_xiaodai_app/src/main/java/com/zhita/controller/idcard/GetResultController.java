package com.zhita.controller.idcard;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.zhita.controller.face.postDemo;
import com.zhita.dao.manage.ThirdcalltongjiMapper;
import com.zhita.service.manage.user.IntUserService;
import com.zhita.service.manage.userattestation.UserAttestationService;
import com.zhita.util.Base64ToInputStream;
import com.zhita.util.FolderUtil;
import com.zhita.util.OssUtil;
import com.zhita.util.PostAndGet;

@Controller
@RequestMapping(value="/result")
public class GetResultController {
    @Autowired
    UserAttestationService UserAttestationService;
    
    @Autowired
    IntUserService intUserService;
    
	@Autowired
	ThirdcalltongjiMapper thirdcalltongjiMapper;
	
//    @RequestMapping("/getresult")
//    @ResponseBody
//    @Transactional
//    public Map<String, Object> getresult(int userId) throws Exception{
//    	Map<String, Object> map = new HashMap<String, Object>();
//    	Map<String, Object> userAttestation = UserAttestationService.getuserAttestation(userId); 
//    	JSONObject jsonObject =null;
//    	JSONObject jsonObject1 =null;
//    	String sign = (String) userAttestation.get("sign");
//      	sign = URLEncoder.encode(sign, "UTF-8");
//    	String biz_token = (String) userAttestation.get("idCardBizToken");
////    	String param = "{'sign':'"+sign+"','sign_version':'hmac_sha1','userId':'"+userId+"','biz_token':'"+biz_token+"','need_image':1}";
//    	PostAndGet postAndGet = new PostAndGet();
//    	String result = postAndGet.sendGet("https://openapi.faceid.com/lite_ocr/v1/get_result?sign="+sign+"&sign_version=hmac_sha1&biz_token="+biz_token+"&need_image=1");
//    	   jsonObject = JSONObject.parseObject(result);
//    	   try {
//    		   int result_code =Integer.parseInt(jsonObject.get("result_code").toString());
//               String idcard_number =  jsonObject.get("idcard_number").toString();
//               jsonObject1 = JSONObject.parseObject(idcard_number);
//               idcard_number = jsonObject1.getString("result");
//               String image_frontside = jsonObject.get("image_frontside").toString();
//               String image_backside = jsonObject.get("image_backside").toString();
//               String name = jsonObject.get("name").toString();
//               jsonObject1 = JSONObject.parseObject(name);
//               name = jsonObject1.getString("result");
//               String gender = jsonObject.get("gender").toString();
//               jsonObject1 = JSONObject.parseObject(gender);
//               gender = jsonObject1.getString("result");
//               String nationality = jsonObject.get("nationality").toString();
//               jsonObject1 = JSONObject.parseObject(nationality);
//               nationality = jsonObject1.getString("result");
//               String birth_year = jsonObject.get("birth_year").toString();
//               jsonObject1 = JSONObject.parseObject(birth_year);
//               birth_year = jsonObject1.getString("result");
//               String birth_month = jsonObject.get("birth_month").toString();
//               jsonObject1 = JSONObject.parseObject(birth_month);
//               birth_month = jsonObject1.getString("result");
//               String birth_day = jsonObject.get("birth_day").toString();
//               jsonObject1 = JSONObject.parseObject(birth_day);
//               birth_day = jsonObject1.getString("result");
//               String address = jsonObject.get("address").toString();
//               jsonObject1 = JSONObject.parseObject(address);
//               address = jsonObject1.getString("result");
//               String issued_by = jsonObject.get("issued_by").toString();
//               jsonObject1 = JSONObject.parseObject(issued_by);
//               issued_by = jsonObject1.getString("result");
//               String valid_date_start = jsonObject.get("valid_date_start").toString();
//               jsonObject1 = JSONObject.parseObject(valid_date_start);
//               valid_date_start = jsonObject1.getString("result");
//               String valid_date_end = jsonObject.get("valid_date_end").toString();
//               jsonObject1 = JSONObject.parseObject(valid_date_end);
//               valid_date_end = jsonObject1.getString("result");
//               
//               if(result_code==1001) {
//            	    
//            	    Base64ToInputStream base64ToInputStream = new Base64ToInputStream();
//            	    InputStream stream = base64ToInputStream.BaseToInputStream(image_frontside);
//            	    InputStream stream1 = base64ToInputStream.BaseToInputStream(image_backside);
//    				String path = "xiaodai_idcard/"+idcard_number+"zhengmian"+".jpg";
//    				String path1 = "xiaodai_idcard/"+idcard_number+"beimian"+".jpg";
//    				OssUtil ossUtil = new OssUtil();
//    				String frontsidePath = ossUtil.uploadFile(stream, path);
//    				String backsidePath = ossUtil.uploadFile(stream1, path1);
//                    int number = UserAttestationService.updateUserAttestation(name, gender, nationality, birth_year, birth_month, birth_day, address,issued_by,valid_date_start,valid_date_end,frontsidePath,backsidePath,userId,idcard_number);
//                    if (number == 1) {                  	
//                        map.put("msg", "数据插入成功");
//                        map.put("Code", "200");
//                        map.put("name", name);
//                        map.put("idcard_number", idcard_number);
//                    } else {
//                        map.put("msg", "数据插入失败");
//                        map.put("Code", "405");
//                    }
//   			
//               }
//		} catch (Exception e) {
//			return map;
//		}
//          
//		return map;
//    	
//    }
    
    
    
//    //个人信息模块的基础信息
//    @RequestMapping("/setidcard")
//    @ResponseBody
//    @Transactional
//    public Map<String, Object> setidcard(int userId,String name,String gender,String nationality,String birth_year,String birth_month,String birth_day,String address,String issued_by,
//    		String valid_date_start,String valid_date_end,String idcard_number,String homeAddressLongitude,String homeAddressLatitude,String detailAddress,MultipartFile head,MultipartFile nationalEmblem) throws Exception{
//    	Map<String, Object> map = new HashMap<String, Object>();
//		String frontsidePath = null;// 正面文件路径
//		String backsidePath = null;// 背面文件路径
//		String headossPath = null;
//		String nationalEmblemossPath = null;
//    	if (head != null) {// 判断上传的文件是否为空
//			String type = null;// 文件类型
//			InputStream iStream = head.getInputStream();
//			String fileName = head.getOriginalFilename();// 文件原名称
//			// 判断文件类型
//			type = fileName.indexOf(".") != -1? fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()): null;
//			if (type != null) {// 判断文件类型是否为空
//				if ("GIF".equals(type.toUpperCase()) || "PNG".equals(type.toUpperCase()) || "JPG".equals(type.toUpperCase())) {
//					// 自定义的文件名称
//					String trueFileName = String.valueOf(System.currentTimeMillis()) + fileName;
//					// 设置存放图片文件的路径
//					frontsidePath = "xiaodai_idcard/"+idcard_number+"zhengmian"+".jpg";
//					OssUtil ossUtil = new OssUtil();
//					headossPath = ossUtil.uploadFile(iStream, frontsidePath);
//					if(headossPath.substring(0, 5).equals("https")) {
//						System.out.println("路径为："+headossPath);
////						shufflingFigure.setCover(ossPath);
//						map.put("msg", "图片上传成功");
//					}
////					InputStream inStream = head.getInputStream();
////					FolderUtil folderUtil = new FolderUtil();
////					String code = folderUtil.uploadImage(inStream, frontsidePath);
////					if(code.equals("200")) {
////						map.put("msg", "图片上传成功");
////
////					}else {
////						map.put("msg", "图片上传失败");
////						return map;
////					}
//				} else {
//					map.put("msg", "不是我们想要的文件类型,请按要求重新上传");
//					return map;
//				}
//			} else {
//				map.put("msg", "文件类型为空");
//				return map;
//			}
//		}else {
//			map.put("msg", "请上传图片");
//			return map;
//		} 
//    	
//    	if (nationalEmblem != null) {// 判断上传的文件是否为空
//			String type = null;// 文件类型
//			InputStream iStream = nationalEmblem.getInputStream();
//			String fileName = nationalEmblem.getOriginalFilename();// 文件原名称
//			// 判断文件类型
//			type = fileName.indexOf(".") != -1? fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()): null;
//			if (type != null) {// 判断文件类型是否为空
//				if ("GIF".equals(type.toUpperCase()) || "PNG".equals(type.toUpperCase()) || "JPG".equals(type.toUpperCase())) {
//					// 自定义的文件名称
//					String trueFileName = String.valueOf(System.currentTimeMillis()) + fileName;
//					// 设置存放图片文件的路径
//					backsidePath = "xiaodai_idcard/"+idcard_number+"beimian"+".jpg";
//					OssUtil ossUtil = new OssUtil();
//					nationalEmblemossPath = ossUtil.uploadFile(iStream, backsidePath);
//					if(nationalEmblemossPath.substring(0, 5).equals("https")) {
//						System.out.println("路径为："+nationalEmblemossPath);
////						shufflingFigure.setCover(ossPath);
//						map.put("msg", "图片上传成功");
//					}
////					InputStream inStream = nationalEmblem.getInputStream();
////					FolderUtil folderUtil = new FolderUtil();
////					String code = folderUtil.uploadImage(inStream, backsidePath);
////					if(code.equals("200")) {
////						map.put("msg", "图片上传成功");
////					}else {
////						map.put("msg", "图片上传失败");
////						return map;
////					}
//				} else {
//					map.put("msg", "不是我们想要的文件类型,请按要求重新上传");
//					return map;
//				}
//			} else {
//				map.put("msg", "文件类型为空");
//				return map;
//			}
//		}else {
//			map.put("msg", "请上传图片");
//			return map;
//		} 
//    	String authenticationSteps ="1";
//    	 int number = UserAttestationService.updateUserAttestation(name, gender, nationality, birth_year, birth_month, birth_day, address,issued_by,valid_date_start,valid_date_end,frontsidePath,backsidePath,userId,idcard_number,homeAddressLongitude,homeAddressLatitude,detailAddress,headossPath,nationalEmblemossPath,authenticationSteps);
//    	 if(number==1) {
//	    		map.put("code", 200);
//	    		map.put("msg","插入成功");
//	    	}else {
//				map.put("code",405);
//				map.put("msg", "插入失败");
//			}
//		return map;
//    	
//    }
    
    //个人信息模块的基础信息
    @RequestMapping("/setidcard")
    @ResponseBody
    @Transactional
    public Map<String, Object> setidcard(int userId,String name,String gender,String nationality,String birth_year,String birth_month,String birth_day,String address,String issued_by,
    		String valid_date_start,String valid_date_end,String idcard_number,String homeAddressLongitude,String homeAddressLatitude,String detailAddress,String head,String nationalEmblem) throws Exception{
    	Map<String, Object> map = new HashMap<String, Object>();
    	String authentime =System.currentTimeMillis()+"";//认证时间
		String frontsidePath = null;// 正面文件路径
		String backsidePath = null;// 背面文件路径
	    Base64ToInputStream base64ToInputStream = new Base64ToInputStream();
	    InputStream stream = base64ToInputStream.BaseToInputStream(head);
	    InputStream stream1 = base64ToInputStream.BaseToInputStream(nationalEmblem);
		String path = "xiaodai_idcard/"+idcard_number+"zhengmian"+".jpg";
		String path1 = "xiaodai_idcard/"+idcard_number+"beimian"+".jpg";
		OssUtil ossUtil = new OssUtil();
		frontsidePath = ossUtil.uploadFile(stream, path);
		backsidePath = ossUtil.uploadFile(stream1, path1);
    	String authenticationSteps ="1";
    	intUserService.updatename(name,userId);
    	String string = UserAttestationService.getauthenticationSteps(userId);
    	if(string==null) {
          	 int number = UserAttestationService.insertUserAttestation(name, gender, nationality, birth_year, birth_month, birth_day, address,issued_by,valid_date_start,valid_date_end,frontsidePath,backsidePath,userId,idcard_number,homeAddressLongitude,homeAddressLatitude,detailAddress,authenticationSteps,authentime);
          	 String userAuthenStatus ="0";
          	intUserService.updateUserAuthenStatus(userId,userAuthenStatus);
           	 if(number==1) {    		 
           	    	map.put("Ncode","2000");
       	    		map.put("code", 200);
       	    		map.put("msg","插入成功");
       	    	}else {
       	        	map.put("Ncode","405");
       				map.put("code",405);
       				map.put("msg", "插入失败");
       			}
    	}else {
	    	map.put("Ncode","410");
    		map.put("code", 410);
    		map.put("msg","该用户数据已存在");
    	}
		return map;
    	
    }
      
    
    
    //第三方收费接口
    @RequestMapping("/setthirdcalltongji")
    @ResponseBody
    @Transactional
    public Map<String, Object> setthirdcalltongji(int companyId,String thirdtypeid,int userId){
    	Map<String, Object> map = new HashMap<String, Object>();
		String date = System.currentTimeMillis()+"";
		thirdcalltongjiMapper.setthirdcalltongji2(companyId,thirdtypeid,date,userId);
    	map.put("Ncode","2000");
		map.put("code", 200);
		map.put("msg","插入成功");
		return map;
    	
    }  
    
    
    
    //个人信息模块的人脸识别
    @RequestMapping("/setface")
    @ResponseBody
    @Transactional
    public Map<String, Object> setface(int userId,String code,String facePhoto,String idcard_number) throws Exception{
    	Map<String, Object> map = new HashMap<String, Object>();
    	if("200".equals(code)) {
    		String authenticationSteps ="2";
    		int number = UserAttestationService.updateAuthenticationSteps(userId,authenticationSteps);
       	 if(number==1) {
         	map.put("Ncode","2000");
	    		map.put("code", 200);
	    		map.put("msg","插入成功");
	    	}else {
	    		map.put("Ncode","405");
				map.put("code",405);
				map.put("msg", "插入失败");
			}
    	}
    	String facePhotoPath = null;// 人脸照
	    Base64ToInputStream base64ToInputStream = new Base64ToInputStream();
	    InputStream stream = base64ToInputStream.BaseToInputStream(facePhoto);
		String path = "xiaodai_idcard/"+idcard_number+"facePhoto"+".jpg";
		OssUtil ossUtil = new OssUtil();
	    facePhotoPath = ossUtil.uploadFile(stream, path);
	    int num = UserAttestationService.updatefacePhoto(userId,facePhotoPath);
	    if(num==1) {
         	map.put("Ncode","2000");
	    		map.put("code", 201);
	    		map.put("msg","更新成功");
	    	}else {
	    		map.put("Ncode","406");
				map.put("code",405);
				map.put("msg", "更新失败");
			}

		return map;
    }
    
    
    //获取身份证号和姓名
    @RequestMapping("/getidCardAndName")
    @ResponseBody
    @Transactional
    public Map<String, Object> getidCardAndName (int userId){
 	   Map<String, Object> map  = new HashMap<String, Object>();
 	   map.put("Ncode","2000");
 	   Map<String, Object> map1 = UserAttestationService.getuserAttestation(userId);
 	   String name = (String) map1.get("trueName");
 	  String idcard_number = (String) map1.get("idcard_number");
 	  map.put("name", name);
 	 map.put("idcard_number", idcard_number);
 	return map;
 	   
    }
    
    
    //判断个人信息认证到哪一步了
    @RequestMapping("/getauthenticationSteps")
    @ResponseBody
    @Transactional
    public Map<String, Object> getauthenticationSteps(int userId){  
    	Map<String, Object> map  = new HashMap<String, Object>();
    		String authenticationSteps = UserAttestationService.getauthenticationSteps(userId);
    		map.put("authenticationSteps",authenticationSteps);
    		map.put("Ncode","2000");
		return map;
    }
    
}
