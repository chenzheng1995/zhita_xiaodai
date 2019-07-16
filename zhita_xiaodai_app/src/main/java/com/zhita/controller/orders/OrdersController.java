package com.zhita.controller.orders;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.Lifecycle;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;
import com.zhita.dao.manage.OrderdetailsMapper;
import com.zhita.model.manage.AuthenticationInformation;
import com.zhita.model.manage.LiftingAmount;
import com.zhita.service.manage.borrowmoneymessage.IntBorrowmonmesService;
import com.zhita.service.manage.liftingamount.IntLiftingamountServcie;
import com.zhita.service.manage.operational.OperationalService;
import com.zhita.service.manage.order.IntOrderService;

@Controller
@RequestMapping("/Order")
public class OrdersController {
	@Autowired
	IntOrderService intOrderService;
	
	@Autowired
	IntLiftingamountServcie intLiftingamountServcie;
	
	@Autowired
	IntBorrowmonmesService intBorrowmonmesService;
	
	@Autowired
    OrderdetailsMapper orderdetailsMapper;

	//获取用户借款额度
	   @RequestMapping("/getCanBorrowLines")
	    @ResponseBody
	    public Map<String, Object> getCanBorrowLines(int userId,int companyId) {
		   BigDecimal finalLine = new BigDecimal(0);
		   int num =0;
		   Map<String, Object> map = new HashMap<String, Object>();		   
		   int borrowNumber = intOrderService.borrowNumber(userId,companyId); //用户还款次数
		   BigDecimal canBorrowlines = intBorrowmonmesService.getCanBorrowlines(companyId); //初始额度
		   ArrayList<LiftingAmount> list = intLiftingamountServcie.getintLiftingamount(companyId);
		   Integer firstline = intLiftingamountServcie.getFirstline(companyId);
		   if(firstline==null||borrowNumber<firstline) {
			   finalLine = canBorrowlines;
			   map.put("finalLine", finalLine);
		   }else {
			   for (LiftingAmount liftingAmount : list) {
				     int userHowManyConsecutivePayments = liftingAmount.getUserhowmanyconsecutivepayments();//还款多少次之后提额
				     num = userHowManyConsecutivePayments;
				     if (borrowNumber<userHowManyConsecutivePayments) {
				    	 int increaseThequota = intLiftingamountServcie.getIncreaseThequota(num,companyId);//提高的额度				    		
				    	 BigDecimal decimalIncreaseThequota = new BigDecimal(Integer.toString(increaseThequota));
				    	 int ordersId =  intOrderService.getOrdersId(userId,companyId);
				    	 BigDecimal lastLine = orderdetailsMapper.getlastLine(ordersId);//获取最后一次还款时的额度
				    	 finalLine = lastLine.add(decimalIncreaseThequota);
				    	 map.put("finalLine", finalLine);
				     }
				}
		}

			return map;
		   
	   }
}
