package com.zhita.service.manage.role;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhita.dao.manage.RoleMapper;
import com.zhita.dao.manage.SysUserMapper;
import com.zhita.model.manage.Functions;
import com.zhita.model.manage.Role;
import com.zhita.util.ListPageUtil;
import com.zhita.util.PageUtil2;


@Service
public class RoleServiceImp implements IntRoleService{
	@Autowired
	private RoleMapper roleMapper;
	@Autowired
	private SysUserMapper sysUserMapper;
	
	//admin------角色——列表展示
	@Override
	public Map<String,Object> queryAll(Integer page) {
		List<Role> list=new ArrayList<>();
		List<Role> listto=new ArrayList<>();
		PageUtil2 pageUtil=null;
    
		list=roleMapper.queryAll();
    	
    	if(list!=null && !list.isEmpty()){
    		ListPageUtil listPageUtil=new ListPageUtil(list,page,10);
    		listto.addAll(listPageUtil.getData());
    		
    		pageUtil=new PageUtil2(listPageUtil.getCurrentPage(), listPageUtil.getPageSize(),listPageUtil.getTotalCount());
    	}else{
    		pageUtil=new PageUtil2(1, 10, 0);
    	}
    	
		HashMap<String,Object> map=new HashMap<>();
		map.put("rolelist", listto);
		map.put("pageutil", pageUtil);
    	return map;
	}
	
	//admin------角色——模糊查询（角色状态）
	@Override
	public Map<String,Object> queryAllByLike(String status, Integer page) {
		List<Role> list=new ArrayList<>();
		List<Role> listto=new ArrayList<>();
		PageUtil2 pageUtil=null;
    
		list=roleMapper.queryAllByLike(status);
    	
    	if(list!=null && !list.isEmpty()){
    		ListPageUtil listPageUtil=new ListPageUtil(list,page,10);
    		listto.addAll(listPageUtil.getData());
    		
    		pageUtil=new PageUtil2(listPageUtil.getCurrentPage(), listPageUtil.getPageSize(),listPageUtil.getTotalCount());
    	}else{
    		pageUtil=new PageUtil2(1, 10, 0);
    	}
    	
		HashMap<String,Object> map=new HashMap<>();
		map.put("rolelistlike", listto);
		map.put("pageutil", pageUtil);
    	return map;
	}
	
	//admin------角色——添加功能（先查询出所有的权限）
	@Override
	public List<Functions> queryAllFunctions() {
		/*List<Functions> listfunction=new ArrayList<>();//最终返回的权限集合
		List<Functions> list=roleMapper.queryAllFunctions();//当前的function对象里面只有firstlevelmenu  status有值
		for (int i = 0; i < list.size(); i++) {
			
			List<SecondFunction> listsecond=new ArrayList<>();
			
			Functions functionto=new Functions();
			functionto.setFirstlevelmenu(list.get(i).getFirstlevelmenu());//一级菜单  添加
			List<SecondFunction> listfor=roleMapper.queryFunctionByFirstmenu(list.get(i).getFirstlevelmenu());//当前SecondFunction对象里面functionid  secondlevelmenu  thirdlevelmenu有值
			for (int j = 0; j < listfor.size(); j++) {
				
				List<ThirdFunction> listthird=new ArrayList<>();
				
				SecondFunction secondFunction=new SecondFunction();
				secondFunction.setSecondLevelmenu(listfor.get(j).getSecondLevelmenu());
				String[] functionIdarray=listfor.get(j).getFunctionid().split(",");
				for (int k = 0; k < functionIdarray.length; k++) {
					String thirdmenu=roleMapper.queryThirdmenuByFunctionId(Integer.parseInt(functionIdarray[k]));
					ThirdFunction thirdFunction=new ThirdFunction();
					thirdFunction.setId(Integer.parseInt(functionIdarray[k]));
					thirdFunction.setThirdLevelmenu(thirdmenu);
					listthird.add(thirdFunction);
				}
				secondFunction.setThirdfunctionlist(listthird);
				listsecond.add(secondFunction);
			}
			functionto.setSecondfunctionlist(listsecond);//二级菜单集合  添加
			listfunction.add(functionto);
		}
		return listfunction;*/
		List<Functions> list=roleMapper.queryAllfun();//查询权限表所有信息
		return list;
	}
	//admin------角色——添加功能
	@Override
	public int insert(Role record) {
		int num=roleMapper.insert(record);
		if(record.getListfunctionIdString()!=null&&!"".equals(record.getListfunctionIdString())){
			String[] strsplit=record.getListfunctionIdString().split(",");
			for (int i = 0; i < strsplit.length; i++) {
				roleMapper.insertFunction(record.getRoleid(),Integer.parseInt(strsplit[i]));
			}
		}
		return num;
	}
	 
