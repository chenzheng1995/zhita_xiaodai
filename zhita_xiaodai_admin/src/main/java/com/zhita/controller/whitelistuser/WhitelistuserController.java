package com.zhita.controller.whitelistuser;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.zhita.model.manage.Company;
import com.zhita.model.manage.WhitelistUser;
import com.zhita.service.manage.whitelistuser.IntWhitelistuserService;

@Controller
@RequestMapping("/whitelistuser")
public class WhitelistuserController {
	@Autowired
	private IntWhitelistuserService intWhitelistuserService;
	
	//后台管理---查询列表
	@ResponseBody
	@RequestMapping("/queryAll")
    public Map<String, Object>  queryAll(Integer page,Integer companyId,String name,String phone,String idcard){
    	Map<String, Object>  map=intWhitelistuserService.queryAll(page,companyId, name, phone, idcard);
    	return map;
    }
	
	//后台管理——添加功能（先查询出所有公司）
	@ResponseBody
	@RequestMapping("/queryAllCompany")
    public List<Company> queryAllCompany(){
    	List<Company> list=intWhitelistuserService.queryAllCompany();
    	return list;
    }
    
    //后台管理---添加操作
	@ResponseBody
	@RequestMapping("/insert")
    public int insert(WhitelistUser record){
    	int num=intWhitelistuserService.insert(record);
    	return num;
    }
	
	//后台管理---根据id查询当前用户的信息
	@ResponseBody
	@RequestMapping("/selectByPrimaryKey")
    public WhitelistUser selectByPrimaryKey(Integer id){
    	WhitelistUser whitelistUser=intWhitelistuserService.selectByPrimaryKey(id);
    	return whitelistUser;
    }
	
	 //后台管理---更新保存功能
	@ResponseBody
	@RequestMapping("/updateByPrimaryKey")
    public int updateByPrimaryKey(WhitelistUser record){
    	int num=intWhitelistuserService.updateByPrimaryKey(record);
    	return num;
    }
	
	//后台管理---更新假删除状态
	@ResponseBody
	@RequestMapping("/upaFalseDel")
    public int upaFalseDel(Integer id){
    	int num=intWhitelistuserService.upaFalseDel(id);
    	return num;
    }
	
	/**
	 * 批量导入Excel
	 * @param excelFile
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/importExc")
	public String importExc(@RequestParam(value="excelFile",required = false) MultipartFile excelFile,Integer companyId,Integer operator) {
	    try {
	        String s = intWhitelistuserService.ajaxUploadExcel(excelFile,companyId,operator);
	        return s;
	    } catch (Exception e) {
	        e.printStackTrace();
	        success(false);
	    }
	    return "0";

	}

	private void success(boolean b) {
		// TODO Auto-generated method stub
		
	}

}