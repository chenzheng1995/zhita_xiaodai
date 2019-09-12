package com.zhita.dao.manage;

import java.util.List;

import com.zhita.model.manage.Maillist;

public interface MaillistMapper {

    int deleteByPrimaryKey(Integer id);
    
    //后台管理---判断该用户在通讯录表是否有记录
    int querycount(Integer userid);
    
    //后台管理---通过用户手机号查找用户的id
    int queryuserid(String phone);
    
    //后台管理---添加功能
    int insert(Maillist record);
    
    
    //后台管理---根据用户id查询通讯录信息
    List<Maillist> queryByUserid(Integer userid);
    
    int insertSelective(Maillist record);


    Maillist selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Maillist record);

    int updateByPrimaryKey(Maillist record);
}