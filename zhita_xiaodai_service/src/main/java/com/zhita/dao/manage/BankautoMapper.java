package com.zhita.dao.manage;

import java.util.List;

import com.zhita.model.manage.Bankauto;
import com.zhita.model.manage.Company;

public interface BankautoMapper {
    int deleteByPrimaryKey(Integer id);
    
    //后台管理-----添加功能（先查询出所有公司）
    List<Company> queryAllCompany();

    //后台管理----添加功能
    int insert(Bankauto record);

    int insertSelective(Bankauto record);
    
    //后台管理----根据id查询当前对象
    Bankauto selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Bankauto record);
    
    //后台管理----更新修改
    int updateByPrimaryKey(Bankauto record);
    
    //后台管理----查询数量
    int querycount(Integer companyId);
    
    //后台管理----查询所有
    List<Bankauto> queryAll(Integer companyId,Integer page,Integer pagesize);
    
    
    //后台管理----修改假删除状态
    int upaFaldel(Integer id);
}