	//admin------角色——查看权限功能
	@Override
	public List<Functions> queryFunctionsByRoleid(Integer roleid) {
		/*List<Functions> listfunction=new ArrayList<>();//最终返回的权限集合
		List<Functions> list=roleMapper.queryFunctionByRoleid(roleid);//当前的function对象里面只有firstlevelmenu  status有值
		for (int i = 0; i < list.size(); i++) {
			
			List<SecondFunction> listsecond=new ArrayList<>();
			
			Functions functionto=new Functions();
			functionto.setFirstlevelmenu(list.get(i).getFirstlevelmenu());//一级菜单  添加
			List<SecondFunction> listfor=roleMapper.queryFunctionByFirstmenuAndRoleid(list.get(i).getFirstlevelmenu(), roleid);//当前SecondFunction对象里面functionid  secondlevelmenu  thirdlevelmenu有值
			for (int j = 0; j < listfor.size(); j++) {
				
				List<ThirdFunction> listthird=new ArrayList<>();
				
				SecondFunction secondFunction=new SecondFunction();
				secondFunction.setSecondLevelmenu(listfor.get(j).getSecondLevelmenu());
				String[] functionIdarray=listfor.get(j).getFunctionid().split(",");
				for (int k = 0; k < functionIdarray.length; k++) {
					String thirdmenu=roleMapper.queryThirdmenuByFunctionId(Integer.parseInt(functionIdarray[k]));
					ThirdFunction thirdFunction=new ThirdFunction();
					thirdFunction.setId(Integer.parseInt(functionIdarray[k]));
					thirdFunction.setThirdLevelmenu(thirdmenu);
					listthird.add(thirdFunction);
				}
				secondFunction.setThirdfunctionlist(listthird);
				listsecond.add(secondFunction);
			}
			functionto.setSecondfunctionlist(listsecond);//二级菜单集合  添加
			listfunction.add(functionto);
		}
		return listfunction;*/
		List<Functions> list=roleMapper.queryAllfunByRoleid(roleid);
		return list;
	}
	
	//admin-----角色——编辑功能（通过角色id查询角色对象）
	@Override
	public Map<String, Object> editByRoleid(Integer roleid) {
		Role role=roleMapper.selectByPrimaryKey(roleid);//当前角色id的角色对象----

		/*List<Functions> listfunction=new ArrayList<>();//最终返回的权限集合----
		List<Functions> list=roleMapper.queryAllFunctions();//当前的function对象里面只有firstlevelmenu  status有值
		for (int i = 0; i < list.size(); i++) {
			
			List<SecondFunction> listsecond=new ArrayList<>();
			
			Functions functionto=new Functions();
			functionto.setFirstlevelmenu(list.get(i).getFirstlevelmenu());//一级菜单  添加
			List<SecondFunction> listfor=roleMapper.queryFunctionByFirstmenu(list.get(i).getFirstlevelmenu());//当前SecondFunction对象里面functionid  secondlevelmenu  thirdlevelmenu有值
			for (int j = 0; j < listfor.size(); j++) {
				
				List<ThirdFunction> listthird=new ArrayList<>();
				
				SecondFunction secondFunction=new SecondFunction();
				secondFunction.setSecondLevelmenu(listfor.get(j).getSecondLevelmenu());
				String[] functionIdarray=listfor.get(j).getFunctionid().split(",");
				for (int k = 0; k < functionIdarray.length; k++) {
					String thirdmenu=roleMapper.queryThirdmenuByFunctionId(Integer.parseInt(functionIdarray[k]));
					ThirdFunction thirdFunction=new ThirdFunction();
					thirdFunction.setId(Integer.parseInt(functionIdarray[k]));
					thirdFunction.setThirdLevelmenu(thirdmenu);
					listthird.add(thirdFunction);
				}
				secondFunction.setThirdfunctionlist(listthird);
				listsecond.add(secondFunction);
			}
			functionto.setSecondfunctionlist(listsecond);//二级菜单集合  添加
			listfunction.add(functionto);
		}*/
		
		List<Functions> listfunction=roleMapper.queryAllfun();//查询权限表所有信息
		List<Functions> listfunctionByRoleid=roleMapper.queryAllfunByRoleid(roleid);
		/*List<Functions> listfunctionByRoleid=new ArrayList<>();//通过角色id最终返回的权限集合---
		List<Functions> list1=roleMapper.queryFunctionByRoleid(roleid);//当前的function对象里面只有firstlevelmenu  status有值
		for (int i = 0; i < list1.size(); i++) {
			
			List<SecondFunction> listsecond=new ArrayList<>();
			
			Functions functionto=new Functions();
			functionto.setFirstlevelmenu(list1.get(i).getFirstlevelmenu());//一级菜单  添加
			List<SecondFunction> listfor=roleMapper.queryFunctionByFirstmenuAndRoleid(list1.get(i).getFirstlevelmenu(), roleid);//当前SecondFunction对象里面functionid  secondlevelmenu  thirdlevelmenu有值
			for (int j = 0; j < listfor.size(); j++) {
				
				List<ThirdFunction> listthird=new ArrayList<>();
				
				SecondFunction secondFunction=new SecondFunction();
				secondFunction.setSecondLevelmenu(listfor.get(j).getSecondLevelmenu());
				String[] functionIdarray=listfor.get(j).getFunctionid().split(",");
				for (int k = 0; k < functionIdarray.length; k++) {
					String thirdmenu=roleMapper.queryThirdmenuByFunctionId(Integer.parseInt(functionIdarray[k]));
					ThirdFunction thirdFunction=new ThirdFunction();
					thirdFunction.setId(Integer.parseInt(functionIdarray[k]));
					thirdFunction.setThirdLevelmenu(thirdmenu);
					listthird.add(thirdFunction);
				}
				secondFunction.setThirdfunctionlist(listthird);
				listsecond.add(secondFunction);
			}
			functionto.setSecondfunctionlist(listsecond);//二级菜单集合  添加
			listfunctionByRoleid.add(functionto);
		}*/
		
		HashMap<String, Object> map=new HashMap<>();
    	map.put("role",role);
    	map.put("listall",listfunction);
    	map.put("listByRoleid",listfunctionByRoleid);
    	return map;
	}
	
