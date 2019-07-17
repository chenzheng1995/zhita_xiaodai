package com.zhita.service.manage.thirdpartyint;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhita.dao.manage.SysUserMapper;
import com.zhita.dao.manage.ThirdpartyInterfaceMapper;
import com.zhita.model.manage.Company;
import com.zhita.model.manage.FaceRecognition;
import com.zhita.model.manage.LoanSetting;
import com.zhita.model.manage.OperatorSetting;
import com.zhita.model.manage.RepaymentSetting;
import com.zhita.model.manage.ThirdpartyInterface;

@Service
public class ThirdpartyintServiceImp implements IntThirdpartyintService{
	@Autowired
	private ThirdpartyInterfaceMapper thirdpartyInterfaceMapper;
	@Autowired
	private SysUserMapper sysUserMapper;
	
	//后台管理---查询出第三方接口配置表所有信息
    public List<ThirdpartyInterface> queryAll(Integer companyId){
    	List<ThirdpartyInterface> list=thirdpartyInterfaceMapper.queryAll(companyId);
    	return list;
    }
    
    //后台管理---添加功能（查询出所有公司）
    public Map<String, Object> queryAllCompany(Integer companyId){
    	List<Company> list=sysUserMapper.queryAllCompany();
    	List<FaceRecognition> list1=thirdpartyInterfaceMapper.queryface(companyId);
    	List<OperatorSetting> list2=thirdpartyInterfaceMapper.queryopera(companyId);
    	List<LoanSetting> list3=thirdpartyInterfaceMapper.queryloan(companyId);
    	List<RepaymentSetting> list4=thirdpartyInterfaceMapper.queryrepayment(companyId);
    	Map<String, Object> map=new HashMap<>();
    	map.put("comlist", list);
    	map.put("facelist", list1);
    	map.put("operalist", list2);
    	map.put("loanlist", list3);
    	map.put("repaylist", list4);
    	return map;
    }
    
    //后台管理---添加功能
    public int insert(ThirdpartyInterface record){
    	int num=thirdpartyInterfaceMapper.insert(record);
    	return num;
    }
    
    //后台管理---根据id查询当前对象信息
    public ThirdpartyInterface selectByPrimaryKey(Integer id){
    	ThirdpartyInterface thirdpartyInterface=thirdpartyInterfaceMapper.selectByPrimaryKey(id);
    	return thirdpartyInterface;
    }
    
    //后台管理---编辑保存功能
    public int updateByPrimaryKey(ThirdpartyInterface record){
    	int num=thirdpartyInterfaceMapper.updateByPrimaryKey(record);
    	return num;
    }
}
