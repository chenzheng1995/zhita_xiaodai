package com.zhita.service.manage.whitelistuser;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.zhita.dao.manage.SysUserMapper;
import com.zhita.dao.manage.WhitelistUserMapper;
import com.zhita.model.manage.Company;
import com.zhita.model.manage.WhitelistUser;
import com.zhita.util.ExcelUtils;
import com.zhita.util.ListPageUtil;
import com.zhita.util.PageUtil;
import com.zhita.util.Timestamps;

@Service
public class WhitelistuserServiceImp implements IntWhitelistuserService{
	@Autowired
	private WhitelistUserMapper whitelistUserMapper;
	@Autowired
	private SysUserMapper sysUserMapper;
	
	//后台管理---查询列表
    public Map<String, Object>  queryAll(Integer page,Integer companyId,String name,String phone,String idcard){
    	List<WhitelistUser> list=new ArrayList<>();
    	List<WhitelistUser> listto=new ArrayList<>();
    	PageUtil pageUtil=null;
    	list=whitelistUserMapper.queryAll(companyId, name, phone, idcard);
    	
    	for (int i = 0; i < list.size(); i++) {
    		list.get(i).setOperationtime(Timestamps.stampToDate(list.get(i).getOperationtime()));
		}
    	
    	if(list!=null && !list.isEmpty()){
    		ListPageUtil listPageUtil=new ListPageUtil(list,page,10);
    		listto.addAll(listPageUtil.getData());
    		
    		pageUtil=new PageUtil(listPageUtil.getCurrentPage(), listPageUtil.getPageSize(),listPageUtil.getTotalCount());
    	}else{
    		pageUtil=new PageUtil(1, 10, 0);
    	}
    	
		HashMap<String,Object> map=new HashMap<>();
		map.put("whituserlist", listto);
		map.put("pageutil", pageUtil);
    	return map;
    }
    
    //后台管理——添加功能（先查询出所有公司）
    public List<Company> queryAllCompany(){
    	List<Company> list=sysUserMapper.queryAllCompany();
    	return list;
    }
    
    //后台管理---添加操作
    public int insert(WhitelistUser record){
    	record.setOperationtime(System.currentTimeMillis()+"");//获取当前时间戳
    	int num=whitelistUserMapper.insert(record);
    	return num;
    }
 
    //后台管理---根据id查询当前用户的信息
    public WhitelistUser selectByPrimaryKey(Integer id){
    	WhitelistUser whitelistUser=whitelistUserMapper.selectByPrimaryKey(id);
    	whitelistUser.setOperationtime(Timestamps.stampToDate(whitelistUser.getOperationtime()));
    	return whitelistUser;
    }
    
    //后台管理---更新保存功能
    public int updateByPrimaryKey(WhitelistUser record){
    	record.setOperationtime(System.currentTimeMillis()+"");//获取当前时间戳
    	int num=whitelistUserMapper.updateByPrimaryKey(record);
    	return num;
    }
    
    //后台管理---更新假删除状态
    public int upaFalseDel(Integer id){
    	int num=whitelistUserMapper.upaFalseDel(id);
    	return num;
    }
    
    /**
     * 批量导入Excel
     */
    public String ajaxUploadExcel(MultipartFile file,Integer companyId,Integer operator){
	       /* MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
	        MultipartFile file = multipartRequest.getFile("excelFile");*/
	        if(file.isEmpty()){
	            try {
	                throw new Exception("文件不存在！");
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }
	 
	        InputStream in =null;
	        try {
	            in = file.getInputStream();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	 
	        List<List<Object>> listob = null;
	        try {
	            listob = new ExcelUtils().getBankListByExcel(in,file.getOriginalFilename());
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        for (int i = 0; i < listob.size(); i++) {
	            /*   List<Object> lo = listob.get(i);
	               if (lo.get(i)=="") {
	                    continue;
	                }*/
	            System.out.println(listob.get(i)+"-------");
	 
	        }
	        for (int i = 0; i < listob.size(); i++) {
	            List<Object> lo = listob.get(i);
	            WhitelistUser vo = new WhitelistUser();
	            WhitelistUser j = null;
	 
	            try {
	                j = whitelistUserMapper.queryByPhone(String.valueOf(lo.get(1)));//通过手机号查询表中是否有该白名单用户
	            } catch (NumberFormatException e) {
	                // TODO Auto-generated catch block
	                System.out.println("数据库中无该条数据，新增");
	 
	            }
	            vo.setCompanyid(companyId);
	            vo.setName(String.valueOf(lo.get(0)));
	            vo.setPhone(String.valueOf(lo.get(1)));
	            vo.setIdcard(String.valueOf(lo.get(2)));
	            vo.setOperator(operator);
	            vo.setOperationtime(System.currentTimeMillis()+"");//获取当前时间戳
	 
	            if(j == null)
	            {
	            	whitelistUserMapper.insert(vo);
	                System.out.println("susscess");
	            }
	            else
	            {
	            	whitelistUserMapper.updateByPhone(vo);
	            }
	        }
	 
	        return "1";
	    }

}

