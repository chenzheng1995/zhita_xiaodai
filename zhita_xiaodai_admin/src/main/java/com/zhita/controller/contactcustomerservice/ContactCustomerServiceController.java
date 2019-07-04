package com.zhita.controller.contactcustomerservice;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.zhita.model.manage.Company;
import com.zhita.model.manage.ContactCustomerService;
import com.zhita.service.manage.contactcustomer.IntContactcustomerService;

@Controller
@RequestMapping("/contact")
public class ContactCustomerServiceController {
	@Autowired
	private IntContactcustomerService intContactcustomerService;
	
	//后台管理---查询联系客服表所有信息
	@ResponseBody
	@RequestMapping("/queryAll")
    public List<ContactCustomerService> queryAll(Integer companyId){
    	List<ContactCustomerService> list=intContactcustomerService.queryAll(companyId);
    	return list;
    }
	
    //后台管理---添加功能（查询出所有公司）
	@ResponseBody
	@RequestMapping("/queryAllCompany")
    public List<Company> queryAllCompany(){
		List<Company> list=intContactcustomerService.queryAllCompany();
		return list;
	}
	
	 //后台管理---添加功能
	@ResponseBody
	@RequestMapping("insert")
    public Map<String, Object> insert(ContactCustomerService record,MultipartFile file,MultipartFile file1) throws Exception{
		Map<String, Object> map=intContactcustomerService.insert(record, file, file1);
		return map;
    }
	
	 //后台管理---根据id查询出当前对象信息
	@ResponseBody
	@RequestMapping("/selectByPrimaryKey")
    public ContactCustomerService selectByPrimaryKey(Integer id){
    	ContactCustomerService contactCustomerService=intContactcustomerService.selectByPrimaryKey(id);
    	return contactCustomerService;
    }
	
	//后台管理---修改功能
	@ResponseBody
	@RequestMapping("/updateByPrimaryKey")
    public Map<String, Object> updateByPrimaryKey(ContactCustomerService record,MultipartFile file,MultipartFile file1) throws Exception{
    	Map<String, Object> map=intContactcustomerService.updateByPrimaryKey(record, file, file1);
    	return map;
    }
}
