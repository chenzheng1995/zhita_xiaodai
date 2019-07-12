package com.zhita.service.manage.thirdpartyint;

import java.util.List;
import java.util.Map;

import com.zhita.model.manage.ThirdpartyInterface;

public interface IntThirdpartyintService {
	
	//后台管理---查询出第三方接口配置表所有信息
    public List<ThirdpartyInterface> queryAll(Integer companyId);
    
    //后台管理---添加功能（查询出所有公司）
    public Map<String, Object> queryAllCompany();
    
    //后台管理---添加功能
    public int insert(ThirdpartyInterface record);
    
    //后台管理---根据id查询当前对象信息
    public ThirdpartyInterface selectByPrimaryKey(Integer id);
    
    //后台管理---编辑保存功能
    public int updateByPrimaryKey(ThirdpartyInterface record);
}
