package com.zhita.service.manage.manconsettings;

import java.util.List;

import com.zhita.model.manage.Company;
import com.zhita.model.manage.ManageControlSettings;

public interface IntManconsettingsServcie {
	
	//后台管理---查询风控设置管理表所有信息
    public List<ManageControlSettings> queryAll(Integer companyId);
    
    //后台管理---添加功能（查询出所有公司）
    public List<Company> queryAllCompany();
    
    //后台管理---添加功能
    public int insert(ManageControlSettings record);
    
    //后台管理---根据主键id查询当前对象信息
    public ManageControlSettings selectByPrimaryKey(Integer id);
    
    //后台管理---编辑保存功能
    public int updateByPrimaryKey(ManageControlSettings record);
}
