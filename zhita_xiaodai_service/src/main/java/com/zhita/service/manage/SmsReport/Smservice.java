package com.zhita.service.manage.SmsReport;

import java.util.Map;

import com.zhita.model.manage.Shortmessage;
import com.zhita.model.manage.SmsSendRequest;

public interface Smservice {
	
	
	Map<String, Object> SendSm(SmsSendRequest sm);
	
	
	Map<String, Object> DayShortMessage(Shortmessage shor);
	
	
	Map<String, Object> OneDayShortMessage(Shortmessage shor);
	
	
	Map<String, Object> AllPhone(SmsSendRequest sm);
	
	
	Map<String, Object> AllCollection(String collection_time);

}
