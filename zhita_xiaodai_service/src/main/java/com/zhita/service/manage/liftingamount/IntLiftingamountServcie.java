package com.zhita.service.manage.liftingamount;

import java.util.List;

import com.zhita.model.manage.LiftingAmount;

public interface IntLiftingamountServcie {
	//后台管理---查询续借提额表所有信息
    public List<LiftingAmount> queryAll(Integer comapnyId);
    
    //后台管理---添加功能
    public int insert(LiftingAmount record);
    
    //后台管理---根据当前id查询当前对象信息
    public  LiftingAmount selectByPrimaryKey(Integer id);
    
    //后台管理---更新保存
    public int updateByPrimaryKey(LiftingAmount record);
    
    //后台管理---修改当前对象的假删除状态
    public int upaFalseDel(String operator,Integer id);
}
