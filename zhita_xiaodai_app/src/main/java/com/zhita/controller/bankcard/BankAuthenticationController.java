package com.zhita.controller.bankcard;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.zhita.model.manage.Bankcard;
import com.zhita.service.manage.bankcard.BankcardService;
import com.zhita.service.manage.chanpayQuickPay.Chanpayservice;


@Controller
@RequestMapping("bankauthentication")
public class BankAuthenticationController {
	
	
	@Autowired
	private BankcardService bser;
	
	@Autowired
	private Chanpayservice chersim;
	
	public static void main(String[] args) {
		String bankcardTypeName = "";
		
	    String host = "https://bankver.market.alicloudapi.com";
	    String path = "/creditop/BankCardQuery/BankCardVerification";
	    String method = "GET";
	    String appcode = "ea779e8386254a6db6b3d4b599dfea44";
	    Map<String, String> headers = new HashMap<String, String>();
	    //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
	    headers.put("Authorization", "APPCODE " + appcode);
	    Map<String, String> querys = new HashMap<String, String>();
	    querys.put("accountNo", "6214835901884138");
	    querys.put("bankPreMobile", "13487139655");
	    querys.put("idCardCode", "420621199905157170");
	    querys.put("name", "东新雨");


	    try {
	    	/**
	    	* 重要提示如下:
	    	* HttpUtils请从
	    	* https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
	    	* 下载
	    	*
	    	* 相应的依赖请参照
	    	* https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
	    	*/
	    	HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
	    	String returns = response.toString();
	    	String code = returns.substring(9, 13);
	    	RreturnAuth rauth = JSON.parseObject(EntityUtils.toString(response.getEntity()), RreturnAuth.class);
	    	System.out.println(rauth.getResult().getMessagetype()+rauth.getResult().getMessage());
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	}
	
	/**
	 * 
	 * @param accountNo		//银行卡号
	 * @param bankPreMobile	//手机号
	 * @param idCardCode	//身份证号
	 * @param name			//姓名
	 * @param bankcardTypeName//开户行
	 * @return
	 */
	@ResponseBody
	@RequestMapping("BankDistinguishA")
	public Map<String, Object> IdNumber(String accountNo,String bankPreMobile,String idCardCode,String name,String bankcardTypeName,Integer userId){
		  Map<String, Object> map = new HashMap<String, Object>();
		  Integer banktypeid = bser.GetBanktype(bankcardTypeName);
		if(banktypeid!=null){
			
			 	String host = "https://bankver.market.alicloudapi.com";
			    String path = "/creditop/BankCardQuery/BankCardVerification";
			    String method = "GET";
			    String appcode = "ea779e8386254a6db6b3d4b599dfea44";
			    Map<String, String> headers = new HashMap<String, String>();
			    //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
			    headers.put("Authorization", "APPCODE " + appcode);
			    Map<String, String> querys = new HashMap<String, String>();
			    querys.put("accountNo", "6212261102005817757");
			    querys.put("bankPreMobile", "13487139655");
			    querys.put("idCardCode", "130321198804010180");
			    querys.put("name", "东新雨");

			    Bankcard bank = new Bankcard();
			    bank.setUserId(userId);
			    bank.setBankcardTypeId(banktypeid);
			    bank.setBankcardName(accountNo);
			    bank.setTiedCardPhone(bankPreMobile);
			    bank.setIDcardnumber(idCardCode);
			    bank.setDeleted("0");
			    bank.setCstmrnm(name);
			    try {
			    	/**
			    	* 重要提示如下:
			    	* HttpUtils请从
			    	* https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
			    	* 下载
			    	*
			    	* 相应的依赖请参照
			    	* https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
			    	*/
			    	HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
			    	RreturnAuth rauth = JSON.parseObject(EntityUtils.toString(response.getEntity()), RreturnAuth.class);
			    	if(rauth.getResult().getMessagetype()==0){//等于0是认证成功
			    		bank.setAttestationStatus("1");
			    		chersim.AddBankcard(bank);
			    		map.put("code", 200);
			    		map.put("Ncode", 2000);
			    		map.put("msg", rauth.getResult().getMessage());
			    	}else{
			    		map.put("code", 0);
			    		map.put("Ncode", 0);
			    		map.put("msg", rauth.getResult().getMessage());
			    	}
			    } catch (Exception e) {
			    	e.printStackTrace();
			    }
			
		}else{
			map.put("code", "0");
			map.put("Ncode", "0");
			map.put("msg", "该卡不在放款范围");
		}
	   
	    return map;
	}
	
	
	
	
	
	
	
	
	
	/**
	 * 身份证识别
	 */
	@ResponseBody
	@RequestMapping("BankDistinguish")
	public Map<String, Object> BankDistinguish(String accountNo,String bankPreMobile,String idCardCode,String name,Integer userId,Integer bankcardTypeId){
		Map<String, Object> map = new HashMap<String, Object>();
		String host = "https://bankver.market.alicloudapi.com";
	    String path = "/creditop/BankCardQuery/BankCardVerification";
	    String method = "POST";
	    String appcode = "ea779e8386254a6db6b3d4b599dfea44";
	    Map<String, String> headers = new HashMap<String, String>();
	    //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
	    headers.put("Authorization", "APPCODE " + appcode);
	    Map<String, String> querys = new HashMap<String, String>();
	    querys.put("accountNo", accountNo);
	    querys.put("bankPreMobile", bankPreMobile);
	    querys.put("idCardCode", idCardCode);
	    querys.put("name", name);
	    Bankcard bank = new Bankcard();
	    bank.setUserId(userId);
	    bank.setBankcardTypeId(bankcardTypeId);
	    bank.setBankcardName(name);
	    bank.setTiedCardPhone(bankPreMobile);
	    bank.setIDcardnumber(idCardCode);
	    bank.setCstmrnm(name);
	    try {
	    	/**
	    	* 重要提示如下:
	    	* HttpUtils请从
	    	* https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
	    	* 下载
	    	*
	    	* 相应的依赖请参照
	    	* https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
	    	*/
	    	HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
	    	String returns = response.toString();
	    	String code = returns.substring(9, 13);
	    	System.out.println("code:"+code);
	    	System.out.println(returns);
	    	if(code.equals("200")){
	    		bank.setAttestationStatus("1");
	    		
	    		map.put("code", code);
	    		map.put("msg", "识别成功");
	    	}else{
	    		map.put("code", code);
	    		map.put("msg", "识别异常");
	    	}
	    	//获取response的body
	    	//System.out.println(EntityUtils.toString(response.getEntity()));
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	    return map;
	}
	
	
	
	
	
	
	/**
	 * 身份证识别
	 */
	@ResponseBody
	@RequestMapping("Photorecognition")
	public Map<String, Object> Photorecognition(String img){
		Map<String, Object> map = new HashMap<String, Object>();
		String host = "https://bankocr.market.alicloudapi.com";
	    String path = "/cardbank";
	    String method = "POST";
	    String appcode = "ea779e8386254a6db6b3d4b599dfea44";
	    Map<String, String> headers = new HashMap<String, String>();
	    //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
	    headers.put("Authorization", "APPCODE " + appcode);
	    //根据API的要求，定义相对应的Content-Type
	    headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
	    Map<String, String> querys = new HashMap<String, String>();
	    Map<String, String> bodys = new HashMap<String, String>();
	    bodys.put("img", img);
	    //"http://img3.fegine.com/image/bank.jpg"
	    //或者base64
	    //bodys.put("img", "data:image/jpeg;base64,/9j/4A......");
	    try {
	    	/**
	    	* 重要提示如下:
	    	* HttpUtils请从
	    	* https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
	    	* 下载
	    	*
	    	* 相应的依赖请参照
	    	* https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
	    	*/
	    	HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
	    	//System.out.println(response.toString());
	    	//获取response的body
	    	System.out.println();
	    	ReturnBank retun = JSON.parseObject(EntityUtils.toString(response.getEntity()), ReturnBank.class);
	    	System.out.println(retun.getResult().getBank_name());
	    	
	    	map.put("code", retun.getCode());
	    	map.put("msg", retun.getMsg());
	    	map.put("bankname", retun.getResult().getBank_name());
	    	map.put("bank_card_number", retun.getResult().getBank_card_number());
	    } catch (Exception e) {
	    	e.printStackTrace();
	    	map.put("code", 0);
	    	map.put("msg", "查询失败");
	    }
	    return map;
	}
	
	
	
	
	
		
		
	@ResponseBody
	@RequestMapping("AllBankName")
	public Map<String, Object> BankName(){
		Integer companyId = 3;
		return bser.SelectBankCard(companyId);
	}
}
