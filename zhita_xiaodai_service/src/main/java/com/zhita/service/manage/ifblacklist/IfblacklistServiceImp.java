package com.zhita.service.manage.ifblacklist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhita.dao.manage.IfblacklistMapper;
import com.zhita.model.manage.Ifblacklist;

@Service
public class IfblacklistServiceImp implements IntIfblacklistService{

	@Autowired
	private IfblacklistMapper ifblacklistMapper;
	
	
	//后台管理——列表查询
	public Ifblacklist queryAll(Integer companyId){
		return ifblacklistMapper.queryAll(companyId);
	}
	
	//后台管理——编辑功能
    public int updateByPrimaryKey(Ifblacklist record){
    	return ifblacklistMapper.updateByPrimaryKey(record);
    }
}
