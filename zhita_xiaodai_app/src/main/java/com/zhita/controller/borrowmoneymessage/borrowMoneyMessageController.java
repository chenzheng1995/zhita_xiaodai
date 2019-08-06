package com.zhita.controller.borrowmoneymessage;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhita.model.manage.User;
import com.zhita.service.manage.borrowmoneymessage.IntBorrowmonmesService;
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
	

    @RequestMapping("/getborrowMoneyMessage")
    @ResponseBody
    @Transactional
    public Map<String, Object> getborrowMoneyMessage(int companyId) {
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> map2 = new HashMap<String, Object>();
        map = intBorrowmonmesService.getborrowMoneyMessage(companyId); 
        BigDecimal canBorrowlines = (BigDecimal) map.get("canBorrowlines");
        BigDecimal averageDailyInterest = (BigDecimal) map.get("averageDailyInterest");
        BigDecimal bd8 = new BigDecimal("100");
        averageDailyInterest = averageDailyInterest.divide(bd8);
        int lifeOfLoan = ((int) map.get("lifeOfLoan"));
        BigDecimal loan = new BigDecimal(0);
        loan=BigDecimal.valueOf((int)lifeOfLoan);
        BigDecimal alsoAmount = ((canBorrowlines.multiply(loan).multiply(averageDailyInterest)).add(canBorrowlines)).setScale(2,BigDecimal.ROUND_HALF_UP);
        map2.put("alsoAmount", alsoAmount);
        map2.put("map1", map);
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
