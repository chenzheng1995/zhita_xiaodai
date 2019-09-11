package com.zhita.service.manage.iftuoming;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhita.dao.manage.IftuomingMapper;
import com.zhita.model.manage.Iftuoming;
import com.zhita.util.RedisClientUtil;

@Service
public class IfTuomingServiceImp implements IntIfTuomingService{
	
	@Autowired
	private IftuomingMapper iftuomingMapper;
	
	//后台管理----查询
    public Iftuoming queryAll(Integer companyId){
    	Iftuoming iftuoming=iftuomingMapper.queryAll(companyId);
    	return iftuoming;
    }
    
    //后台管理---修改功能
    public int updateByPrimaryKey(Iftuoming record){
    	RedisClientUtil redisClientUtil=new RedisClientUtil();
    	int num=iftuomingMapper.updateByPrimaryKey(record);
    	redisClientUtil.delkey("phoneConfig");
    	return num;
    }
}
