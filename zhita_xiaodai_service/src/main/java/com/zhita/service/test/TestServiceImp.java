package com.zhita.service.test;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhita.dao.manage.XtestMapper;




@Service
public class TestServiceImp implements TestService{
	@Autowired
	XtestMapper xtestMapper;

	@Override
	public int settest(String name) {
		int number = xtestMapper.settest(name); 
		return number;
	}

	@Override
	public String gettest(int id) {
		String name = xtestMapper.gettest(id);
		return name;
	}

}
