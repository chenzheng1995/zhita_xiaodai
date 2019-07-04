package com.zhita.controller.homepageviewpager;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.zhita.model.manage.Company;
import com.zhita.model.manage.HomepageViewpager;
import com.zhita.service.manage.homepageviewpager.IntHomepageViewpageService;

@Controller
@RequestMapping("/homepage")
public class HomepageViewpagerController {
	@Autowired
	private IntHomepageViewpageService intHomepageViewpageService;
	
	//后台管理----查询首页轮播图所有信息
	@ResponseBody
	@RequestMapping("/queryAll")
    public List<HomepageViewpager> queryAll(Integer companyId){
    	List<HomepageViewpager> list=intHomepageViewpageService.queryAll(companyId);
    	return list;
    }
	
	//后台管理---添加功能（查询出所有公司）
	@ResponseBody
	@RequestMapping("/queryAllCompany")
    public List<Company> queryAllCompany(){
		List<Company> list=intHomepageViewpageService.queryAllCompany();
		return list;
	}
	
	//后台管理---添加功能
	@ResponseBody
	@RequestMapping("/insert")
    public Map<String, Object> insert(HomepageViewpager record,MultipartFile file) throws Exception{
    	Map<String, Object> map=intHomepageViewpageService.insert(record,file);
    	return map;
    }
    
    //后台管理---编辑功能（根据id查询当前对象信息）
	@ResponseBody
	@RequestMapping("/selectByPrimaryKey")
    public HomepageViewpager selectByPrimaryKey(Integer id){
    	HomepageViewpager homepageViewpager=intHomepageViewpageService.selectByPrimaryKey(id);
    	return homepageViewpager;
    }
    
    //后台管理---编辑功能
	@ResponseBody
	@RequestMapping("/updateByPrimaryKey")
    public Map<String, Object> updateByPrimaryKey(HomepageViewpager record,MultipartFile file)throws Exception{
		Map<String, Object> map=intHomepageViewpageService.updateByPrimaryKey(record,file);
    	return map;
    }
	
	//后台管理---修改当前对象假删除状态
	@ResponseBody
	@RequestMapping("/updateFalDel")
    public int updateFalDel(Integer id){
    	int num=intHomepageViewpageService.updateFalDel(id);
    	return num;
    }
}
