package com.zhita.service.manage.source;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhita.dao.manage.SourceMapper;
import com.zhita.dao.manage.SysUserMapper;
import com.zhita.model.manage.Company;
import com.zhita.model.manage.Source;
import com.zhita.model.manage.TongjiSorce;
import com.zhita.util.ListPageUtil;
import com.zhita.util.PageUtil;

@Service
public class SourceServiceImp implements IntSourceService{
	@Autowired
	private SourceMapper sourceMapper;
	@Autowired
	private SysUserMapper sysUserMapper;
	
	//后台管理---查询渠道表所有信息
    public Map<String,Object> queryAll(Integer comapnyId,Integer page){
    	List<Source> list=new ArrayList<>();
    	List<Source> listto=new ArrayList<>();
    	PageUtil pageUtil=null;
    	list=sourceMapper.queryAll(comapnyId);
    	
    	if(list!=null && !list.isEmpty()){
    		ListPageUtil listPageUtil=new ListPageUtil(list,page,2);
    		listto.addAll(listPageUtil.getData());
    		
    		pageUtil=new PageUtil(listPageUtil.getCurrentPage(), listPageUtil.getPageSize(),listPageUtil.getTotalCount());
    	}
    	
		HashMap<String,Object> map=new HashMap<>();
		map.put("sourcelist", listto);
		map.put("pageutil", pageUtil);
    	return map;
    }
    
    //后台管理---添加功能（查询出所有公司）
    public List<Company> queryAllCompany(){
    	List<Company> list=sysUserMapper.queryAllCompany();
    	return list;
    }
    
    //后台管理---添加功能
    public int insert(Source record){
    	int count=sourceMapper.ifSourceNameIfExist(record.getSourcename());
		int num=0;
		int num1=0;
		if(count==0){
			num=sourceMapper.insert(record);//添加渠道表信息	
			return num;
		}else{
			num1=sourceMapper.updateSource(record);
			return num1;
		}
    }
    
    //后台管理---根据id查询当前对象信息
    public Source selectByPrimaryKey(Integer id){
    	Source source=sourceMapper.selectByPrimaryKey(id);
    	return source;
    }
    
    //后台管理---编辑功能
    public int updateByPrimaryKey(Source record){
    	int num=sourceMapper.updateByPrimaryKey(record);
    	return num;
    }
    
    //后台管理---根据id  对当前对象的假删除状态进行修改
    public int updateFalDel(Integer id){
    	int num=sourceMapper.updateFalDel(id);
    	return num;
    }
    
    //后台管理---查询当天各个渠道在用户表的注册数量
    public List<TongjiSorce> queryAllSourceByUser(Integer companyid,String StartTime,String EndTime){
    	List<TongjiSorce> list=sourceMapper.queryAllSourceByUser(companyid, StartTime, EndTime);
    	return list;
    }
    
    //后台管理---查询某一天某个渠道的注册数量
    public TongjiSorce queryAllSourceByUserDetail(Integer companyid,String StartTime,String EndTime,String sourceName){
    	TongjiSorce tongjisource=sourceMapper.queryAllSourceByUserDetail(companyid, StartTime, EndTime, sourceName);
    	return tongjisource;
    }
    
    //后台管理---通过渠道名称查询出当前渠道的折扣率
    public String queryDiscount(String sourceName,Integer companyId){
    	String discount=sourceMapper.queryDiscount(sourceName, companyId);
    	return discount;
    }

	@Override
	public int getsourceId(String sourceName) {
		int merchantId = sourceMapper.getsourceId(sourceName);
		return merchantId;
	}
    
    
}
