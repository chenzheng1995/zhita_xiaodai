package com.zhita.service.manage.homepageviewpager;

import java.util.List;

import com.zhita.model.manage.Company;
import com.zhita.model.manage.HomepageViewpager;

public interface IntHomepageViewpageService {
	
    //后台管理----查询首页轮播图所有信息
    public List<HomepageViewpager> queryAll(Integer companyId);
    
    //后台管理---添加功能
    public int insert(HomepageViewpager record);
    
    //后台管理---添加功能（查询出所有公司）
    public List<Company> queryAllCompany();
    
    //后台管理---编辑功能（根据id查询当前对象信息）
    public HomepageViewpager selectByPrimaryKey(Integer id);
    
    //后台管理---编辑功能
    public int updateByPrimaryKey(HomepageViewpager record);
    
    //后台管理---修改当前对象假删除状态
    public int updateFalDel(Integer id,Integer sort);

	public List<HomepageViewpager> gethomepageViewpager(int companyId);
	
	//后台管理---通过id修改排序字段
	public int upasort(Integer id,Integer sort);

}
