package com.zhita.service.manage.thirdpartyint;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhita.dao.manage.SysUserMapper;
import com.zhita.dao.manage.ThirdpartyInterfaceMapper;
import com.zhita.model.manage.Company;
import com.zhita.model.manage.ThirdpartyInterface;

@Service
public class ThirdpartyintServiceImp implements IntThirdpartyintService{
	@Autowired
	private ThirdpartyInterfaceMapper thirdpartyInterfaceMapper;
	@Autowired
	private SysUserMapper sysUserMapper;
	
	//后台管理---查询出第三方接口配置表所有信息
    public List<ThirdpartyInterface> queryAll(Integer companyId){
    	List<ThirdpartyInterface> list=thirdpartyInterfaceMapper.queryAll(companyId);
    	return list;
    }
    
    //后台管理---添加功能（查询出所有公司）
    public List<Company> queryAllCompany(){
    	List<Company> list=sysUserMapper.queryAllCompany();
    	return list;
    }
    
    //后台管理---添加功能
    public int insert(ThirdpartyInterface record){
    	int num=thirdpartyInterfaceMapper.insert(record);
    	return num;
    }
    
    //后台管理---根据id查询当前对象信息
    public ThirdpartyInterface selectByPrimaryKey(Integer id){
    	ThirdpartyInterface thirdpartyInterface=thirdpartyInterfaceMapper.selectByPrimaryKey(id);
    	return thirdpartyInterface;
    }
    
    //后台管理---编辑保存功能
    public int updateByPrimaryKey(ThirdpartyInterface record){
    	int num=thirdpartyInterfaceMapper.updateByPrimaryKey(record);
    	return num;
    }
}
