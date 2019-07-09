package com.zhita.controller.mysqltimer;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.zhita.service.manage.source.IntSourceService;

@Component
public class TimerTask {
	@Autowired
	private IntSourceService intSourceService;
    
    /**
     * 每天00:00:00执行test1
     * @throws ParseException 
     */
    //                   秒分时日 月周
      @Scheduled(cron = "0 48 13 * * ?")
      public void test1() throws ParseException
      {
          System.out.println("开始做定时任务");
          intSourceService.queryAllTongji();
          System.out.println("定时任务结束");
      } 
}
