package com.zhita.service.manage.fengkong;

import org.springframework.beans.factory.annotation.Autowired;
import com.zhita.dao.manage.FengkongMapper;



public class FengkongServiceimp implements FengkongService{

	
	@Autowired
	private FengkongMapper fmap;
	
	
	@Override
	public Integer Passuser(Integer userId) {
		return fmap.passuser(fmap.UserPass(userId));
	}

}
