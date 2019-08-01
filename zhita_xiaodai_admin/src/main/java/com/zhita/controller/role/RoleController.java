package com.zhita.controller.role;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhita.model.manage.Functions;
import com.zhita.model.manage.Role;
import com.zhita.service.manage.role.IntRoleService;
/**
 * 
 * @author lhq
 * @{date} 2019年4月19日
 */
@Controller
@RequestMapping("/role")
public class RoleController {
	@Autowired
	private IntRoleService intRoleService;
	
	//admin------角色——列表展示
	@ResponseBody
	@RequestMapping("/queryAll")
	/*@RequiresPermissions(value={"1"})*/
	public Map<String,Object> queryAll(Integer page) {
		Map<String,Object> map=intRoleService.queryAll(page);
		return map;
	}
	
	//admin------角色——模糊查询（角色状态）
	@ResponseBody
	@RequestMapping("/queryAllByLike")
	//@RequiresPermissions(value={"1"})
	public Map<String,Object> queryAllByLike(String status,Integer page) {
		Map<String,Object> map=intRoleService.queryAllByLike(status,page);
		return map;
	}
	
	//admin------角色——添加功能（先查询出所有的权限）
	@ResponseBody
	@RequestMapping("/queryAllFunctions")
	public List<Functions> queryAllFunctions() {
		List<Functions> list=intRoleService.queryAllFunctions();
		return list;
	}
	
	//admin------角色——添加功能
	@ResponseBody
	@RequestMapping("/insert")
	public int insert(Role record) {
		int num=intRoleService.insert(record);
		return num;
	}
	
	//admin------角色——查看权限功能
	@ResponseBody
	@RequestMapping("/queryFunctionsByRoleid")
	public List<Functions> queryFunctionsByRoleid(Integer roleid) {
		List<Functions> list=intRoleService.queryFunctionsByRoleid(roleid);
		return list;
	}
	
	//admin-----角色——编辑功能（通过角色id查询角色对象）
	@ResponseBody
	@RequestMapping("/editByRoleid")
	public Map<String, Object> editByRoleid(Integer roleid) {
		Map<String, Object> map=intRoleService.editByRoleid(roleid);
		return map;
	}
	
	//admin----角色——更新保存功能
	@ResponseBody
	@RequestMapping("/updateByPrimaryKey")
	public int updateByPrimaryKey(Role record) {
		int num=intRoleService.updateByPrimaryKey(record);
		return num;
	}
	
	//admin----角色——修改状态
	@ResponseBody
	@RequestMapping("/updateStatus")
	public int updateStatus(Integer id,String status){
		int num=intRoleService.updateStatus(id, status);
		return num;
	}
}
