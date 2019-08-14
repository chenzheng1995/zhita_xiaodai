package com.zhita.service.manage.manconsettings;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhita.dao.manage.ManageControlSettingsMapper;
import com.zhita.dao.manage.SysUserMapper;
import com.zhita.model.manage.Company;
import com.zhita.model.manage.ManageControlSettings;

@Service
public class ManconsettingsServiceImp implements IntManconsettingsServcie{
	
	@Autowired
	private ManageControlSettingsMapper manageControlSettingsMapper;
	@Autowired
	private SysUserMapper sysUserMapper;
	
	//后台管理---查询风控设置管理表所有信息
    public List<ManageControlSettings> queryAll(Integer companyId){
    	List<ManageControlSettings> list=manageControlSettingsMapper.queryAll(companyId);
    	return list;
    }
    
    //后台管理---添加功能（查询出所有公司）
    public List<Company> queryAllCompany(){
    	List<Company> list=sysUserMapper.queryAllCompany();
    	return list;
    }
    
    //后台管理---添加功能
    @Transactional
    public int insert(ManageControlSettings record){
    	int num=manageControlSettingsMapper.insert(record);
    	return num;
    }
    
    //后台管理---根据主键id查询当前对象信息
    public ManageControlSettings selectByPrimaryKey(Integer id){
    	ManageControlSettings manageControlSettings=manageControlSettingsMapper.selectByPrimaryKey(id);
    	return manageControlSettings;
    }
    
    //后台管理---编辑保存功能
    @Transactional
    public int updateByPrimaryKey(ManageControlSettings record){
    	String roatnptfractionalsegment=record.getRoatnptfractionalsegment();//机审拒绝需人审分数段
    	String[] strroat=roatnptfractionalsegment.split("-");
    	String strStart=strroat[0];
    	String strEnd=strroat[1];
    	
    	String atrntlfractionalsegment=record.getAtrntlfractionalsegment();//机审拒绝不放款分数段
    	String[] stratr=atrntlfractionalsegment.split("-");
    	String strStart1=stratr[0];
    	record.setAtrntlfractionalsegment(strStart1+"-"+(Integer.parseInt(strStart)-1));//机审拒绝不放款分数段
    	
    	String airappfractionalsegment=record.getAirappfractionalsegment();//机审通过分数段
    	String[] straitr=airappfractionalsegment.split("-");
    	String strEnd1=straitr[1];
    	record.setAirappfractionalsegment((Integer.parseInt(strEnd)+1+"-"+strEnd1));
    	int num=manageControlSettingsMapper.updateByPrimaryKey(record);
    	return num;
    }

	@Override
	public Map<String, Object> getManconsettings(int manageControlId) {
		 Map<String, Object> map1 =  manageControlSettingsMapper.getManconsettings(manageControlId);  
		return map1;
	}
}
