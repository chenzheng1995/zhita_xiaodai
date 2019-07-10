package com.zhita.service.manage.contactcustomer;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.zhita.model.manage.Company;
import com.zhita.model.manage.ContactCustomerService;

public interface IntContactcustomerService {
	//后台管理---查询联系客服表所有信息
    public List<ContactCustomerService> queryAll(Integer companyId);
    
    //后台管理---添加功能（查询出所有公司）
    public List<Company> queryAllCompany();
    
    //后台管理---添加功能
    public Map<String, Object> insert(ContactCustomerService record,MultipartFile file,MultipartFile file1)throws Exception;
    
    //后台管理---根据id查询出当前对象信息
    public ContactCustomerService selectByPrimaryKey(Integer id);
    
    //后台管理---修改功能
    public int updateByPrimaryKey(ContactCustomerService record);
    
}
