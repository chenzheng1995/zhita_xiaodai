package com.zhita.service.manage.borrowmoneymessage;

import com.zhita.dao.manage.BorrowMoneyMessageMapper;
import com.zhita.dao.manage.SysUserMapper;
import com.zhita.model.manage.BorrowMoneyMessage;
import com.zhita.model.manage.Company;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BorrowmonmesServiceImp implements IntBorrowmonmesService{
	@Autowired
	private BorrowMoneyMessageMapper borrowMoneyMessageMapper;
	@Autowired
	private SysUserMapper sysUserMapper;
	
	//后台管理----查询借款信息表所有信息
    public List<BorrowMoneyMessage> queryAll(Integer companyId){
    	List<BorrowMoneyMessage> list=borrowMoneyMessageMapper.queryAll(companyId);
    	return list;
    }
    
    //后台管理---添加功能（查询出所有公司）
    public List<Company> queryAllCompany(){
    	List<Company> list=sysUserMapper.queryAllCompany();
    	return list;
    }
    
    //后台管理---添加功能
    public int insert(BorrowMoneyMessage record){
    	record.setOperationtime(System.currentTimeMillis()+"");//获取当前时间戳
    	int num=borrowMoneyMessageMapper.insert(record);
    	return num;
    }
    
    //后台管理---根据id查询当前对象信息
    public BorrowMoneyMessage selectByPrimaryKey(Integer id){
    	BorrowMoneyMessage borrowMoneyMessage=borrowMoneyMessageMapper.selectByPrimaryKey(id);
    	return borrowMoneyMessage;
    }
    
    
    //后台管理----修改保存功能
    @Transactional
    public int updateByPrimaryKey(BorrowMoneyMessage record){
    	record.setOperationtime(System.currentTimeMillis()+"");//获取当前时间戳
    	int num=borrowMoneyMessageMapper.updateByPrimaryKey(record);
    	return num;
    }

	@Override
	public Map<String, Object> getborrowMoneyMessage(int companyId) {
		Map<String, Object> map = borrowMoneyMessageMapper.getborrowMoneyMessage(companyId); 
		return map;
	}

	@Override
	public BigDecimal getCanBorrowlines(int companyId) {
		BigDecimal canBorrowlines = borrowMoneyMessageMapper.getCanBorrowlines(companyId);
		return canBorrowlines;
	}
}
