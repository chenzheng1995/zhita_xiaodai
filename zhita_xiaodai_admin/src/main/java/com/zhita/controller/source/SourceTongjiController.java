package com.zhita.controller.source;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhita.model.manage.TongjiSorce;
import com.zhita.service.manage.source.IntSourceService;
import com.zhita.util.DateListUtil;
import com.zhita.util.RedisClientUtil;
import com.zhita.util.Timestamps;
/**
 * 我们自己看的渠道统计
 * @author lhq
 * @{date} 2019年7月5日
 */
@Controller
@RequestMapping("/sourcetongji")
public class SourceTongjiController {
	@Autowired
	private IntSourceService intSourceService;
	
	//后台管理---我们自己看的统计数据——在用户表存在的渠道，当天的统计信息（数据待完善   目前只有渠道名     uv  注册数    uv到注册的转化率）
	@ResponseBody
	@RequestMapping("/queryByToday")
	public void queryByToday(Integer companyId,Integer page)throws ParseException{
		List<TongjiSorce> listsource = new ArrayList<>();
		List<TongjiSorce> listsourcepage = new ArrayList<>();//经过分页后的数据集合
		RedisClientUtil redisClientUtil=new RedisClientUtil();
		Date date=new Date();//得到当天时间
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
		String today=sf.format(date);
		
		String startTime = today;
		String startTimestamps = Timestamps.dateToStamp(startTime);
		String endTime = today;
		String endTimestamps = (Long.parseLong(Timestamps.dateToStamp(endTime))+86400000)+"";
		
		listsource=intSourceService.queryAllSourceByUser(companyId, startTimestamps, endTimestamps);
		for (int i = 0; i < listsource.size(); i++) {
			String sourcename=listsource.get(i).getSourcename();//渠道名
			float registernum=listsource.get(i).getRegisternum();//真实的注册数
			Integer companyid=listsource.get(i).getCompanyid();//公司id
			int uv=0;
			String cvr=null;
			if (redisClientUtil.getSourceClick(companyid + sourcename + today + "daichaoKey") == null) {
				uv = 0;
			} else {
				uv = Integer.parseInt(companyid + sourcename + today + "daichaoKey");
			}
			listsource.get(i).setUv(uv);//uv
			if ((registernum < 0.000001) || (uv == 0)) {
				cvr = 0 + "%";// 得到转化率
			} else {
				cvr = (new DecimalFormat("#.00").format(registernum / uv * 100)) + "%";// 得到uv到注册人数转化率
			}
			listsource.get(i).setCvr(cvr);//uv到注册的转化率
		}
	}
	
	//后台管理---我们自己看的统计数据——在用户表存在的渠道    某个时间段的统计信息
	@ResponseBody
	@RequestMapping("/queryByTimeslot")
	public void queryByTimeslot(Integer companyId,Integer page,String dateStart,String dateEnd) throws ParseException{
		List<TongjiSorce> listsource = new ArrayList<>();
		List<TongjiSorce> listsourcepage = new ArrayList<>();//经过分页后的数据集合
		RedisClientUtil redisClientUtil=new RedisClientUtil();
		
		String startTime = dateStart;
		String startTimestamps = Timestamps.dateToStamp(startTime);
		String endTime = dateEnd;
		String endTimestamps = (Long.parseLong(Timestamps.dateToStamp(endTime))+86400000)+"";
		
		List<String> listdate=DateListUtil.getDays(dateStart, dateEnd);
		
		listsource=intSourceService.queryAllSourceByUser(companyId, startTimestamps, endTimestamps);
		for (int i = 0; i < listsource.size(); i++) {
			String sourcename=listsource.get(i).getSourcename();//渠道名
			float registernum=listsource.get(i).getRegisternum();//真实的注册数
			Integer companyid=listsource.get(i).getCompanyid();//公司id
			int uv=0;
			String cvr=null;
			for (int j = 0; j < listdate.size(); j++) {
				int uvi=0;
				if (redisClientUtil.getSourceClick(companyid + sourcename + listdate.get(j) + "daichaoKey") == null) {
					uv = 0;
				} else {
					uv = Integer.parseInt(companyid + sourcename + listdate.get(j) + "daichaoKey");
				}
				uv=uv+uvi;
			}
			
			listsource.get(i).setUv(uv);//uv
			if ((registernum < 0.000001) || (uv == 0)) {
				cvr = 0 + "%";// 得到转化率
			} else {
				cvr = (new DecimalFormat("#.00").format(registernum / uv * 100)) + "%";// 得到uv到注册人数转化率
			}
			listsource.get(i).setCvr(cvr);//uv到注册的转化率
		}
	}
	
	// 后台管理---我们自己看的统计数据——在用户表存在的渠道    某个时间段每一天的详细信息  
	//例如01号-03号   外面显示的是01号到03号总的统计数据    里面显示的是01,02,03号每一天的详细统计数据
	@ResponseBody
	@RequestMapping("/queryAllPageDetail")
	public void queryAllPageDetail(Integer companyId,String sourcename, String dateStart,String dateEnd) throws ParseException {
		RedisClientUtil redisClientUtil = new RedisClientUtil();
		
		List<TongjiSorce> listsource = new ArrayList<>();
		
		List<String> listdate=DateListUtil.getDays(dateStart, dateEnd);
		
		for (int i = 0; i < listdate.size(); i++) {
			String date=listdate.get(i);
			String startTime = date;
			String startTimestamps = Timestamps.dateToStamp(startTime);
			String endTime = date;
			String endTimestamps = (Long.parseLong(Timestamps.dateToStamp(endTime))+86400000)+"";
			
			TongjiSorce tongjiSorce=new TongjiSorce();
			tongjiSorce=intSourceService.queryAllSourceByUserDetail(companyId, startTimestamps, endTimestamps, sourcename);
			float registernum=tongjiSorce.getRegisternum();//真实的注册数
			
			int uv=0;
			String cvr=null;
			if (redisClientUtil.getSourceClick(companyId + sourcename + listdate.get(i) + "daichaoKey") == null) {
				uv = 0;
			} else {
				uv = Integer.parseInt(companyId + sourcename + listdate.get(i) + "daichaoKey");
			}
			tongjiSorce.setUv(uv);
			if ((registernum < 0.000001) || (uv == 0)) {
				cvr = 0 + "%";// 得到转化率
			} else {
				cvr = (new DecimalFormat("#.00").format(registernum / uv * 100)) + "%";// 得到uv到注册人数转化率
			}
			tongjiSorce.setCvr(cvr);//uv到注册的转化率
			listsource.add(tongjiSorce);//listsoruce里面将每一天的数据都存进去
			
		}
	}
}
