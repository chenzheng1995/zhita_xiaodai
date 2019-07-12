package com.zhita.service.manage.overdueclass;

import java.util.List;
import java.util.Map;

import com.zhita.model.manage.Company;
import com.zhita.model.manage.OverdueClass;

public interface IntOverdueclassService {
	//后台管理 --- 查询逾期等级表所有信息
    public Map<String,Object> queryAll(Integer companyId);
    
    //后台管理---添加功能（查询出所有公司）
    public List<Company> queryAllCompany();
    
    //后台管理---添加功能
    public int insert(OverdueClass record);
    
    //后台管理---根据id查询出当前对象信息
    public OverdueClass selectByPrimaryKey(Integer id);
    
    //后台管理---修改保存功能
    public int updateByPrimaryKey(OverdueClass record);
    
    //后台管理---修改黑名单分界线的值
    public int update(Integer blacklinevalue);
}
