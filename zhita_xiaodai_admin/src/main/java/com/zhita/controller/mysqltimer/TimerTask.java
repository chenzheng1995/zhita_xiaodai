package com.zhita.controller.mysqltimer;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.zhita.service.manage.projecttimer.IntProjecttimerService;
import com.zhita.service.manage.source.IntSourceService;

@Component
public class TimerTask {
	@Autowired
	private IntSourceService intSourceService;
	
	@Autowired
	private IntProjecttimerService intProjecttimerService;
    
    /**
     * 每天00:00:00执行test1
     * @throws ParseException 
     */
	  //                   秒分时日 月周
	 //每日0点  将各个渠道的历史数据存入历史表
      @Scheduled(cron = "0 01 0 * * ?")
      @Lazy(false)
      public void test1() throws ParseException
      {
          System.out.println("开始做定时任务1");
          intSourceService.selAllTongji();
          System.out.println("定时任务结束1");
      } 
      
      //                   秒分时日 月周
      //定时任务     控制逾期
      @Scheduled(cron = "0 01 0 * * ?")
      @Lazy(false)
      public void test2() throws ParseException
      {
          System.out.println("开始做定时任务2");
          intProjecttimerService.selAllover();
          System.out.println("定时任务结束2");
      } 
      
      //                   秒分时日 月周
      //定时任务   控制逾期等级
      @Scheduled(cron = "0 01 0 * * ?")
      @Lazy(false)
      public void test3() throws ParseException
      {
          System.out.println("开始做定时任务3");
          intProjecttimerService.upaoverclass();
          System.out.println("定时任务结束3");
      } 
      
      //                   秒分时日 月周
      //定时任务   控制黑名单
      @Scheduled(cron = "0 01 0 * * ?")
      @Lazy(false)
      public void test4() throws ParseException
      {
          System.out.println("开始做定时任务4");
          intProjecttimerService.addblack();
          System.out.println("定时任务结束4");
      } 
}
