package com.zhita.service.manage.autheninfor;


import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.zhita.dao.manage.AuthenticationInformationMapper;
import com.zhita.dao.manage.SysUserMapper;
import com.zhita.model.manage.AuthenticationInformation;
import com.zhita.model.manage.Company;

import sun.text.normalizer.ICUBinary.Authenticate;

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
    public int updateByPrimaryKey(Integer id,String value){
    	int num=authenticationInformationMapper.updateByPrimaryKey(id,value);
    	return num;
    }


	@Override
	public ArrayList<AuthenticationInformation> getCertificationCenter(int companyId) {
		ArrayList<AuthenticationInformation> CertificationCenter = authenticationInformationMapper.getCertificationCenter(companyId);
		return CertificationCenter;
	}


	@Override
	public Map<String, Object> updateByPrimaryKey1(AuthenticationInformation record, MultipartFile file)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public ArrayList<String> getifAuthentication(int companyId) {
		ArrayList<String> list = authenticationInformationMapper.getifAuthentication(companyId);
		return list;
	}
}
