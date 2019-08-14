package com.zhita.dao.manage;

import java.util.List;
import java.util.Map;

import com.zhita.model.manage.ContactCustomerService;

public interface ContactCustomerServiceMapper {
    int deleteByPrimaryKey(Integer id);
    
    //后台管理---添加功能
    int insert(ContactCustomerService record);

    int insertSelective(ContactCustomerService record);
    
    //后台管理---根据id查询出当前对象信息
    ContactCustomerService selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ContactCustomerService record);
    
    //后台管理---修改功能
    int updateByPrimaryKey(ContactCustomerService record);
    
    //后台管理---查询联系客服表所有信息
    List<ContactCustomerService> queryAll(Integer companyId);
    
    //后台管理---根据id查询出当前对象的宣传图字段
    String queryAdvertisingmap(Integer id);
    
    //后台管理---根据id查询出当前对象的二维码字段
    String queryQrcode(Integer id);

	Map<String, Object> getContactCustomer(int companyId);
    
}