package com.zhita.service.manage.whitelistuser;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.zhita.model.manage.Company;
import com.zhita.model.manage.WhitelistUser;

public interface IntWhitelistuserService {
	//后台管理---查询列表
    public Map<String, Object> queryAll(Integer page,Integer companyId,String name,String phone,String idcard);
    
    //后台管理——添加功能（先查询出所有公司）
    public List<Company> queryAllCompany();
    
    //后台管理---添加操作
    public int insert(WhitelistUser record);
    
    //后台管理---根据id查询当前用户的信息
    public WhitelistUser selectByPrimaryKey(Integer id);
    
    //后台管理---更新保存功能
    public int updateByPrimaryKey(WhitelistUser record);
    
    //后台管理---更新假删除状态
    public int upaFalseDel(Integer id);
    
    /**
     * 批量导入Excel
     * @param excelFile
     * @return
     */
    String ajaxUploadExcel(MultipartFile excelFile,Integer companyId,Integer operator);

	public int getWhitelistuser(String newphone, String idcard_number);

}
