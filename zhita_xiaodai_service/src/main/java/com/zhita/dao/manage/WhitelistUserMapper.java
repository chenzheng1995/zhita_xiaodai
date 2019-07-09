package com.zhita.dao.manage;

import java.util.List;

import com.zhita.model.manage.WhitelistUser;

public interface WhitelistUserMapper {
    int deleteByPrimaryKey(Integer id);
    
    //后台管理---添加操作
    int insert(WhitelistUser record);

    int insertSelective(WhitelistUser record);
    
    //后台管理---根据id查询当前用户的信息
    WhitelistUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WhitelistUser record);
    
    //后台管理---更新保存功能
    int updateByPrimaryKey(WhitelistUser record);
    
    //后台管理---查询列表
    List<WhitelistUser> queryAll(Integer companyId,String name,String phone,String idcard);
    
    //后台管理---更新假删除状态
    int upaFalseDel(Integer id);
}