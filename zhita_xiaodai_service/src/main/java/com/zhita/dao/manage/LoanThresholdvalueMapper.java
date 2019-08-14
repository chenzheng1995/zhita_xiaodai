package com.zhita.dao.manage;

import java.math.BigDecimal;
import java.util.List;

import com.zhita.model.manage.LoanThresholdvalue;

public interface LoanThresholdvalueMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(LoanThresholdvalue record);

    int insertSelective(LoanThresholdvalue record);

    LoanThresholdvalue selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LoanThresholdvalue record);

    int updateByPrimaryKey(LoanThresholdvalue record);
    
    //后台管理----放款表当天放款金额
    BigDecimal paymentToday(Integer companyId,String starttime,String endtime);
    
    //后台管理----放款表所有放款金额
    BigDecimal allpayment(Integer companyId);
    
    //后台管理----放款表最早的放款时间
    String minpaymenttime(Integer companyId);
    
    //后台管理---查询放款渠道最大阀值表     最大阀值
    int maxthresholdvalue(Integer companyId);
    
    //后台管理----修改最大阀值
    int upamaxthresholdvalue(Integer maxthresholdvalue);
    
    //后台管理----查询放款渠道状态
    List<String> queryStatus(Integer companyId);
    
    //后台管理----修改放款渠道状态
    int upaloanstatus(String status,Integer companyId);

	int getmaxthresholdvalue(int companyId);
}