package com.zhita.dao.manage;

import java.util.List;

import org.apache.ibatis.annotations.Param;

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
    List<WhitelistUser> queryAll(@Param("companyId") Integer companyId,@Param("name") String name,@Param("phone") String phone,@Param("idcard") String idcard);
    
    //后台管理---更新假删除状态
    int upaFalseDel(Integer id);
}