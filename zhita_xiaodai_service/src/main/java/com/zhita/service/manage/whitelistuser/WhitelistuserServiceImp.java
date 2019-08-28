package com.zhita.service.manage.whitelistuser;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.zhita.dao.manage.SysUserMapper;
import com.zhita.dao.manage.WhitelistUserMapper;
import com.zhita.model.manage.Company;
import com.zhita.model.manage.WhitelistUser;
import com.zhita.util.ExcelUtils;
import com.zhita.util.PageUtil2;
import com.zhita.util.Timestamps;

@Service
public class WhitelistuserServiceImp implements IntWhitelistuserService{
	@Autowired
	private WhitelistUserMapper whitelistUserMapper;
	@Autowired
	private SysUserMapper sysUserMapper;
	
	//后台管理---查询列表
    public Map<String, Object>  queryAll(Integer page,Integer companyId,String name,String phone,String idcard){
    	List<WhitelistUser> list=new ArrayList<WhitelistUser>();
    	List<WhitelistUser> listto=new ArrayList<WhitelistUser>();
    	PageUtil2 pageUtil=null;
    	
    	for (int i = 0; i < list.size(); i++) {
    		list.get(i).setOperationtime(Timestamps.stampToDate(list.get(i).getOperationtime()));
		}
    	
    	
		int totalCount=whitelistUserMapper.queryAllcount(companyId, name, phone, idcard);//查询总数量
		pageUtil=new PageUtil2(page,totalCount);
    	if(page<1) {
    		page=1;
    		pageUtil.setPage(page);
    	}
    	else if(page>pageUtil.getTotalPageCount()) {
    		if(totalCount==0) {
    			page=pageUtil.getTotalPageCount()+1;
    		}else {
    			page=pageUtil.getTotalPageCount();
    		}
    		pageUtil.setPage(page);
    	}
    	int pages=(page-1)*pageUtil.getPageSize();
    	list=whitelistUserMapper.queryAll(companyId, pages, pageUtil.getPageSize(), name, phone, idcard);//查询list集合
    	for (int i = 0; i < list.size();i++) {
			list.get(i).setOperationtime(Timestamps.stampToDate(list.get(i).getOperationtime()));
		}
    	
		Map<String,Object> map=new HashMap<>();
		map.put("whituserlist", list);
		map.put("pageutil", pageUtil);
    	return map;
    }
    
    //后台管理——添加功能（先查询出所有公司）
    public List<Company> queryAllCompany(){
    	List<Company> list=sysUserMapper.queryAllCompany();
    	return list;
    }
    
    //后台管理---添加操作
    @Transactional
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
    @Transactional
    public int updateByPrimaryKey(WhitelistUser record){
    	record.setOperationtime(System.currentTimeMillis()+"");//获取当前时间戳
    	int num=whitelistUserMapper.updateByPrimaryKey(record);
    	return num;
    }
    
    //后台管理---更新假删除状态
    @Transactional
    public int upaFalseDel(Integer id){
    	int num=whitelistUserMapper.upaFalseDel(id);
    	return num;
    }
    
    /**
     * 批量导入Excel
     */
    @Transactional
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

	@Override
	public int getWhitelistuser(String newphone, String idcard_number) {
		int number = whitelistUserMapper.getWhitelistuser(newphone,idcard_number);
		return number;
	}

	@Override
	public int getWhitelistuser1(String phone, String idcard_number, String name) {
		int num =  whitelistUserMapper.getWhitelistuser1(phone,idcard_number,name);
		return num;
	}

}

