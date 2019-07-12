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
import com.zhita.util.PhoneDeal;


//借款金额
@Controller
@RequestMapping(value="/borrowMoneyMessage")
public class borrowMoneyMessageController {
	@Autowired
	IntBorrowmonmesService intBorrowmonmesService;
	

    @RequestMapping("/getborrowMoneyMessage")
    @ResponseBody
    @Transactional
    public Map<String, Object> getborrowMoneyMessage(int companyId) {
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> map2 = new HashMap<String, Object>();
        map = intBorrowmonmesService.getborrowMoneyMessage(companyId); 
        int canBorrowlines = (int) map.get("canBorrowlines");
        double averageDailyInterest = (double) map.get("averageDailyInterest");
        int lifeOfLoan = (int) map.get("lifeOfLoan");
        double alsoAmount = canBorrowlines*averageDailyInterest*lifeOfLoan+canBorrowlines;
        map2.put("alsoAmount", alsoAmount);
        map2.put("map1", map);
        return map2;

    }

}
