package com.zhita.service.manage.fendback;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhita.dao.manage.FeedbackMapper;
import com.zhita.model.manage.Feedback;
import com.zhita.util.PageUtil2;
import com.zhita.util.PhoneDeal;
import com.zhita.util.Timestamps;
import com.zhita.util.TuoMinUtil;

@Service
public class FeedbackServiceImp implements IntFeedbackService{
	
	@Autowired
	private FeedbackMapper feedbackMapper;
	
	//后台管理----查询功能
	public Map<String,Object> queryAll(Integer page){
		PhoneDeal phoneDeal=new PhoneDeal();
		TuoMinUtil tuoMinUtil=new TuoMinUtil();
		int totalCount=feedbackMapper.queryAllcount();//查询总条数
		PageUtil2 pageUtil=new PageUtil2(page,totalCount);
    	if(page<1) {
    		page=1;
    		pageUtil.setPage(page);
    	}
    	else if(page>pageUtil.getTotalPageCount()) {
    		if(totalCount==0) {
    			page=pageUtil.getTotalPageCount()+1;
    		}else {
    			page=pageUtil.getTotalPageCount();
    		}
    		pageUtil.setPage(page);
    	}
    	int pages=(page-1)*pageUtil.getPageSize();
		List<Feedback> list=feedbackMapper.queryAll(pages,pageUtil.getPageSize());//查询list集合
		for (int i = 0; i < list.size(); i++) {
			list.get(i).setTime(Timestamps.stampToDate(list.get(i).getTime()));
			list.get(i).setPhone(tuoMinUtil.mobileEncrypt(phoneDeal.decryption(list.get(i).getPhone())));
		}
		
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("feedbacklist", list);
		map.put("pageutil", pageUtil);
		return map;
	}
}
