package com.zhita.dao.manage;

import java.util.List;

import com.zhita.model.manage.SourceDiscountHistory;
import com.zhita.model.manage.TongjiSorce;

public interface SourceDiscountHistoryMapper {
    int deleteByPrimaryKey(Integer id);
    
    //后台管理---往历史表插入数据
    int insert(TongjiSorce record);

    int insertSelective(SourceDiscountHistory record);

    SourceDiscountHistory selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SourceDiscountHistory record);
    
    //后台管理---修改保存
    int updateByPrimaryKey(TongjiSorce record);
    
    //后台管理  ----- 查询当前渠道在历史表的时间
    List<String> queryDate(Integer sourceName);
    
    //后台管理----通过渠道名字查询当前渠道在历史表的信息
    List<TongjiSorce> queryAllBySourceName(Integer sourceName);
    
    //后台管理---通过渠道和时间查询是否有数据
    TongjiSorce queryBySourcenameAndDate(Integer sourcename,String startdate,String enddate);
}