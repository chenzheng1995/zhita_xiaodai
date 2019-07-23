package com.zhita.service.manage.deferredsettings;

import java.util.List;
import java.util.Map;

import com.zhita.model.manage.DeferredSettings;

public interface IntDeferredsetService {
	
    //后台管理---查询延期设置表所有信息
    public List<DeferredSettings> queryAll(Integer companyId);
    
    //后台管理---查询借款信息表的产品id和产品名称
    public Map<String,Object> queryAllBorrow(Integer companyId);
    
    //后台管理---添加功能
    public int insert(DeferredSettings record);
    
    //后台管理---根据当前id查询出当前对象信息
    public DeferredSettings selectByPrimaryKey(Integer id);
    
    //后台管理---修改保存功能
    public int updateByPrimaryKey(DeferredSettings record);

	public Map<String, Object> getDeferredset(int companyId);
}
