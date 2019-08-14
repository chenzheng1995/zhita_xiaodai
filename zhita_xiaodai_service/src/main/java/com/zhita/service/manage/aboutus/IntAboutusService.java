package com.zhita.service.manage.aboutus;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.zhita.model.manage.Aboutus;
import com.zhita.model.manage.Company;

public interface IntAboutusService {
	//后台管理---查询所有
    public List<Aboutus> queryAll(Integer companyId);
    
    //后台管理---添加功能（查询出所有公司）
    public List<Company> queryAllCompany();
    
    //后台管理---添加功能
    public Map<String, Object> insert(Aboutus record,MultipartFile file) throws Exception;
    
    //后台管理---根据id查询当前对象信息
    public Aboutus selectByPrimaryKey(Integer id);
    
    //后台管理---更新功能
    public int updateByPrimaryKey(Aboutus record);

	public Map<String, Object> getaboutus(int companyId);
}
