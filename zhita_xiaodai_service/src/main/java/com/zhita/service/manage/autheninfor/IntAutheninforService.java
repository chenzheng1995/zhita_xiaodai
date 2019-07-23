package com.zhita.service.manage.autheninfor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.zhita.model.manage.AuthenticationInformation;
import com.zhita.model.manage.Company;

public interface IntAutheninforService {
	
	//后台管理---查询认证信息表所有信息
    public List<AuthenticationInformation> queryAll(Integer companyId);
    
    //后台管理---添加功能
    public int insert(AuthenticationInformation record);
    
    //后台管理---添加功能（查询出所有公司）
    public List<Company> queryAllCompany();
    
    //后台管理---根据主键id查询出当前对象信息
    public AuthenticationInformation selectByPrimaryKey(Integer id);
    
    

    public Map<String, Object> updateByPrimaryKey1(AuthenticationInformation record,MultipartFile file) throws Exception;

	public ArrayList<AuthenticationInformation> getCertificationCenter(int companyId);
	
	//后台管理---更新保存功能
    public int updateByPrimaryKey(Integer id,String value);

	public ArrayList<String> getifAuthentication(int companyId);

}
