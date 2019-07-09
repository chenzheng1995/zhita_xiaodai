package com.zhita.service.manage.source;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.zhita.model.manage.Source;
import com.zhita.model.manage.TongjiSorce;

public interface IntSourceService {
	
	//后台管理---查询渠道表所有信息
    public Map<String,Object> queryAll(Integer comapnyId,Integer page);
    
    //后台管理---添加功能（查询出所有公司   模板和风控）
    public Map<String, Object> queryAllCompany(Integer companyId);
    
    //后台管理---添加功能
    public int insert(Source record);
    
    //后台管理---根据id查询当前对象信息
    public Source selectByPrimaryKey(Integer id);
    
    //后台管理---编辑功能
    public int updateByPrimaryKey(Source record)throws ParseException;
    
    //后台管理---根据id  对当前对象的假删除状态进行修改
    public int updateFalDel(Integer id);
    
    //后台管理---查询当天各个渠道在用户表的注册数量
    public List<TongjiSorce> queryAllSourceByUser(Integer companyid,String StartTime,String EndTime);
    
    //后台管理---查询某一天某个渠道的注册数量
    public TongjiSorce queryAllSourceByUserDetail(Integer companyid,String StartTime,String EndTime,String sourceName);
    
    //定时任务
    //后台管理----做定时任务需要执行的方法（每日0点  将各个渠道的历史数据存入历史表）
    public void queryAllTongji() throws ParseException;
    
    //后台管理----通过渠道名字查询当前渠道在历史表的信息
    public List<TongjiSorce> queryAllBySourceName(String sourceName);
    
    //后台管理 ------查询统计申请数 
    public int queryApplicationNumber(Integer companyId,String sourceName,String startTime,String endTime);
    
    //后台管理---通过渠道名称查询出当前渠道的折扣率
    public String queryDiscount(String sourceName,Integer companyId);


	public int getsourceId(String sourceName);

    
    //后台管理---通过渠道和时间查询在历史表是否有数据
    public TongjiSorce queryBySourcenameAndDate(String sourcename,String startdate,String enddate);

}
