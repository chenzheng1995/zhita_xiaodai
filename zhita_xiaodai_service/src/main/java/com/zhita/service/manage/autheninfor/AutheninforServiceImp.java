package com.zhita.service.manage.autheninfor;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhita.dao.manage.AuthenticationInformationMapper;
import com.zhita.dao.manage.SysUserMapper;
import com.zhita.model.manage.AuthenticationInformation;
import com.zhita.model.manage.Company;

@Service
public class AutheninforServiceImp implements IntAutheninforService{
	@Autowired
	private AuthenticationInformationMapper authenticationInformationMapper;
	@Autowired
	private SysUserMapper sysUserMapper;
	
	//后台管理---查询认证信息表所有信息
    public List<AuthenticationInformation> queryAll(Integer companyId){
    	List<AuthenticationInformation> list=authenticationInformationMapper.queryAll(companyId);
    	return list;
    }
    
    
    //后台管理---添加功能（查询出所有公司）
    public List<Company> queryAllCompany(){
    	List<Company> list=sysUserMapper.queryAllCompany();
    	return list;
    }
    
    //后台管理---添加功能
    public int insert(AuthenticationInformation record){
    	int num=authenticationInformationMapper.insert(record);
    	return num;
    }
    
    //后台管理---根据主键id查询出当前对象信息
    public AuthenticationInformation selectByPrimaryKey(Integer id){
    	AuthenticationInformation authenticationInformation=authenticationInformationMapper.selectByPrimaryKey(id);
    	return authenticationInformation;
    }
    
    //后台管理---更新保存功能
    public int updateByPrimaryKey(AuthenticationInformation record){
    	int num=authenticationInformationMapper.updateByPrimaryKey(record);
    	return num;
    }
}
