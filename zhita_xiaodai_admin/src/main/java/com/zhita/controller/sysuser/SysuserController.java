package com.zhita.controller.sysuser;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhita.model.manage.SysUser;
import com.zhita.service.manage.sysuser.IntSysuserService;
/**
 * 
 * @author lhq
 * @{date} 2019年6月21日
 */
@Controller
@RequestMapping("/sysuser")
public class SysuserController {
	@Autowired
	private IntSysuserService intSysuserService;
	
	//admin------系统用户——列表展示
	@ResponseBody
	@RequestMapping("/queryAll")
	public Map<String, Object> queryAll(Integer companyId,Integer page){
		Map<String, Object> map=intSysuserService.queryAll(companyId,page);
		return map;
	}
	
	//admin------系统用户——模糊查询（账号和账号状态）
	@ResponseBody
	@RequestMapping("/queryAllByLike")
	public Map<String, Object> queryAllByLike(Integer companyId,String account,String status,Integer page){
		Map<String, Object> map=intSysuserService.queryAllByLike(companyId,account,status,page);
		return map;
	}
	//admin-----系统用户——添加功能（先查询出所有公司和所有角色）
	@ResponseBody
	@RequestMapping("/queryAllCompany")
	public Map<String,Object> queryAllCompany() {
		Map<String,Object> map=intSysuserService.queryAllCompany();
		return map;
	}
		
	//admin------系统用户——添加功能
	@ResponseBody
	@RequestMapping("/insert")
	public int insert(SysUser record) {
		int sum=intSysuserService.insert(record);
		return sum;
	}
	
	//admin-----系统用户——修改账号状态
	@ResponseBody
	@RequestMapping("/updateStatus")
	public int updateStatus(Integer id,String status){
		int num=intSysuserService.updateStatus(id, status);
		return num;
	}
	
	//admin----系统用户——编辑功能（通过主键id查询对象）
	@ResponseBody
	@RequestMapping("/selectByPrimaryKey")
    public Map<String, Object> selectByPrimaryKey(Integer userid){
		Map<String, Object> map=intSysuserService.selectByPrimaryKey(userid);
		return map;
	}
	
	//admin----系统用户——编辑功能（修改保存）
	@ResponseBody
	@RequestMapping("/updateByPrimaryKey")
	public int updateByPrimaryKey(SysUser record){
		int num=intSysuserService.updateByPrimaryKey(record);
		return num;
	}
	
	//admin---系统用户——修改假删除状态
	@ResponseBody
	@RequestMapping("/upaFalseDel")
    public int upaFalseDel(Integer id){
    	int num=intSysuserService.upaFalseDel(id);
    	return num;
    }
}
