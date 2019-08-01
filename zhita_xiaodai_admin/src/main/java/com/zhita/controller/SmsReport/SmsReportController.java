package com.zhita.controller.SmsReport;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhita.model.manage.Shortmessage;
import com.zhita.model.manage.SmsSendRequest;
import com.zhita.model.manage.Usershortmessage;
import com.zhita.service.manage.SmsReport.Smservice;


//短信发送
@Controller
@RequestMapping("UserType")
public class SmsReportController {
	
	
	@Autowired
	private Smservice serv;
	
	
	
	/**
	 * 短信发送
	 * @param sm
	 * @param msg 短信内容
	 * @param phone 手机号 
	 * @param phonenum 手机号数量
	 * @param companyid 公司ID
	 * @return
	 */
	@ResponseBody
	@RequestMapping("SendSms")
	public Map<String, Object> SendSms(SmsSendRequest sm){
		return serv.SendSm(sm);
	}
	
	
	
	
	
	/**
	 * 查询逾期前用户手机号
	 * @param sm
	 * @param biaoshi 标识   
	 * @param companyid 公司ID
	 * @return
	 */
	@ResponseBody
	@RequestMapping("DateAllPhone")
	public Map<String, Object> DataAllPhone(SmsSendRequest sm){
		return serv.AllPhone(sm);
	}
	
	
	
	
	
	/**
	 * 逾期时间查询
	 * @return
	 */
	@ResponseBody
	@RequestMapping("AllCollection")
	public Map<String, Object> AllCollection(String collection_time){
		return serv.AllCollection(collection_time);
	}
	
	
	
	
	
	
	
	/**
	 * 查询每天的发送数量
	 * @param shor
	 * @return
	 */
	@ResponseBody
	@RequestMapping("AllShortMessage")
	public Map<String, Object> AllDayShort(Shortmessage shor){
		return serv.DayShortMessage(shor);
	}
	
	
	
	
	
	/**
	 * 查询一天天的发送数量详情
	 * @param shor
	 * @return
	 */
	@ResponseBody
	@RequestMapping("OneDayShortMessage")
	public Map<String, Object> OneDayShort(Shortmessage shor){
		return serv.OneDayShortMessage(shor);
	}
	
	
	
	
	
	/**
	 * 根据用户的注册客户端查询手机号
	 * @param companyId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("UserTypeShortMessage")
	public Map<String, Object> UserTypesShortMessage(Usershortmessage compan){
		return serv.UserTypes(compan);
	}
	
	
	
	/**
	 * 查询发送记录
	 * @param companyId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("AllUserShortMessage")
	public Map<String, Object> AllUserShortMessage(Integer companyId){
		return serv.AllUserShortMessage(companyId);
	}
	
	
	
	/**
	 * 添加短信
	 * @param usershort
	 * @return
	 */
	@ResponseBody
	@RequestMapping("AddUserShort")
	public Map<String, Object> AddUserShort(Usershortmessage usershort){
		return serv.AddUserShortMessage(usershort);
	}
	
	
}
