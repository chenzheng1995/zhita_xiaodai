package com.zhita.service.manage.applycondition;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhita.dao.manage.ApplyConditionMapper;
import com.zhita.dao.manage.SysUserMapper;
import com.zhita.model.manage.ApplyCondition;
import com.zhita.model.manage.Company;

@Service
public class ApplyconditionServiceImp implements IntApplyconditionService{
	@Autowired
	private ApplyConditionMapper applyConditionMapper;
	@Autowired
	private SysUserMapper sysUserMapper;
	
	//后台管理---查询申请条件配置表所有信息
    public List<ApplyCondition> queryAll(Integer companyId){
    	List<ApplyCondition> list=applyConditionMapper.queryAll(companyId);
    	return list;
    }
    
    //后台管理---添加功能（查询出所有公司）
    public List<Company> queryAllCompany(){
    	List<Company> list=sysUserMapper.queryAllCompany();
    	return list;
    }
    
    //后台管理---添加功能
    public int insert(ApplyCondition record){
    	int num=applyConditionMapper.insert(record);
    	return num;
    }
    
    //后台管理---根据id查询出当前对象
    public ApplyCondition selectByPrimaryKey(Integer id){
    	ApplyCondition applyCondition=applyConditionMapper.selectByPrimaryKey(id);
    	return applyCondition;
    }
    
    //后台管理---编辑功能
    public int updateByPrimaryKey(ApplyCondition record){
    	int num=applyConditionMapper.updateByPrimaryKey(record);
    	return num;
    }

	@Override
	public Map<String, Object> getApplycondition(int companyId) {
		Map<String, Object> map2 =  applyConditionMapper.getApplycondition(companyId);
		return map2;
	}
}
