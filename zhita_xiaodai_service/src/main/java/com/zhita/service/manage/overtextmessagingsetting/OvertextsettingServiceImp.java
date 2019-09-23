package com.zhita.service.manage.overtextmessagingsetting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhita.dao.manage.OverTextmessagingSettingMapper;
import com.zhita.model.manage.OverTextmessagingSetting;
import com.zhita.model.manage.TextmessagingTemplate;
import com.zhita.util.PageUtil2;

@Service
public class OvertextsettingServiceImp implements IntovertextsettingService{
	
	@Autowired
	private OverTextmessagingSettingMapper overTextmessagingSettingMapper;
	
	//后台管理---查询出短信发送内容模板表的数据
    public List<TextmessagingTemplate> querytexttemp(){
    	return overTextmessagingSettingMapper.querytexttemp();
    }
    
    //后台管理---添加功能
    public int insert(OverTextmessagingSetting record){
    	return overTextmessagingSettingMapper.insert(record);
    }
    
    //后台管理---查询列表
    public Map<String,Object> queryAll(Integer companyId,Integer page){
    	List<OverTextmessagingSetting> list=new ArrayList<OverTextmessagingSetting>();
    	
		int totalCount=overTextmessagingSettingMapper.queryAllcount(companyId);//查询总数量
		PageUtil2 pageUtil=new PageUtil2(page,totalCount);
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
    	list=overTextmessagingSettingMapper.queryAll(companyId, pages, pageUtil.getPageSize());//查询list集合
    	
		Map<String,Object> map=new HashMap<>();
		map.put("overtextlist", list);
		map.put("pageutil", pageUtil);
    	return map;
    	
    }
    
    //后台管理---根据id查询
    public OverTextmessagingSetting selectByPrimaryKey(Integer id){
    	return overTextmessagingSettingMapper.selectByPrimaryKey(id);
    }
    
    //后台管理---编辑功能
    public int updateByPrimaryKey(OverTextmessagingSetting record){
    	return overTextmessagingSettingMapper.updateByPrimaryKey(record);
    }
    
    //后台管理----修改假删除状态
    public int upaFalDel(Integer id){
    	return overTextmessagingSettingMapper.upaFalDel(id);
    }
}
