package com.zhita.controller.blacklistuser;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhita.model.manage.BlacklistUser;
import com.zhita.model.manage.Company;
import com.zhita.service.manage.blacklistuser.IntBlacklistuserService;

@Controller
@RequestMapping("/blacklistuser")
public class BlacklistuserController {
	@Autowired
	private IntBlacklistuserService intBlacklistuserService;
	
	//后台管理---查询列表
	@ResponseBody
	@RequestMapping("/queryAll")
    public Map<String, Object> queryAll(Integer page,Integer companyId,String name,String phone,String idcard){
    	Map<String, Object> map=intBlacklistuserService.queryAll(page, companyId, name, phone, idcard);
    	return map;
    }
	
	//后台管理——添加功能（先查询出所有公司）
	@ResponseBody
	@RequestMapping("/queryAllCompany")
	public List<Company> queryAllCompany(){
	    List<Company> list=intBlacklistuserService.queryAllCompany();
	    return list;
	}
	
	//后台管理---添加操作
	@ResponseBody
	@RequestMapping("/insert")
    public int insert(BlacklistUser record){
    	int num=intBlacklistuserService.insert(record);
    	return num;
    }
	
	//后台管理---根据id查询当前对象信息
	@ResponseBody
	@RequestMapping("/selectByPrimaryKey")
    public BlacklistUser selectByPrimaryKey(Integer id){
    	BlacklistUser blacklistUser=intBlacklistuserService.selectByPrimaryKey(id);
    	return blacklistUser;
    }
	
	//后台管理---更新保存功能
	@ResponseBody
	@RequestMapping("/updateByPrimaryKey")
    public int updateByPrimaryKey(BlacklistUser record){
    	int num=intBlacklistuserService.updateByPrimaryKey(record);
    	return num;
    }
	
	//后台管理---更新假删除状态
	@ResponseBody
	@RequestMapping("/upaFalseDel")
    public int upaFalseDel(Integer id){
    	int num=intBlacklistuserService.upaFalseDel(id);
    	return num;
    }
}