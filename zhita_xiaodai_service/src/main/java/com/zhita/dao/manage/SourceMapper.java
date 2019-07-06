package com.zhita.dao.manage;

import java.util.List;

import com.zhita.model.manage.Source;
import com.zhita.model.manage.TongjiSorce;

public interface SourceMapper {
    int deleteByPrimaryKey(Integer id);
    
    //后台管理---添加功能（判断该渠道名是否在渠道表存在    其假删除状态是1）
    int ifSourceNameIfExist(String sourceName);
    
    //后台管理---添加功能（该渠道在渠道表已经存在   只需做修改）
    int updateSource(Source source);
    
    //后台管理---添加功能
    int insert(Source record);

    int insertSelective(Source record);
    
    //后台管理---根据id查询当前对象信息
    Source selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Source record);
    
    //后台管理---编辑功能
    int updateByPrimaryKey(Source record);
    
    //后台管理---查询渠道表所有信息
    List<Source> queryAll(Integer comapnyId);
    
    //后台管理---根据id  对当前对象的假删除状态进行修改
    int updateFalDel(Integer id);
    
    //后台管理---查询当天各个渠道在用户表的注册数量
    List<TongjiSorce> queryAllSourceByUser(Integer companyid,String StartTime,String EndTime);
    
    //后台管理---查询某一天某个渠道的注册数量
    TongjiSorce queryAllSourceByUserDetail(Integer companyid,String StartTime,String EndTime,String sourceName);
    
    //后台管理---通过渠道名称查询出当前渠道的折扣率
    String queryDiscount(String sourceName,Integer companyId);

	int getsourceId(String sourceId);
    
}