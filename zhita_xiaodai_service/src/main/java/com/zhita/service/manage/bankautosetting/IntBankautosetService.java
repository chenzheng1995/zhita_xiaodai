package com.zhita.service.manage.bankautosetting;

import java.util.List;
import java.util.Map;

import com.zhita.model.manage.Bankauto;
import com.zhita.model.manage.Company;

public interface IntBankautosetService {
	
	//后台管理----查询所有
    public Map<String,Object> queryAll(Integer companyId,Integer page);
    
    //后台管理-----添加功能（先查询出所有公司）
    public List<Company> queryAllCompany();

    //后台管理----添加功能
    public int insert(Bankauto record);
    
    //后台管理----根据id查询当前对象
    public Bankauto selectByPrimaryKey(Integer id);
    
    //后台管理----更新修改
    public int updateByPrimaryKey(Bankauto record);
    
    //后台管理----修改假删除状态
    public int upaFaldel(Integer id);
}
