package com.zhita.dao.manage;

import java.util.ArrayList;
import java.util.List;

import com.zhita.model.manage.AuthenSecondattributes;
import com.zhita.model.manage.AuthenticationInformation;

public interface AuthenticationInformationMapper {
    int deleteByPrimaryKey(Integer id);
    
    //后台管理---添加功能
    int insert(AuthenticationInformation record);

    int insertSelective(AuthenticationInformation record);
    
    //后台管理---根据主键id查询出当前对象信息
    AuthenticationInformation selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AuthenticationInformation record);
    
    //后台管理---更新保存功能
    int updateByPrimaryKey(Integer id,String value);
    
    //后台管理---查询认证信息表所有信息
    List<AuthenticationInformation> queryAll(Integer companyId);
    
    //后台管理---根据认证表的认证id查询认证信息二级属性关联表的值
    List<AuthenSecondattributes> queryauthsecond(Integer authid);
    
    //后台管理---根据主键id更新状态（authen_secondattributes表）
    int upastatuByprimiartKey(String status,Integer id);
    
    //后台管理---根据id查询图标字段
    String queryIcon(Integer id);

	ArrayList<AuthenticationInformation> getCertificationCenter(int companyId);

	ArrayList<String> getifAuthentication(int companyId);
}