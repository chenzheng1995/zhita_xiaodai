package com.zhita.service.manage.overduesettings;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhita.dao.manage.OverdueSettingsMapper;
import com.zhita.model.manage.OverdueSettings;

@Service
public class OverduesetServiceImp implements IntOverduesetService{
	@Autowired
	private OverdueSettingsMapper overdueSettingsMapper;
	
	 //后台管理---查询逾期信息表
	 public List<OverdueSettings> queryAll(Integer companyId){
		 List<OverdueSettings> list=overdueSettingsMapper.queryAll(companyId);
		 return list;
	 }
	 
	 //后台管理---添加功能
	 public int insert(OverdueSettings record){
		 record.setOperationtime(System.currentTimeMillis()+"");//获取当前时间戳
		 overdueSettingsMapper.insert(record);
		 int id=record.getId();
		 return id;
	 }
	 
	 //后台管理---根据当前id查询出当前用户信息
	 public OverdueSettings selectByPrimaryKey(Integer id){
		 OverdueSettings overdueSettings=overdueSettingsMapper.selectByPrimaryKey(id);
		 return overdueSettings;
	 }
	 
	 //后台管理---修改保存
	 public int updateByPrimaryKey(OverdueSettings record){
		 record.setOperationtime(System.currentTimeMillis()+"");//获取当前时间戳
		 int num=overdueSettingsMapper.updateByPrimaryKey(record);
		 return num;
	 }
	 
	  //后台管理---修改当前对象的假删除状态
	  public int upaFalseDel(String operator,Integer id){
		  String operationtime=System.currentTimeMillis()+"";
		  int num=overdueSettingsMapper.upaFalseDel(operator, operationtime, id);
		  return num;
	  }
}
