package com.zhita.controller.SmsReport;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mysql.fabric.xmlrpc.base.Data;
import com.zhita.model.manage.Shortmessage;
import com.zhita.model.manage.SmsSendRequest;
import com.zhita.service.manage.SmsReport.Smservice;


@Controller
@RequestMapping("SendDateSms")
public class SmsData {
	
	
	@Autowired
	private Smservice serve;
	
	

		
	    static int count = 0;
	    /**
	     * 
	     * @param years		几点
	     * @param months	几分钟
	     * @param days		几秒
	     * @param da		逾期前几天
	     * @param companyId
	     */
	    @ResponseBody
	    @RequestMapping("SmsDatesend")
	    public void showTimer(SmsSendRequest sms) {
	        TimerTask task = new TimerTask() {
	            @Override
	            public void run() {
	                ++count;
	                Shortmessage shortmessage = serve.sendDateSned(sms);
	                SmsSendRequest sms = new SmsSendRequest();
	                if(shortmessage != null){
	                	sms.setMsg(shortmessage.getMsg());
	                	String b = shortmessage.getPhonesa().toString();
	            		String ca = b.substring(1,b.length()-1);
	                	sms.setPhone(ca);
	                	sms.setCompanyid(shortmessage.getCompanyid());
	                	sms.setPhonenum(shortmessage.getPhonenum());
	                	serve.SendSm(sms);
	                }
	                System.out.println("定时器运行:"+count + "次");
	                System.out.println("时间=" + new Date() + " 执行了" + count + "次"); // 1次
	                
	            }
	        };
	        
	        //设置执行时间
	        Calendar calendar = Calendar.getInstance();
	        int year = calendar.get(Calendar.YEAR);
	        int month = calendar.get(Calendar.MONTH);
	        int day = calendar.get(Calendar.DAY_OF_MONTH);//每天
	        SimpleDateFormat simyear = new SimpleDateFormat("HH");
	        SimpleDateFormat simmonth = new SimpleDateFormat("mm");
	        sms.setYears(Integer.valueOf(simyear.format(new Date())));
	        sms.setMonths(Integer.valueOf(simmonth.format(new Date())));
	        sms.setDays(00);
	        //定制每天的21:09:00执行，
	        calendar.set(year, month, day, sms.getYears(), sms.getMonths()+1, sms.getDays());
	        Date date = calendar.getTime();
	        Timer timer = new Timer();
	        System.out.println(date);
	        System.out.println("设置时间:");
	        int period = 3600 * 1000;
	        //int period = 3600 * 2000;
	        //每天的date时刻执行task，每隔一小时重复执行
	        timer.schedule(task, date, period);
	        //每天的date时刻执行task, 仅执行一次
	        //timer.schedule(task, date);
	    }
	    
	    
	    
	    
	    
	    /**
	     * 
	     * @param companyid
	     */
	    @ResponseBody
	    @RequestMapping("showTimerBank")
	    public void showTimerBank(SmsSendRequest sms) {
	        TimerTask task = new TimerTask() {
	            @Override
	            public void run() {
	                ++count;
	                serve.sendBank();
	                System.out.println("时间=" + new Date() + " 执行了" + count + "次"); // 1次
	                
	            }
	        };
	        
	        //设置执行时间
	        Calendar calendar = Calendar.getInstance();
	        int year = calendar.get(Calendar.YEAR);
	        int month = calendar.get(Calendar.MONTH);
	        int day = calendar.get(Calendar.DAY_OF_MONTH);//每天
	        SimpleDateFormat simyear = new SimpleDateFormat("HH");
	        SimpleDateFormat simmonth = new SimpleDateFormat("mm");
	        sms.setYears(Integer.valueOf(simyear.format(new Date())));
	        sms.setMonths(Integer.valueOf(simmonth.format(new Date())));
	        sms.setDays(00);
	        //定制每天的21:09:00执行，
	        calendar.set(year, month, day, sms.getYears(), sms.getMonths()+1, sms.getDays());
	        Date date = calendar.getTime();
	        Timer timer = new Timer();
	        System.out.println(date);
	        System.out.println("设置时间:");
	        int period = 3600 * 1000;
	        //int period = 3600 * 2000;
	        //每天的date时刻执行task，每隔一小时重复执行
	        timer.schedule(task, date, period);
	        //每天的date时刻执行task, 仅执行一次
	        //timer.schedule(task, date);
	    }


}
