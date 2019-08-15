package com.zhita.controller.borrowmoneymessage;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhita.dao.manage.BankcardMapper;
import com.zhita.model.manage.User;
import com.zhita.service.manage.borrowmoneymessage.IntBorrowmonmesService;
import com.zhita.service.manage.operator.OperatorService;
import com.zhita.service.manage.user.IntUserService;
import com.zhita.service.manage.userattestation.UserAttestationService;
import com.zhita.service.test.TestService;
import com.zhita.util.PhoneDeal;


//虚拟宣传页的立即提现的页面数据
@Controller
@RequestMapping(value="/borrowMoneyMessage")
public class borrowMoneyMessageController {
	@Autowired
	IntBorrowmonmesService intBorrowmonmesService;
	
	@Autowired
	TestService testService;
	
	@Autowired 
	IntUserService intUserService;
	
	@Autowired
	UserAttestationService userAttestationService;
	
	@Autowired
	OperatorService operatorService;
	
	@Autowired
	BankcardMapper bankcardMapper;
	

    @RequestMapping("/getborrowMoneyMessage")
    @ResponseBody
    @Transactional
    public Map<String, Object> getborrowMoneyMessage(int companyId,Integer userId) {
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> map2 = new HashMap<String, Object>();
		   String userAttestation =null;
		   String Operator =null;
		   String bankcard =null;
		   BigDecimal canBorrowlines = null;
           map = intBorrowmonmesService.getborrowMoneyMessage(companyId); 
           canBorrowlines = (BigDecimal) map.get("canBorrowlines");//借款金额	
        if(userId!=null) {
 		   Map<String, Object> map4 = userAttestationService.getuserAttestation(userId);
 		   if(map4==null) {
 			   userAttestation ="0";
 		   }else {
 		       userAttestation = (String) map4.get("attestationStatus");
 		   }
 		   
 		   Map<String, Object> map5 = operatorService.getOperator(userId);
 		   if(map5==null) {
 			   Operator ="0";
 		   }else {
 		       Operator = (String) map5.get("attestationStatus");
 		   }
 		   
 		   
 		   Map<String, Object> map3 = bankcardMapper.getattestationStatus(userId);
 		   if(map3==null) {
 			   bankcard ="0";
 		   }else {
 			   bankcard = (String) map3.get("attestationStatus");
 		   }
 		   
 		   String shareOfState = intUserService.getshareOfState(userId);
 		   
 		  if(userAttestation.equals("1")&&Operator.equals("1")&&bankcard.equals("1")&&("2".equals(shareOfState)||"4".equals(shareOfState))) {
 			 canBorrowlines = intUserService.getcanBorrowLines(userId);
 		  }
        }
        BigDecimal averageDailyInterest1 = (BigDecimal) map.get("averageDailyInterest");//日均利息
        BigDecimal bd8 = new BigDecimal("100");
        BigDecimal averageDailyInterest = averageDailyInterest1.divide(bd8);
        Integer platformfeeRatio = (Integer) map.get("platformfeeRatio");
        int lifeOfLoan = ((int) map.get("lifeOfLoan"));//借款天数
        BigDecimal loan = new BigDecimal(0);
        loan=BigDecimal.valueOf((int)lifeOfLoan);
        BigDecimal alsoAmount = ((canBorrowlines.multiply(loan).multiply(averageDailyInterest)).add(canBorrowlines)).setScale(2,BigDecimal.ROUND_HALF_UP);//应还金额
        map2.put("alsoAmount", alsoAmount);
        map2.put("averageDailyInterest1",averageDailyInterest1);
        map2.put("lifeOfLoan",lifeOfLoan);
        map2.put("canBorrowlines",canBorrowlines);
        map2.put("platformfeeRatio",platformfeeRatio);
        return map2;

    }
    
    @RequestMapping("/get")
    @ResponseBody
    @Transactional
    public String get(String name) {

        MyThread2 Thread = new MyThread2(name);   
        String date = System.currentTimeMillis()+""; 
        testService.updatetest(name,date);
        if(name.equals("小红")) {
            Thread.start();
            return name;
        }


		return name;
    }
    
    

    

}
