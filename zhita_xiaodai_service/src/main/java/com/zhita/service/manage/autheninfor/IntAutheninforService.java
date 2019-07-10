package com.zhita.service.manage.autheninfor;

import java.util.List;

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
    
    //后台管理---更新保存功能
    public int updateByPrimaryKey(AuthenticationInformation record);
}
