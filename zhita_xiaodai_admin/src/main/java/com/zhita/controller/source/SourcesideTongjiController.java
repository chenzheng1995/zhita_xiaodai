package com.zhita.controller.source;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhita.model.manage.TongjiSorce;
import com.zhita.service.manage.source.IntSourceService;
import com.zhita.util.DateListUtil;
import com.zhita.util.ListPageUtil;
import com.zhita.util.PageUtil2;
import com.zhita.util.RedisClientUtil;
import com.zhita.util.Timestamps;
/**
 * 渠道方看的统计数据
 * @author lhq
 * @{date} 2019年7月5日
 */
@Controller
@RequestMapping("/sourceside")
public class SourcesideTongjiController {
	@Autowired
	private IntSourceService intSourceService;
	
	/**
	 * 思路   渠道方看的统计     
	 * 1.只有在更改渠道折扣率的时候才进行添加或修改操作   
	 * 2.定时器方法里面也是进行添加修改操作（得到的时间差集   不是昨天就进行添加操作    是昨天就进行修改操作）
	 * 3.访问查询接口时不进行添加修改操作
	 * @param page
	 * @param companyId
	 * @param sourceName
	 * @throws ParseException
	 */
	@ResponseBody
	@RequestMapping("/queryAllTongji")
	public Map<String, Object> queryAllTongji(Integer page,Integer companyId,Integer sourceid,String sourceName) throws ParseException{
		String company=intSourceService.querycompany(companyId);
		SimpleDateFormat sf1=new SimpleDateFormat("yyyy/MM/dd");
		Map<String, Object> map = new HashMap<>();
		
		RedisClientUtil redisClientUtil=new RedisClientUtil();
		List<TongjiSorce> listsource = new ArrayList<>();
		List<TongjiSorce> listsourceto = new ArrayList<>(); 
		
		List<TongjiSorce> listHistory=intSourceService.queryAllBySourceName(sourceid);
		for (int i = 0; i < listHistory.size(); i++) {
			String startTimestamps = listHistory.get(i).getDate();
			String endTimestamps = (Long.parseLong(listHistory.get(i).getDate())+86400000)+"";
			Integer applynum=intSourceService.queryNum(companyId, sourceid,startTimestamps, endTimestamps);//申请人数
			listHistory.get(i).setApplynum(applynum);
			int orderpass=intSourceService.queryorderpass(sourceid, startTimestamps, endTimestamps);//已借款人数
			listHistory.get(i).setOrderpass(orderpass);
			
			listHistory.get(i).setDate(Timestamps.stampToDate1(listHistory.get(i).getDate()));//将历史表数据的日期都变为年月日格式
			listHistory.get(i).setSourcename(sourceName);
		}
		listsource.addAll(listHistory);
		
		Date d=new Date();
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
		String date=sf.format(d);//date为当天时间(格式为年月日)
		
		for (int i = 0; i < listsource.size(); i++) {
			if((date.equals(listsource.get(i).getDate()))==true){
				listsource.remove(listsource.get(i));
			}
		}
		
		String startTime = date;
		String startTimestamps = Timestamps.dateToStamp(startTime);
		String endTime = date;
		String endTimestamps = (Long.parseLong(Timestamps.dateToStamp(endTime))+86400000)+"";
		
		TongjiSorce tongjiSorcelist=intSourceService.queryBySourcenameAndDate(sourceid, startTimestamps,endTimestamps);//判断当前渠道今天在历史表是否存在数据
		
		int uv = 0;
		String cvr = null;
		float disAppnum=0;//折扣申请数
		Integer applynum=0;//申请数
		int orderpass=0;//借款人数
		if(tongjiSorcelist==null){
			float appnum = intSourceService.queryApplicationNumberlike(companyId, sourceid, startTimestamps, endTimestamps);// 得到申请数(该渠道当天在user表的注册数)
			String discount = intSourceService.queryDiscount(sourceid, companyId);// 得到折扣率  （比如取到字符串  "80%"）
			int discount1 = Integer.parseInt(discount.substring(0, discount.length() - 1));//这里取到的折扣率就是80
				
			if (redisClientUtil.getSourceClick(company + sourceName + sf1.format(d) + "xiaodaiKey") == null) {
				uv = 0;
			} else {
				uv = Integer.parseInt(redisClientUtil.getSourceClick(company + sourceName + sf1.format(d) + "xiaodaiKey"));
			}
			
			if (appnum >= 30) {
				int overtop=(int)appnum-30;//overtop是当前申请数超过30的那部分数量
				disAppnum=(int)Math.ceil((overtop * discount1 *1.0/ 100+30));// 申请数

			} else {
				disAppnum=appnum;// 申请数
			}
			
			if ((appnum < 0.000001) || (uv == 0)) {
				cvr = 0 + "%";// 得到转化率
			} else {
				cvr = (new DecimalFormat("#.00").format(disAppnum / uv * 100)) + "%";// 得到转化率
			}
		}else{
			float appnumHistory=tongjiSorcelist.getRegisternumdis();//历史表折扣后的注册人数
			String startTimestamps1=tongjiSorcelist.getDate();
			float appnum = intSourceService.queryApplicationNumberlike(companyId, sourceid, startTimestamps1, endTimestamps);// 得到申请数(该渠道当天在user表的注册数)
			String discount = intSourceService.queryDiscount(sourceid, companyId);// 得到折扣率  （比如取到字符串  "80%"）
			int discount1 = Integer.parseInt(discount.substring(0, discount.length() - 1));//这里取到的折扣率就是80
				
			if (redisClientUtil.getSourceClick(company + sourceName + sf1.format(d) + "xiaodaiKey") == null) {
				uv = 0;
			} else {
				uv = Integer.parseInt(redisClientUtil.getSourceClick(company + sourceName + sf1.format(d) + "xiaodaiKey"));
			}
			
			if (appnum >= 30) {
				int overtop=(int)appnum-30;//overtop是当前申请数超过30的那部分数量
				disAppnum=(int)Math.ceil((overtop * discount1 *1.0/ 100+30))+appnumHistory;// 申请数

			} else {
				disAppnum=appnum+appnumHistory;// 申请数
			}
			
			if ((appnum < 0.000001) || (uv == 0)) {
				cvr = 0 + "%";// 得到转化率
			} else {
				cvr = (new DecimalFormat("#.00").format(disAppnum / uv * 100)) + "%";// 得到转化率
			}
		}
		
		applynum=intSourceService.queryNum(companyId, sourceid,startTimestamps, endTimestamps);//申请人数
		orderpass=intSourceService.queryorderpass(sourceid, startTimestamps, endTimestamps);//已借款人数
		
		TongjiSorce tongjiSorce = new TongjiSorce();
		tongjiSorce.setDate(date);// 日期
		tongjiSorce.setSourcename(sourceName);// 渠道名称
		tongjiSorce.setUv(uv);// uv
		tongjiSorce.setCvr(cvr);// 转化率
		tongjiSorce.setRegisternumdis(disAppnum);
		tongjiSorce.setApplynum(applynum);
		tongjiSorce.setOrderpass(orderpass);
		listsource.add(tongjiSorce);
		
		DateListUtil.ListSort1(listsource);//将集合按照日期进行倒排序
		
		ListPageUtil listPageUtil = new ListPageUtil(listsource, page, 10);
		listsourceto.addAll(listPageUtil.getData());
		
		PageUtil2 pageUtil = new PageUtil2(listPageUtil.getCurrentPage(), listPageUtil.getPageSize(),listPageUtil.getTotalCount());
		map.put("listsourceto", listsourceto);
		map.put("pageutil", pageUtil);
		return map;
	}
	
	
	@ResponseBody
	@RequestMapping("/queryAllTongjiByDate")
	public TongjiSorce queryAllTongjiByDate(Integer companyId,Integer sourceid,String sourceName,String date) throws ParseException{
		String company=intSourceService.querycompany(companyId);
		RedisClientUtil redisClientUtil=new RedisClientUtil();
		TongjiSorce tongjiSorce = new TongjiSorce();
		
		String startTime = date;
		String startTimestamps = Timestamps.dateToStamp(startTime);
		String endTime = date;
		String endTimestamps = (Long.parseLong(Timestamps.dateToStamp(endTime))+86400000)+"";
		
		TongjiSorce tongjiSorcelist=intSourceService.queryBySourcenameAndDate(sourceid, startTimestamps,endTimestamps);
		
		Date d=new Date();
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
		String datetoday=sf.format(d);//date为当天时间(格式为年月日)
		
		int uv = 0;
		String cvr = null;
		float disAppnum=0;//折扣申请数
		Integer applynum=0;//申请数
		int orderpass=0;//借款人数
		if(!date.equals(datetoday)){//证明传进来的日期不是今天
			if(tongjiSorcelist!=null){
				tongjiSorce=tongjiSorcelist;
				String startTimestamps1 = tongjiSorce.getDate();
				String endTimestamps1 = (Long.parseLong(tongjiSorce.getDate())+86400000)+"";
				applynum=intSourceService.queryNum(companyId, sourceid,startTimestamps1, endTimestamps1);//申请人数
				tongjiSorce.setApplynum(applynum);
				orderpass=intSourceService.queryorderpass(sourceid, startTimestamps1, endTimestamps1);//已借款人数
				tongjiSorce.setOrderpass(orderpass);
				
				tongjiSorce.setDate(Timestamps.stampToDate1(tongjiSorce.getDate()));
				tongjiSorce.setSourcename(sourceName);
			}
		}else{//证明传进来的日期是今天
			if(tongjiSorcelist!=null){//证明当天历史表有数据
				float appnumHistory=tongjiSorcelist.getRegisternumdis();//历史表折扣后的注册人数
				String startTimestamps1=tongjiSorcelist.getDate();
				float appnum = intSourceService.queryApplicationNumberlike(companyId, sourceid,startTimestamps1,endTimestamps);// 得到申请数
				String discount = intSourceService.queryDiscount(sourceid, companyId);// 得到折扣率
				int discount1 = Integer.parseInt(discount.substring(0, discount.length() - 1));
					
				if (redisClientUtil.getSourceClick(company + sourceName + date.replace("-", "/") + "xiaodaiKey") == null) {
					uv = 0;
				} else {
					uv = Integer.parseInt(redisClientUtil.getSourceClick(company + sourceName+ date.replace("-", "/") + "xiaodaiKey"));
				}
				
				if (appnum >= 30) {
					int overtop=(int)appnum-30;//overtop是当前申请数超过30的那部分数量
					disAppnum=((int)Math.ceil((overtop * discount1 *1.0/ 100+30)))+appnumHistory;// 申请数
				} else {
					disAppnum=appnum+appnumHistory;// 申请数
				}
					
				if ((appnum < 0.000001) || (uv == 0)) {
					cvr = 0 + "%";// 得到转化率
				} else {
					cvr = (new DecimalFormat("#.00").format(disAppnum / uv * 100)) + "%";// 得到转化率
				}
			}else{//证明当天历史表没数据
				float appnum = intSourceService.queryApplicationNumberlike(companyId, sourceid,startTimestamps,endTimestamps);// 得到申请数
				String discount = intSourceService.queryDiscount(sourceid, companyId);// 得到折扣率
				int discount1 = Integer.parseInt(discount.substring(0, discount.length() - 1));
					
				if (redisClientUtil.getSourceClick(company + sourceName + date.replace("-", "/") + "xiaodaiKey") == null) {
					uv = 0;
				} else {
					uv = Integer.parseInt(redisClientUtil.getSourceClick(company + sourceName+ date.replace("-", "/") + "xiaodaiKey"));
				}
				
				if (appnum >= 30) {
					int overtop=(int)appnum-30;//overtop是当前申请数超过30的那部分数量
					disAppnum=((int)Math.ceil((overtop * discount1 *1.0/ 100+30)));// 申请数
				} else {
					disAppnum=appnum;// 申请数
				}
					
				if ((appnum < 0.000001) || (uv == 0)) {
					cvr = 0 + "%";// 得到转化率
				} else {
					cvr = (new DecimalFormat("#.00").format(disAppnum / uv * 100)) + "%";// 得到转化率
				}
			}
			
			applynum=intSourceService.queryNum(companyId, sourceid,startTimestamps, endTimestamps);//申请人数
			orderpass=intSourceService.queryorderpass(sourceid, startTimestamps, endTimestamps);//已借款人数
		
			tongjiSorce.setDate(date);// 日期
			tongjiSorce.setSourcename(sourceName);// 渠道名称
			tongjiSorce.setUv(uv);// uv
			tongjiSorce.setRegisternumdis(disAppnum);
			tongjiSorce.setCvr(cvr);// 转化率
			tongjiSorce.setApplynum(applynum);
			tongjiSorce.setOrderpass(orderpass);
		}
		return tongjiSorce;
	}
}
