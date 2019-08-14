package com.zhita.service.manage.deferredsettings;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhita.dao.manage.DeferredSettingsMapper;
import com.zhita.dao.manage.SysUserMapper;
import com.zhita.model.manage.BorrowMoneyMessage;
import com.zhita.model.manage.Company;
import com.zhita.model.manage.DeferredSettings;

@Service
public class DeferredsetServcieImp implements IntDeferredsetService{
	@Autowired
	private DeferredSettingsMapper deferredSettingsMapper;
	@Autowired
	private SysUserMapper sysUserMapper;
	
	//后台管理---查询延期设置表所有信息
    public List<DeferredSettings> queryAll(Integer companyId){
    	List<DeferredSettings> list=deferredSettingsMapper.queryAll(companyId);
    	for (int i = 0; i < list.size(); i++) {
    		BorrowMoneyMessage borr=deferredSettingsMapper.queryBorrow(list.get(i).getProductid());
    		list.get(i).setOncedeferredday(borr.getLifeofloan());
    		list.get(i).setOncedeferredmoney(borr.getCanborrowlines().multiply(new BigDecimal(borr.getPlatformfeeratio())).divide(new BigDecimal(100)));
		}
    	return list;
    }
    
   //后台管理---(添加功能)先查询借款信息表的产品id和产品名称    公司表信息     供添加时选择
    public Map<String,Object> queryAllBorrow(Integer companyId){
    	List<BorrowMoneyMessage> listborr=deferredSettingsMapper.queryAllBorrow(companyId);
    	List<Company> listcom=sysUserMapper.queryAllCompany();
    	
    	Map<String, Object> map=new HashMap<>();
    	map.put("listborr",listborr );
    	map.put("listcom", listcom);
    	return map;
    }
    
    //后台管理---添加功能
    public int insert(DeferredSettings record){
    	record.setOperationtime(System.currentTimeMillis()+"");//获取当前时间戳
    	int num=deferredSettingsMapper.insert(record);
    	return num;
    }
    
    //后台管理---根据当前id查询出当前对象信息
    public DeferredSettings selectByPrimaryKey(Integer id){
    	DeferredSettings deferredSettings=deferredSettingsMapper.selectByPrimaryKey(id);
    	return deferredSettings;
    }
    
    //后台管理---修改保存功能
    @Transactional
    public int updateByPrimaryKey(DeferredSettings record){
    	record.setOperationtime(System.currentTimeMillis()+"");//获取当前时间戳
    	int num=deferredSettingsMapper.updateByPrimaryKey(record);
    	return num;
    }

	@Override
	public Map<String, Object> getDeferredset(int companyId) {
		Map<String, Object> map1 = deferredSettingsMapper.getDeferredset(companyId); 
		return map1;
	}
}
