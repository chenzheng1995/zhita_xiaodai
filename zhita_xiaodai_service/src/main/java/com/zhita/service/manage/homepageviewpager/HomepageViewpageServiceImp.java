package com.zhita.service.manage.homepageviewpager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhita.dao.manage.HomepageViewpagerMapper;
import com.zhita.dao.manage.SysUserMapper;
import com.zhita.model.manage.Company;
import com.zhita.model.manage.HomepageViewpager;
import com.zhita.util.Timestamps;

@Service
public class HomepageViewpageServiceImp implements IntHomepageViewpageService{
	@Autowired
	private HomepageViewpagerMapper homepageViewpagerMapper;
	@Autowired
	private SysUserMapper sysUserMapper;
	
	//后台管理----查询首页轮播图所有信息
    public List<HomepageViewpager> queryAll(Integer companyId){
    	List<HomepageViewpager> list=homepageViewpagerMapper.queryAll(companyId);
    	for (int i = 0; i < list.size(); i++) {
			list.get(i).setUpdatetime(Timestamps.stampToDate(list.get(i).getUpdatetime()));
		}
    	return list;
    }
    
    //后台管理---添加功能
    public int insert(HomepageViewpager record) {
	     return homepageViewpagerMapper.insert(record);
    }
    
    //后台管理---添加功能（查询出所有公司）
    public List<Company> queryAllCompany(){
    	List<Company> list=sysUserMapper.queryAllCompany();
    	return list;
    }
    
    //后台管理---编辑功能（根据id查询当前对象信息）
    public HomepageViewpager selectByPrimaryKey(Integer id){
    	HomepageViewpager homepageViewpager=homepageViewpagerMapper.selectByPrimaryKey(id);
    	return homepageViewpager;
    }
    
    //后台管理---编辑功能
    public int updateByPrimaryKey(HomepageViewpager record){
    	record.setUpdatetime(System.currentTimeMillis()+"");//获取当前时间戳
    	int num=homepageViewpagerMapper.updateByPrimaryKey(record);
    	return num;
    }
    
    //后台管理---修改当前对象假删除状态
    public int updateFalDel(Integer id){
    	int num=homepageViewpagerMapper.updateFalDel(id);
    	return num;
    }

}