	//admin----角色——更新保存功能
	@Override
	public int updateByPrimaryKey(Role record) {
		int num=roleMapper.updateByPrimaryKey(record);
		
		List<Integer> list=roleMapper.queryMiddleId(record.getRoleid());
		if(list.size()!=0){
			if(record.getListfunctionIdString()!=null&&!"".equals(record.getListfunctionIdString())){
				String[] strsplit=record.getListfunctionIdString().split(",");
				//进入当前    证明当前角色之前有权限  现在传进来有权限
				for (int i = 0; i <list.size(); i++) {
					roleMapper.delMiddleId(list.get(i));
				}
				for (int i = 0; i < strsplit.length; i++) {
					roleMapper.insertFunction(record.getRoleid(),Integer.parseInt(strsplit[i]));
				}
			}else{
				//进入当前  证明当前角色之前有权限   现在传进来没有权限
				for (int i = 0; i <list.size(); i++) {
					roleMapper.delMiddleId(list.get(i));
				}
			}
		}else{
			if(record.getListfunctionIdString()!=null&&!"".equals(record.getListfunctionIdString())){
				//进入当前  证明当前角色之前没有权限   现在传进来有权限
				String[] strsplit=record.getListfunctionIdString().split(",");
				for (int i = 0; i < strsplit.length; i++) {
					roleMapper.insertFunction(record.getRoleid(),Integer.parseInt(strsplit[i]));
				}
			}
		}
		return num;
	}
	
	//admin----角色——修改状态
    public int updateStatus(Integer id,String status){
    	List<Integer> sysidlist=roleMapper.querysysid(id);
    	int num=0;
		if(status.equals("1")){
			num=roleMapper.upaStatusOpen(id);
			for (int i = 0; i < sysidlist.size(); i++) {
				sysUserMapper.upaStatusOpen(sysidlist.get(i));
			}
		}else{
			num=roleMapper.upaStatusClose(id);
			for (int i = 0; i < sysidlist.size(); i++) {
				sysUserMapper.upaStatusClose(sysidlist.get(i));
			}
		}
		return num;
    }
    
    //admin-----角色——修改假删除状态
    public int upaFalseDel(Integer roleid){
    	int num=roleMapper.upaFalseDel(roleid);
    	roleMapper.delfunction(roleid);
    	roleMapper.delsysuser(roleid);
    	return num;
    }
    
}
