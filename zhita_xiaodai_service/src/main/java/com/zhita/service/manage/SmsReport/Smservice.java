package com.zhita.service.manage.SmsReport;

import java.util.Map;

import com.zhita.model.manage.Shortmessage;
import com.zhita.model.manage.SmsSendRequest;
import com.zhita.model.manage.Usershortmessage;

public interface Smservice {
	
	
	Map<String, Object> SendSm(SmsSendRequest sm);
	
	
	Map<String, Object> SendSmOne(SmsSendRequest sm);
	
	
	Map<String, Object> DayShortMessage(Shortmessage shor);
	
	
	Map<String, Object> OneDayShortMessage(Shortmessage shor);
	
	
	Map<String, Object> AllPhone(SmsSendRequest sm);
	
	
	Map<String, Object> AllCollection(String collection_time);
	
	
	Map<String, Object> UserTypes(Usershortmessage companyId);


	Map<String, Object> AllUserShortMessage(Usershortmessage companyId);
	
	
	Map<String, Object> AddUserShortMessage(Usershortmessage shor);
	
	
	Shortmessage sendDateSned(SmsSendRequest sms);
	
	
	void sendBank();
}
