package com.zhita.dao.manage;

import java.math.BigDecimal;
import java.util.List;

import com.zhita.model.manage.Thirdpricefind;

public interface ThirdpricefindMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Thirdpricefind record);

    int insertSelective(Thirdpricefind record);

    Thirdpricefind selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Thirdpricefind record);

    int updateByPrimaryKey(Thirdpricefind record);
    
    //后台管理---查询所有
    List<Thirdpricefind> queryall(Integer companyid);
    
    
    //后台管理----修改价格
    int updateprice(BigDecimal price,Integer id);
    
    //后台管理---查询数量
    int querycount(Integer companyId,Integer thirdid, String starttime,String endtime);
    
}