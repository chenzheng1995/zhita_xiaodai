package com.zhita.service.manage.source;

import java.util.List;
import java.util.Map;

import com.zhita.model.manage.Company;
import com.zhita.model.manage.Source;
import com.zhita.model.manage.TongjiSorce;

public interface IntSourceService {
	
	//后台管理---查询渠道表所有信息
    public Map<String,Object> queryAll(Integer comapnyId,Integer page);
    
    //后台管理---添加功能（查询出所有公司）
    public List<Company> queryAllCompany();
    
    //后台管理---添加功能
    public int insert(Source record);
    
    //后台管理---根据id查询当前对象信息
    public Source selectByPrimaryKey(Integer id);
    
    //后台管理---编辑功能
    public int updateByPrimaryKey(Source record);
    
    //后台管理---根据id  对当前对象的假删除状态进行修改
    public int updateFalDel(Integer id);
    
    //后台管理---查询当天各个渠道在用户表的注册数量
    public List<TongjiSorce> queryAllSourceByUser(Integer companyid,String StartTime,String EndTime);
    
    //后台管理---查询某一天某个渠道的注册数量
    public TongjiSorce queryAllSourceByUserDetail(Integer companyid,String StartTime,String EndTime,String sourceName);
    
    //后台管理---通过渠道名称查询出当前渠道的折扣率
    public String queryDiscount(String sourceName,Integer companyId);
}
