package com.zhita.service.manage.homepageviewpager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public int insert(HomepageViewpager record) {
    	int count=homepageViewpagerMapper.queryAllCount(record.getCompanyid());
    	record.setSort(count+1);
    	record.setUpdatetime(System.currentTimeMillis()+"");//获取当前时间戳
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
    @Transactional
    public int updateByPrimaryKey(HomepageViewpager record){
    	record.setUpdatetime(System.currentTimeMillis()+"");//获取当前时间戳
    	int num=homepageViewpagerMapper.updateByPrimaryKey(record);
    	return num;
    }
    
    //后台管理---修改当前对象假删除状态
    @Transactional
    public int updateFalDel(Integer id,Integer sort){
    	int num=homepageViewpagerMapper.updateFalDel(id);
    	homepageViewpagerMapper.upadateSort(sort);
    	return num;
    }

	@Override
	public List<HomepageViewpager> gethomepageViewpager(int companyId) {
		List<HomepageViewpager> list=homepageViewpagerMapper.gethomepageViewpager(companyId); //获取轮播图的所有数据   	
		return list;
	}
	
	//后台管理---通过id修改排序字段
	@Transactional
	public int upasort(Integer id,Integer sort){
		Integer lastid=homepageViewpagerMapper.selidbysort(sort-1);
		homepageViewpagerMapper.upasort(id, sort-1);
		homepageViewpagerMapper.upasort(lastid, sort);
		return 1;
	}

}
