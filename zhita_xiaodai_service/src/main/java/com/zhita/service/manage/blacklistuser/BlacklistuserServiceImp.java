package com.zhita.service.manage.blacklistuser;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.zhita.dao.manage.BlacklistUserMapper;
import com.zhita.dao.manage.SysUserMapper;
import com.zhita.model.manage.BlacklistUser;
import com.zhita.model.manage.Company;
import com.zhita.util.ExcelUtils;
import com.zhita.util.ListPageUtil;
import com.zhita.util.PageUtil;
import com.zhita.util.Timestamps;

@Service
public class BlacklistuserServiceImp implements IntBlacklistuserService{
	@Autowired
	private BlacklistUserMapper blacklistUserMapper;
	@Autowired
	private SysUserMapper syUserMapper;
	
	//后台管理---查询列表
    public Map<String, Object> queryAll(Integer page,Integer companyId,String name,String phone,String idcard){
     	List<BlacklistUser> list=new ArrayList<>();
    	List<BlacklistUser> listto=new ArrayList<>();
    	PageUtil pageUtil=null;
    	list=blacklistUserMapper.queryAll(companyId, name, phone, idcard);
    	
    	
    	if(list!=null && !list.isEmpty()){
    		ListPageUtil listPageUtil=new ListPageUtil(list,page,10);
    		listto.addAll(listPageUtil.getData());
    		
    		for (int i = 0; i < listto.size(); i++) {
    			listto.get(i).setOperationtime(Timestamps.stampToDate(listto.get(i).getOperationtime()));
    		}
    		
    		pageUtil=new PageUtil(listPageUtil.getCurrentPage(), listPageUtil.getPageSize(),listPageUtil.getTotalCount());
    	}else{
    		pageUtil=new PageUtil(1, 10, 0);
    	}
    	
		HashMap<String,Object> map=new HashMap<>();
		map.put("blackuserlist", listto);
		map.put("pageutil", pageUtil);
    	return map;
    }
    
    //后台管理——添加功能（先查询出所有公司）
    public List<Company> queryAllCompany(){
    	List<Company> list=syUserMapper.queryAllCompany();
    	return list;
    }
    
    //后台管理---添加操作
    public int insert(BlacklistUser record){
    	record.setOperationtime(System.currentTimeMillis()+"");//获取当前时间戳
    	int num=blacklistUserMapper.insert(record);
    	return num;
    }
    
    //后台管理---根据id查询当前对象信息
    public BlacklistUser selectByPrimaryKey(Integer id){
    	BlacklistUser blacklistUser=blacklistUserMapper.selectByPrimaryKey(id);
    	return blacklistUser;
    }
    
    //后台管理---更新保存功能
    public int updateByPrimaryKey(BlacklistUser record){
    	record.setOperationtime(System.currentTimeMillis()+"");//获取当前时间戳
    	int num=blacklistUserMapper.updateByPrimaryKey(record);
    	return num;
    }
    
    //后台管理---更新假删除状态
    public int upaFalseDel(Integer id){
    	int num=blacklistUserMapper.upaFalseDel(id);
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
	            BlacklistUser vo = new BlacklistUser();
	            BlacklistUser j = null;
	 
	            try {
	                j = blacklistUserMapper.queryByPhone(String.valueOf(lo.get(1)));//通过手机号查询表中是否有该白名单用户
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
	            	blacklistUserMapper.insert(vo);
	                System.out.println("susscess");
	            }
	            else
	            {
	            	blacklistUserMapper.updateByPhone(vo);
	            }
	        }
	 
	        return "1";
	    }
}
