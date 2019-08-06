package com.zhita.service.manage.sysuser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhita.dao.manage.SysUserMapper;
import com.zhita.model.manage.Company;
import com.zhita.model.manage.Role;
import com.zhita.model.manage.SysUser;
import com.zhita.util.ListPageUtil;
import com.zhita.util.PageUtil2;
import com.zhita.util.Timestamps;

@Service
public class SysuserServiceImp implements IntSysuserService{
	@Autowired
	private SysUserMapper sysUserMapper;
	
	//admin------系统用户——列表展示
	@Override
    public Map<String, Object> queryAll(Integer companyId,Integer page){
		List<SysUser> list=new ArrayList<>();
		List<SysUser> listto=new ArrayList<>();
		PageUtil2 pageUtil=null;
    
    	list=sysUserMapper.queryAll(companyId);
    	
    	for (int i = 0; i < list.size(); i++) {
			list.get(i).setLogintime(Timestamps.stampToDate(list.get(i).getLogintime()));
		}
    	
    	if(list!=null && !list.isEmpty()){
    		ListPageUtil listPageUtil=new ListPageUtil(list,page,10);
    		listto.addAll(listPageUtil.getData());
    		
    	//	pageUtil=new PageUtil(listPageUtil.getCurrentPage(), listPageUtil.getPageSize());
    		pageUtil=new PageUtil2(listPageUtil.getCurrentPage(), listPageUtil.getPageSize(),listPageUtil.getTotalCount());
    	}else{
    		pageUtil=new PageUtil2(1, 10, 0);
    	}
    	
		HashMap<String,Object> map=new HashMap<>();
		map.put("sysuserlist", listto);
		map.put("pageutil", pageUtil);
    	return map;
    }
	
	//admin------系统用户——模糊查询（账号和账号状态）
	@Override
	public Map<String, Object> queryAllByLike(Integer companyId,String account, String status, Integer page) {
		List<SysUser> list=new ArrayList<>();
		List<SysUser> listto=new ArrayList<>();
		PageUtil2 pageUtil=null;
		
		list=sysUserMapper.queryAllByLike(companyId,account, status);
		
		for (int i = 0; i < list.size(); i++) {
			list.get(i).setLogintime(Timestamps.stampToDate(list.get(i).getLogintime()));
		}
		
		if(list!=null && !list.isEmpty()){
    		ListPageUtil listPageUtil=new ListPageUtil(list,page,10);
    		listto.addAll(listPageUtil.getData());
    		
    		pageUtil=new PageUtil2(listPageUtil.getCurrentPage(), listPageUtil.getPageSize(),listPageUtil.getTotalCount());
    	}else{
    		pageUtil=new PageUtil2(1, 10, 0);
    	}
		
		HashMap<String,Object> map=new HashMap<>();
		map.put("sysuserlist", listto);
		map.put("pageutil", pageUtil);
    	return map;
	}
	
	//admin-----系统用户——添加功能（先查询出所有公司和角色）
	@Override
	public Map<String,Object> queryAllCompany() {
		List<Company> listcompany=sysUserMapper.queryAllCompany();
		List<Role> listrole=sysUserMapper.queryAllRole();
		HashMap<String,Object> map=new HashMap<>();
		map.put("listcompany", listcompany);
		map.put("listrole", listrole);
		return map;
	}
	
	//admin------系统用户——添加功能
	@Transactional
	@Override
	public int insert(SysUser record) {
		int sum=sysUserMapper.insert(record);
		
		String listRoleIdString=record.getListRoleIdString();
    	if(listRoleIdString!=null&&!"".equals(listRoleIdString)){
    		String[] strSplit=listRoleIdString.split(",");
    		for (int i = 0; i < strSplit.length; i++) {
				sysUserMapper.insertRole(record.getUserid(), Integer.parseInt(strSplit[i]));
			}
    	}
		return sum;
	}
	
	//admin-----系统用户——修改账号状态
	@Transactional
	@Override
	public int updateStatus(Integer id,String status) {
		int num=0;
		if(status.equals("1")){
			num=sysUserMapper.upaStatusOpen(id);
		}else{
			num=sysUserMapper.upaStatusClose(id);
		}
		return num;
	}
	
	//admin----系统用户——编辑功能（通过主键id查询对象）
	@Override
    public Map<String, Object> selectByPrimaryKey(Integer userid){
    	List<Company> listcompany=sysUserMapper.queryAllCompany();
		List<Role> listrole=sysUserMapper.queryAllRole();
		SysUser sysuser=sysUserMapper.selectByPrimaryKey(userid);
/*		sysuser.setLogintime(Timestamps.stampToDate(sysuser.getLogintime()));*/
		
		HashMap<String,Object> map=new HashMap<>();
		map.put("listcompany", listcompany);
		map.put("listrole", listrole);
		map.put("sysuser", sysuser);
		return map;
    }
    
    //admin----系统用户——编辑功能（修改保存）
	@Transactional
	@Override
	public int updateByPrimaryKey(SysUser record) {
		int num=sysUserMapper.updateByPrimaryKey(record);
		
		String listRoleIdString=record.getListRoleIdString();
		List<Integer> list=sysUserMapper.queryMiddleId(record.getUserid());
		if(list.size()!=0){
			if(listRoleIdString!=null&&!"".equals(listRoleIdString)){
				String[] strSplit=listRoleIdString.split(",");
	    		//进入当前  证明当前用户之前有角色  现在传进来的有角色
				for (int i = 0; i < list.size(); i++) {
					sysUserMapper.delMiddleId(list.get(i));
				}
				for (int i = 0; i < strSplit.length; i++) {
					sysUserMapper.insertRole(record.getUserid(), Integer.parseInt(strSplit[i]));
				}
	    	}else{
	    		//进入当前  证明当前用户之前有角色  现在传进来的没有角色
	    		for (int i = 0; i < list.size(); i++) {
					sysUserMapper.delMiddleId(list.get(i));
				}
	    	}
		}else{
			if(listRoleIdString!=null&&!"".equals(listRoleIdString)){
				String[] strSplit=listRoleIdString.split(",");
				//进入当前  证明当前用户之前没有角色  现在传进来的有角色
				for (int i = 0; i < strSplit.length; i++) {
					sysUserMapper.insertRole(record.getUserid(), Integer.parseInt(strSplit[i]));
				}
			}
		}
		return num;
	}
	
    //admin---系统用户——修改假删除状态
    public int upaFalseDel(Integer id){
    	int num=sysUserMapper.upaFalseDel(id);
    	return num;
    }
}
