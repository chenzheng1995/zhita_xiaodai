package com.zhita.service.manage.applycondition;

import java.util.List;
import java.util.Map;

import com.zhita.model.manage.ApplyCondition;
import com.zhita.model.manage.Company;

public interface IntApplyconditionService {
	//后台管理---查询申请条件配置表所有信息
    public List<ApplyCondition> queryAll(Integer companyId);
    
    //后台管理---添加功能（查询出所有公司）
    public List<Company> queryAllCompany();
    
    //后台管理---添加功能
    public int insert(ApplyCondition record);
    
    //后台管理---根据id查询出当前对象
    public ApplyCondition selectByPrimaryKey(Integer id);
    
    //后台管理---编辑功能
    public int updateByPrimaryKey(ApplyCondition record);

	public Map<String, Object> getApplycondition(int companyId);
}
