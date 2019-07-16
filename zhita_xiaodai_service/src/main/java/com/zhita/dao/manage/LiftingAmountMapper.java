package com.zhita.dao.manage;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.zhita.model.manage.LiftingAmount;

public interface LiftingAmountMapper {
    int deleteByPrimaryKey(Integer id);
    
    //后台管理---添加功能
    int insert(LiftingAmount record);

    int insertSelective(LiftingAmount record);
    
    //后台管理---根据当前id查询当前对象信息
    LiftingAmount selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LiftingAmount record);
    
    //后台管理---更新保存
    int updateByPrimaryKey(LiftingAmount record);
    
    //后台管理---查询续借提额表所有信息
    List<LiftingAmount> queryAll(Integer comapnyId);
    
    //后台管理---修改当前对象的假删除状态
    int upaFalseDel(String operator,String operationTime,Integer id);

	ArrayList<LiftingAmount> getintLiftingamount(int companyId);

	int getIncreaseThequota(@Param("num")int num,@Param("companyId") int companyId);

	Integer getFirstline(int companyId);
}