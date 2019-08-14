package com.zhita.dao.manage;

import java.math.BigDecimal;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.zhita.model.manage.Orderdetails;

public interface OrderdetailsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Orderdetails record);

    int insertSelective(Orderdetails record);


    Orderdetails selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Orderdetails record);

    int updateByPrimaryKey(Orderdetails record);

	BigDecimal getlastLine(int ordersId);

	int setororderdetails(@Param("orderId")int orderId,@Param("finalLine") BigDecimal finalLine,@Param("averageDailyInterest") BigDecimal averageDailyInterest,
			@Param("totalInterest")BigDecimal totalInterest,@Param("platformServiceFee") BigDecimal platformServiceFee,@Param("actualAmountReceived") BigDecimal actualAmountReceived,
			@Param("registeClient")String registeClient,@Param("sourceName") String sourceName,@Param("shouldTotalAmount") BigDecimal shouldTotalAmount,@Param("surplus_money") BigDecimal surplus_money);

	Map<String, Object> getOrderdetails(String orderStatus);

	BigDecimal getShouldReapyMoney(int orderId);

	BigDecimal getinterestInAll(int orderId);

	String getoverdueNumberOfDays(int orderId);

	BigDecimal interestPenaltySum(int id);
	
	
	String loanSetStatu(Integer companyId);


}