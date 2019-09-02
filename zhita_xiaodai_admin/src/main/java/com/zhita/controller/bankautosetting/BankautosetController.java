package com.zhita.controller.bankautosetting;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhita.model.manage.Bankauto;
import com.zhita.model.manage.Company;
import com.zhita.service.manage.bankautosetting.IntBankautosetService;

@Controller
@RequestMapping("/bankauto")
public class BankautosetController {
	@Autowired
	private IntBankautosetService intBankautosetService;
	
	//后台管理----查询所有
	@ResponseBody
	@RequestMapping("/queryAll")
    public Map<String,Object> queryAll(Integer companyId,Integer page){
		Map<String,Object> map=intBankautosetService.queryAll(companyId,page);
    	return map;
    }
	
	//后台管理-----添加功能（先查询出所有公司）
	@ResponseBody
	@RequestMapping("/queryAllCompany")
    public List<Company> queryAllCompany(){
    	List<Company> list=intBankautosetService .queryAllCompany();
    	return list;
    }

    //后台管理----添加功能
	@ResponseBody
	@RequestMapping("/insert")
    public int insert(Bankauto record){
    	int num=intBankautosetService.insert(record);
    	return num;
    }
	
	//后台管理----根据id查询当前对象
	@ResponseBody
	@RequestMapping("/selectByPrimaryKey")
    public Bankauto selectByPrimaryKey(Integer id){
    	Bankauto bankauto=intBankautosetService.selectByPrimaryKey(id);
    	return bankauto;
    }
    
    //后台管理----更新修改
	@ResponseBody
	@RequestMapping("/updateByPrimaryKey")
    public int updateByPrimaryKey(Bankauto record){
    	int num=intBankautosetService.updateByPrimaryKey(record);
    	return num;
    }
    
    //后台管理----修改假删除状态
	@ResponseBody
	@RequestMapping("/upaFaldel")
    public int upaFaldel(Integer id){
    	int num=intBankautosetService.upaFaldel(id);
    	return num;
    }
}
