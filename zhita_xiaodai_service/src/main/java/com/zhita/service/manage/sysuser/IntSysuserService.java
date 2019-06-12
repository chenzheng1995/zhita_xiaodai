package com.zhita.service.manage.sysuser;

import java.util.Map;

import com.zhita.model.manage.SysUser;

public interface IntSysuserService {
	//admin------系统用户——列表展示
    public Map<String, Object> queryAll(Integer page);
    
    //admin------系统用户——模糊查询（账号和账号状态）
    public Map<String, Object> queryAllByLike(String account,String status,Integer page);
    
    //admin-----系统用户——添加功能（先查询出所有公司和角色）
    public Map<String, Object> queryAllCompany();
    
    //admin------系统用户——添加功能
    public int insert(SysUser record);
    
    //admin-----系统用户——修改账号状态
    public int updateStatus(Integer id,String status);
    
    //admin----系统用户——编辑功能（通过主键id查询对象）
    public Map<String, Object> selectByPrimaryKey(Integer userid);
    
    //admin----系统用户——编辑功能（修改保存）
    public int updateByPrimaryKey(SysUser record);
}
