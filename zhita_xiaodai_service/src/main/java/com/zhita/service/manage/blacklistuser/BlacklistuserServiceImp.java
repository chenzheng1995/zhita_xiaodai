package com.zhita.service.manage.blacklistuser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhita.dao.manage.BlacklistUserMapper;
import com.zhita.dao.manage.SysUserMapper;
import com.zhita.model.manage.BlacklistUser;
import com.zhita.model.manage.Company;
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
    	
    	for (int i = 0; i < list.size(); i++) {
    		list.get(i).setOperationtime(Timestamps.stampToDate(list.get(i).getOperationtime()));
		}
    	
    	if(list!=null && !list.isEmpty()){
    		ListPageUtil listPageUtil=new ListPageUtil(list,page,2);
    		listto.addAll(listPageUtil.getData());
    		
    		pageUtil=new PageUtil(listPageUtil.getCurrentPage(), listPageUtil.getPageSize(),listPageUtil.getTotalCount());
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
}
