package com.zhita.service.manage.fengkong;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhita.dao.manage.FengkongMapper;


@Service
public class FengkongServiceimp implements FengkongService{

	
	@Autowired
	private FengkongMapper fmap;
	
	
	@Override
	public Integer Passuser(Integer userId) {
		return fmap.passuser(fmap.UserPass(userId));
	}

}
