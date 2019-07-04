package com.zhita.controller.autheninfor;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.zhita.model.manage.AuthenticationInformation;
import com.zhita.model.manage.Company;
import com.zhita.service.manage.autheninfor.IntAutheninforService;

@Controller
@RequestMapping("/autheninfor")
public class AutheninforController {
	@Autowired
	private IntAutheninforService intAutheninforService;
	
	//后台管理---查询认证信息表所有信息
	@ResponseBody
	@RequestMapping("/queryAll")
    public List<AuthenticationInformation> queryAll(Integer companyId){
    	List<AuthenticationInformation> list=intAutheninforService.queryAll(companyId);
    	return list;
    }
	
	//后台管理---添加功能（查询出所有公司）
	@ResponseBody
	@RequestMapping("/queryAllCompany")
    public List<Company> queryAllCompany(){
    	List<Company> list=intAutheninforService.queryAllCompany();
    	return list;
    }
	
	//后台管理---添加功能
	@ResponseBody
	@RequestMapping("/insert")
    public Map<String, Object> insert(AuthenticationInformation record,MultipartFile file) throws Exception{
		Map<String, Object> map=intAutheninforService.insert(record,file);
    	return map;
    }
	
	//后台管理---根据主键id查询出当前对象信息
	@ResponseBody
	@RequestMapping("/selectByPrimaryKey")
    public AuthenticationInformation selectByPrimaryKey(Integer id){
    	AuthenticationInformation authenticationInformation=intAutheninforService.selectByPrimaryKey(id);
    	return authenticationInformation;
    }

    //后台管理---编辑功能
	@ResponseBody
	@RequestMapping("/updateByPrimaryKey")
    public Map<String, Object> updateByPrimaryKey(AuthenticationInformation record,MultipartFile file)throws Exception{
    	Map<String, Object> map=intAutheninforService.updateByPrimaryKey(record, file);
    	return map;
    }
}
