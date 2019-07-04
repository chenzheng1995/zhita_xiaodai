package com.zhita.service.manage.borrowmoneymessage;

import java.util.List;

import com.zhita.model.manage.BorrowMoneyMessage;
import com.zhita.model.manage.Company;

public interface IntBorrowmonmesService {
	
	//后台管理----查询借款信息表所有信息
    public List<BorrowMoneyMessage> queryAll(Integer companyId);
    
    //后台管理---添加功能（查询出所有公司）
    public List<Company> queryAllCompany();
    
    //后台管理---添加功能
    public int insert(BorrowMoneyMessage record);
    
    //后台管理---根据id查询当前对象信息
    public BorrowMoneyMessage selectByPrimaryKey(Integer id);
    
    //后台管理----修改保存功能
    public int updateByPrimaryKey(BorrowMoneyMessage record);
}
