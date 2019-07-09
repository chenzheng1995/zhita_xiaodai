package com.zhita.dao.manage;

import java.util.List;

import com.zhita.model.manage.BlacklistUser;

public interface BlacklistUserMapper {
    int deleteByPrimaryKey(Integer id);
    
    //后台管理---添加操作
    int insert(BlacklistUser record);

    int insertSelective(BlacklistUser record);
    
    //后台管理---根据id查询当前对象信息
    BlacklistUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BlacklistUser record);
    
    //后台管理---更新保存功能
    int updateByPrimaryKey(BlacklistUser record);
    
    //后台管理---查询列表
    List<BlacklistUser> queryAll(Integer companyId,String name,String phone,String idcard);
    
    //后台管理---更新假删除状态
    int upaFalseDel(Integer id);
}