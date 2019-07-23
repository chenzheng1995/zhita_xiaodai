package com.zhita.dao.manage;

import java.util.List;
import java.util.Map;

import com.zhita.model.manage.BorrowMoneyMessage;
import com.zhita.model.manage.DeferredSettings;

public interface DeferredSettingsMapper {
    int deleteByPrimaryKey(Integer id);
    
    //后台管理---添加功能
    int insert(DeferredSettings record);

    int insertSelective(DeferredSettings record);
    
    //后台管理---根据当前id查询出当前对象信息
    DeferredSettings selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DeferredSettings record);
    
    //后台管理---修改保存功能
    int updateByPrimaryKey(DeferredSettings record);
    
    //后台管理---查询延期设置表所有信息
    List<DeferredSettings> queryAll(Integer companyId);
    
    //后台管理---查询借款信息表的产品id和产品名称
    List<BorrowMoneyMessage> queryAllBorrow(Integer companyId);
    
    //后台管理---根据id查询信息（借款期限   平台服务费比率）
    BorrowMoneyMessage queryBorrow(Integer productid);

	Map<String, Object> getDeferredset(int companyId);
    
}