package com.zhita.controller.idcard;

import com.zhita.controller.face.postDemo;

/**
 * sign 签名
 * sign_version   签名算法版本
 *capture_image 0:双面，1:人像面
 *return_url  网页跳转的目标URL
 *notify_url  回调URL
 *idcard_threshold  限定真是身份证阀值
 *limit_completeness  0:身份证必须完整，1:身份证内容区域必须都在图片内，2:不做限定，如某一面不满足要求则重新拍摄
 *limit_quality  限定图片质量，取［0，1］区间实数，3位有效数字，一般来说质量分低于0.753则认为存在质量问题，如存在某一面低于设定值则重新拍摄
 *limit_logic 0:进行逻辑判断，1:不进行逻辑判断，如存在某一面低于设定值则重新拍摄
 */
public class Idcard_biz_token {
	public String getBizToken(String sign,String sign_version,String capture_image,String return_url,String notify_url,String idcard_threshold,String limit_completeness,String limit_quality,String limit_logic){
		postDemo postDemo = new postDemo();
//		String param = "{'sign':'"+sign+"','sign_version':'hmac_sha1','capture_image':'0','return_url':'https://www.taobao.com','notify_url':'http://39.98.83.65:8080/zhita_xiaodai_app/idcardParam/notify','idcard_threshold':'0.8','limit_completeness':'2','limit_quality':'0.753','limit_logic':'1'}";
		String param = "{'sign':'"+sign+"','sign_version':'"+sign_version+"','capture_image':'"+capture_image+"','return_url':'"+return_url+"','notify_url':'"+notify_url+"','idcard_threshold':'"+idcard_threshold+"','limit_completeness':'"+limit_completeness+"','limit_quality':'"+limit_quality+"','limit_logic':'"+limit_logic+"'}";
		String result = postDemo.post("https://openapi.faceid.com/lite_ocr/v1/get_biz_token",param);
		return result;
		
	}
}
