package com.zhita.controller.borrowmoneymessage;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhita.model.manage.BorrowMoneyMessage;
import com.zhita.service.manage.borrowmoneymessage.IntBorrowmonmesService;

@Controller
@RequestMapping("/borrowmonmes")
public class BorrowmonmesController {
	@Autowired
	private IntBorrowmonmesService intBorrowmonmesService;
	
	//后台管理----查询借款信息表所有信息
	@ResponseBody
	@RequestMapping("/queryAll")
    public List<BorrowMoneyMessage> queryAll(Integer companyId){
    	List<BorrowMoneyMessage> list=intBorrowmonmesService.queryAll(companyId);
    	return list;
    }
	
	//后台管理---添加功能（查询出所有公司）
/*	@ResponseBody
	@RequestMapping("/queryAllCompany")
    public List<Company> queryAllCompany(){
    	List<Company> list=intBorrowmonmesService.queryAllCompany();
    	return list;
    }*/
	
	//后台管理---添加功能
/*	@ResponseBody
	@RequestMapping("/insert")
    public int insert(BorrowMoneyMessage record){
    	int num=intBorrowmonmesService.insert(record);
    	return num;
    }*/
	
	 //后台管理---根据id查询当前对象信息
/*	@ResponseBody
	@RequestMapping("/selectByPrimaryKey")
    public BorrowMoneyMessage selectByPrimaryKey(Integer id){
    	BorrowMoneyMessage borrowMoneyMessage=intBorrowmonmesService.selectByPrimaryKey(id);
    	return borrowMoneyMessage;
    }*/
	
	 //后台管理----修改保存功能
	@ResponseBody
	@RequestMapping("/updateByPrimaryKey")
    public int updateByPrimaryKey(BorrowMoneyMessage record){
    	int num=intBorrowmonmesService.updateByPrimaryKey(record);
    	return num;
    }
    
}
