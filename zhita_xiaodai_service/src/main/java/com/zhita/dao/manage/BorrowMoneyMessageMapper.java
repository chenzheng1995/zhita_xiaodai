package com.zhita.dao.manage;

import java.util.List;
import java.util.Map;

import com.zhita.model.manage.BorrowMoneyMessage;

public interface BorrowMoneyMessageMapper {
    int deleteByPrimaryKey(Integer id);
    
    //后台管理---添加功能
    int insert(BorrowMoneyMessage record);

    int insertSelective(BorrowMoneyMessage record);
    
    //后台管理---根据id查询当前对象信息
    BorrowMoneyMessage selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BorrowMoneyMessage record);
    
    //后台管理----修改保存功能
    int updateByPrimaryKey(BorrowMoneyMessage record);
    
    //后台管理----查询借款信息表所有信息
    List<BorrowMoneyMessage> queryAll(Integer companyId);

	Map<String, Object> getborrowMoneyMessage(int companyId);
}