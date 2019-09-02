package com.zhita.service.manage.bankautosetting;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhita.dao.manage.BankautoMapper;
import com.zhita.model.manage.Bankauto;
import com.zhita.model.manage.Company;
import com.zhita.util.PageUtil2;

@Service
public class BankautosetServiceImp implements IntBankautosetService{
	@Autowired
	private BankautoMapper bankautoMapper;
	
	//后台管理----查询所有
    public Map<String,Object> queryAll(Integer companyId,Integer page){
    	int totalCount=bankautoMapper.querycount(companyId);
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
    	List<Bankauto> list=bankautoMapper.queryAll(companyId,pages,pageUtil.getPageSize());
    	
    	Map<String,Object> map=new HashMap<String, Object>();
    	map.put("bankautolist", list);
    	map.put("pageutil", pageUtil);
    	return map;
    }
    
    //后台管理-----添加功能（先查询出所有公司）
    public List<Company> queryAllCompany(){
    	List<Company> list=bankautoMapper .queryAllCompany();
    	return list;
    }

    //后台管理----添加功能
    public int insert(Bankauto record){
    	int num=bankautoMapper.insert(record);
    	return num;
    }
    
    //后台管理----根据id查询当前对象
    public Bankauto selectByPrimaryKey(Integer id){
    	Bankauto bankauto=bankautoMapper.selectByPrimaryKey(id);
    	return bankauto;
    }
    
    //后台管理----更新修改
    public int updateByPrimaryKey(Bankauto record){
    	int num=bankautoMapper.updateByPrimaryKey(record);
    	return num;
    }
    
    //后台管理----修改假删除状态
    public int upaFaldel(Integer id){
    	int num=bankautoMapper.upaFaldel(id);
    	return num;
    }
}
