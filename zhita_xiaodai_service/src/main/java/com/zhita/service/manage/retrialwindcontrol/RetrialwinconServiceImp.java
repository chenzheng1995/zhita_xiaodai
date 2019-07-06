package com.zhita.service.manage.retrialwindcontrol;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhita.dao.manage.RetrialWindControlMapper;
import com.zhita.model.manage.RetrialWindControl;

@Service
public class RetrialwinconServiceImp implements IntRetrialwinconService{
	
	@Autowired
	private RetrialWindControlMapper retrialWindControlMapper;
	
	//后台管理---查询再审风控表所有信息
    public List<RetrialWindControl> queryAll(Integer companyId){
    	List<RetrialWindControl> list=retrialWindControlMapper.queryAll(companyId);
    	return list;
    }
    
    //后台管理----添加功能
    public int insert(RetrialWindControl record){
    	record.setOperationtime(System.currentTimeMillis()+"");//获取当前时间戳
    	int num=retrialWindControlMapper.insert(record);
    	return num;
    }
    
    //后台管理---根据当前id查询当前对象现在
    public RetrialWindControl selectByPrimaryKey(Integer id){
    	RetrialWindControl retrialWindControl=retrialWindControlMapper.selectByPrimaryKey(id);
    	return retrialWindControl;
    }
    
    //后台管理---更新功能
    public int updateByPrimaryKey(RetrialWindControl record){
    	record.setOperationtime(System.currentTimeMillis()+"");//获取当前时间戳
    	int num=retrialWindControlMapper.updateByPrimaryKey(record);
    	return num;
    }
}
