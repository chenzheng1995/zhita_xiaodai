package com.zhita.dao.manage;

import java.util.List;

import org.apache.ibatis.annotations.Param;

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
    List<BlacklistUser> queryAll(@Param("companyId") Integer companyId,@Param("name") String name,@Param("phone") String phone,@Param("idcard") String idcard,@Param("blackType") String blackType);
    
    //后台管理---更新假删除状态
    int upaFalseDel(Integer id);
    
    /**
     * 批量插入
     * @param list
     */
	void insertInfoBatch(List<BlacklistUser> list);
	/**
	 * 批量插入，通过手机号查询数据库表中是否有该黑名单用户
	 * @param phone
	 * @return
	 */
	BlacklistUser queryByPhone(String phone);
	
	/**
	 * 批量插入，通过手机号更新信息
	 * @param record
	 * @return
	 */
    int updateByPhone(BlacklistUser record);
}