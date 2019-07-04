package com.zhita.controller.aboutus;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.zhita.model.manage.Aboutus;
import com.zhita.model.manage.Company;
import com.zhita.service.manage.aboutus.IntAboutusService;

@Controller
@RequestMapping("/aboutus")
public class AboutusController {
	@Autowired
	private IntAboutusService intAboutusService;
	
	//后台管理---查询所有
	@ResponseBody
	@RequestMapping("/queryAll")
    public List<Aboutus> queryAll(Integer companyId){
    	List<Aboutus> list=intAboutusService.queryAll(companyId);
    	return list;
    }
	
	 //后台管理---添加功能（查询出所有公司）
	@ResponseBody
	@RequestMapping("/queryAllCompany")
    public List<Company> queryAllCompany(){
    	List<Company> list=intAboutusService.queryAllCompany();
    	return list;
    }
	
	//后台管理---添加功能
	@ResponseBody
	@RequestMapping("/insert")
    public Map<String, Object> insert(Aboutus record,MultipartFile file) throws Exception{
		Map<String, Object> map=intAboutusService.insert(record, file);
		return map;
    }
	
	 //后台管理---根据id查询当前对象信息
	@ResponseBody
	@RequestMapping("/selectByPrimaryKey")
    public Aboutus selectByPrimaryKey(Integer id){
    	Aboutus aboutus=intAboutusService.selectByPrimaryKey(id);
    	return aboutus;
    }
	
	 
    //后台管理---更新功能
	@ResponseBody
	@RequestMapping("/updateByPrimaryKey")
    public Map<String, Object> updateByPrimaryKey(Aboutus record,MultipartFile file) throws Exception{
    	Map<String, Object> map=intAboutusService.updateByPrimaryKey(record, file);
    	return map;
    }
}
