package com.zhita.service.manage.manconsettings;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhita.dao.manage.ManageControlSettingsMapper;
import com.zhita.dao.manage.SysUserMapper;
import com.zhita.model.manage.Company;
import com.zhita.model.manage.ManageControlSettings;

@Service
public class ManconsettingsServiceImp implements IntManconsettingsServcie{
	
	@Autowired
	private ManageControlSettingsMapper manageControlSettingsMapper;
	@Autowired
	private SysUserMapper sysUserMapper;
	
	//后台管理---查询风控设置管理表所有信息
    public List<ManageControlSettings> queryAll(Integer companyId){
    	List<ManageControlSettings> list=manageControlSettingsMapper.queryAll(companyId);
    	return list;
    }
    
    //后台管理---添加功能（查询出所有公司）
    public List<Company> queryAllCompany(){
    	List<Company> list=sysUserMapper.queryAllCompany();
    	return list;
    }
    
    //后台管理---添加功能
    public int insert(ManageControlSettings record){
    	int num=manageControlSettingsMapper.insert(record);
    	return num;
    }
    
    //后台管理---根据主键id查询当前对象信息
    public ManageControlSettings selectByPrimaryKey(Integer id){
    	ManageControlSettings manageControlSettings=manageControlSettingsMapper.selectByPrimaryKey(id);
    	return manageControlSettings;
    }
    
    //后台管理---编辑保存功能
    public int updateByPrimaryKey(ManageControlSettings record){
    	int num=manageControlSettingsMapper.updateByPrimaryKey(record);
    	return num;
    }
}
