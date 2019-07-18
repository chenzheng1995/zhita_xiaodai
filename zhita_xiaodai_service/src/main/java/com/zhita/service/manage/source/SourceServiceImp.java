package com.zhita.service.manage.source;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhita.dao.manage.ManageControlSettingsMapper;
import com.zhita.dao.manage.OrdersMapper;
import com.zhita.dao.manage.SourceDiscountHistoryMapper;
import com.zhita.dao.manage.SourceMapper;
import com.zhita.dao.manage.SourceTemplateMapper;
import com.zhita.dao.manage.SysUserMapper;
import com.zhita.model.manage.Company;
import com.zhita.model.manage.ManageControlSettings;
import com.zhita.model.manage.Source;
import com.zhita.model.manage.SourceTemplate;
import com.zhita.model.manage.TongjiSorce;
import com.zhita.util.ListPageUtil;
import com.zhita.util.PageUtil;
import com.zhita.util.RedisClientUtil;
import com.zhita.util.Timestamps;

@Service
public class SourceServiceImp implements IntSourceService{
	@Autowired
	private SourceMapper sourceMapper;
	@Autowired
	private SysUserMapper sysUserMapper;
	@Autowired
	private ManageControlSettingsMapper manageControlSettingsMapper;
	@Autowired
	private SourceTemplateMapper sourceTemplateMapper;
	@Autowired
	private SourceDiscountHistoryMapper sourceDiscountHistoryMapper;
	@Autowired
	private OrdersMapper ordersMapper;
	
	//后台管理---查询渠道表所有信息
    public Map<String,Object> queryAll(Integer companyId,Integer page){
    	List<Source> list=new ArrayList<>();
    	List<Source> listto=new ArrayList<>();
    	PageUtil pageUtil=null;
    	list=sourceMapper.queryAll(companyId);
    	
    	if(list!=null && !list.isEmpty()){
    		ListPageUtil listPageUtil=new ListPageUtil(list,page,10);
    		listto.addAll(listPageUtil.getData());
    		
    		pageUtil=new PageUtil(listPageUtil.getCurrentPage(), listPageUtil.getPageSize());
    	}
    	
		HashMap<String,Object> map=new HashMap<>();
		map.put("sourcelist", listto);
		map.put("pageutil", pageUtil);
    	return map;
    }
    
    //后台管理---添加功能（查询出所有公司 模板和风控）
    public Map<String,Object> queryAllCompany(Integer companyId){
    	List<Company> listcom=sysUserMapper.queryAllCompany();
    	List<ManageControlSettings> listmanage=manageControlSettingsMapper.queryAll(companyId);
    	List<SourceTemplate> listtemp=sourceTemplateMapper.queryAllTemp();
    	HashMap<String,Object> map=new HashMap<>();
		map.put("listcom", listcom);
		map.put("listmanage", listmanage);
		map.put("listtemp", listtemp);
    	return map;
    }
    
    //后台管理---添加功能
    public int insert(Source record){
    	String templateName = record.getName();
    	Integer templateId = sourceTemplateMapper.getid(templateName);
    	record.setTemplateid(templateId);
    	record.setLink("http://tg.mis8888.com/promote/"+templateName+"/index.html?code="+record.getSourcename());
    	
    	int count=sourceMapper.ifSourceNameIfExist(record.getSourcename());
		int num=0;
		int num1=0;
		if(count==0){
			num=sourceMapper.insert(record);//添加渠道表信息	
			return num;
		}else{
			num1=sourceMapper.updateSource(record);
			return num1;
		}
    }
    
    //后台管理---根据id查询当前对象信息
    public Source selectByPrimaryKey(Integer id){
    	Source source=sourceMapper.selectByPrimaryKey(id);
    	return source;
    }
    
    //后台管理---编辑功能
    public int updateByPrimaryKey(Source record) throws ParseException{
    	Integer companyId=record.getCompanyid();
    	String templateName = record.getName();
    	Integer templateId = sourceTemplateMapper.getid(templateName);
    	record.setTemplateid(templateId);
    	record.setLink("http://tg.mis8888.com/promote/"+templateName+"/index.html?code="+record.getSourcename());
    	
    	String discount=sourceMapper.queryDiscountById(record.getId());//得到修改之前的那个折扣率  （比如取到字符串  "80%"）
		RedisClientUtil redisClientUtil = new RedisClientUtil();
		
		if((discount.equals(record.getDiscount())==false)){//等于false  说明折扣率被修改了
			Date d=new Date();
			SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat sf1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String date=sf.format(d);//date为当天时间(年月日)
			String dates=sf1.format(d);//dates为当天时间(年月日时分秒)
			
			String startTime1 = date;
			String startTimestamps1 = Timestamps.dateToStamp(startTime1);
			String endTime1 = date;
			String endTimestamps1 = (Long.parseLong(Timestamps.dateToStamp(endTime1))+86400000)+"";
			
			TongjiSorce tongjisourceyes=sourceDiscountHistoryMapper.queryBySourcenameAndDate(record.getSourcename(), startTimestamps1,endTimestamps1);//判断当前渠道今天在历史表是否存在数据
			
			if(tongjisourceyes==null){//证明当前渠道当天在历史表没有数据
				float appnum=sourceMapper.queryApplicationNumber(companyId, record.getSourcename(), startTimestamps1,endTimestamps1);// 得到申请数(该渠道当天在user表的注册数)
				int discount1 = Integer.parseInt(discount.substring(0, discount.length() - 1));//这里取到的折扣率就是80
				int uv = 0;//uv
				String cvr = null;//转化率
				float disAppnum=0;//折扣申请数
				
				if (redisClientUtil.getSourceClick(companyId + record.getSourcename() + date + "daichaoKey") == null) {
					uv = 0;
				} else {
					uv = Integer.parseInt(redisClientUtil.getSourceClick(companyId + record.getSourcename() + date + "daichaoKey"));
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
					cvr = (new DecimalFormat("#.00").format(disAppnum/ uv * 100)) + "%";// 得到转化率
				}
				
				
				TongjiSorce tongjiSorce=new TongjiSorce();
				tongjiSorce.setSourcename(record.getSourcename());
				tongjiSorce.setDate(Timestamps.dateToStamp1(dates));
				tongjiSorce.setUv(uv);
				tongjiSorce.setRegisternumdis(disAppnum);
				tongjiSorce.setCvr(cvr);
				sourceDiscountHistoryMapper.insert(tongjiSorce);
			
			}else{//证明当前渠道当天在历史表有数据
				float appnumHistory=tongjisourceyes.getRegisternumdis();//历史表折扣后的注册人数
				String startTimestamps = tongjisourceyes.getDate();//该时间为时间戳格式
				String endTime = date;
				String endTimestamps = (Long.parseLong(Timestamps.dateToStamp(endTime))+86400000)+"";
				
				float appnum=sourceMapper.queryApplicationNumber(companyId, record.getSourcename(), startTimestamps,endTimestamps);// 得到申请数(该渠道当天在user表的注册数)
				int discount1 = Integer.parseInt(discount.substring(0, discount.length() - 1));//这里取到的折扣率就是80
				int uv = 0;//uv
				String cvr = null;//转化率
				float disAppnum=0;//折扣申请数
				
				if (redisClientUtil.getSourceClick(companyId + record.getSourcename() + date + "daichaoKey") == null) {
					uv = 0;
				} else {
					uv = Integer.parseInt(redisClientUtil.getSourceClick(companyId + record.getSourcename() + date + "daichaoKey"));
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
					cvr = (new DecimalFormat("#.00").format(disAppnum/ uv * 100)) + "%";// 得到转化率
				}
				
				
				TongjiSorce tongjiSorce=new TongjiSorce();
				tongjiSorce.setSourcename(record.getSourcename());
				tongjiSorce.setDate(Timestamps.dateToStamp1(dates));
				tongjiSorce.setDate1(tongjisourceyes.getDate());
				tongjiSorce.setUv(uv);
				tongjiSorce.setRegisternumdis(disAppnum);
				tongjiSorce.setCvr(cvr);
				sourceDiscountHistoryMapper.updateByPrimaryKey(tongjiSorce);
			}
		}
    	
    	int num=sourceMapper.updateByPrimaryKey(record);
    	return num;
    }
    
    //后台管理---根据id  对当前对象的假删除状态进行修改
    public int updateFalDel(Integer id){
    	int num=sourceMapper.updateFalDel(id);
    	return num;
    }
    
    //后台管理---查询当天各个渠道在用户表的注册数量
    public List<TongjiSorce> queryAllSourceByUser(Integer companyid,String StartTime,String EndTime){
    	List<TongjiSorce> list=sourceMapper.queryAllSourceByUser(companyid, StartTime, EndTime);
    	return list;
    }
    
    //后台管理---查询当天各个渠道在用户表的注册数量(通过渠道查询)
    public List<TongjiSorce> queryAllSourceBySouce(Integer companyid,String StartTime,String EndTime,String sourcename){
    	List<TongjiSorce> list=sourceMapper.queryAllSourceBySouce(companyid, StartTime, EndTime, sourcename);
    	return list;
    }
    //后台管理---查询某一天某个渠道的注册数量
    public TongjiSorce queryAllSourceByUserDetail(Integer companyid,String StartTime,String EndTime,String sourceName){
    	TongjiSorce tongjisource=sourceMapper.queryAllSourceByUserDetail(companyid, StartTime, EndTime, sourceName);
    	return tongjisource;
    }
    
    //定时任务
    //后台管理----做定时任务需要执行的方法（每日0点  将各个渠道的历史数据存入历史表）
    public void queryAllTongji() throws ParseException{
    	RedisClientUtil redisClientUtil=new RedisClientUtil();//redis工具类
    	Integer companyId=3;
    	List<Source> list=sourceMapper.queryAll(companyId);//查询出当前公司下的所有渠道（所有渠道的集合list）
    	for (int i = 0; i < list.size(); i++) {
    		String sourceName=list.get(i).getSourcename();
    		List<String> liststr=sourceMapper.queryTime(companyId, sourceName);// 查询出当前渠道所有的注册时间(liststr里面的时间为时间戳格式)
    		
    		if(liststr!=null&&!liststr.isEmpty()) {//代表当前渠道在用户表有注册的用户
    			List<String> list1 = new ArrayList<>();// 用来存时间戳转换后的时间（年月日格式的时间）(user的注册时间)
    			for (int j = 0; j< liststr.size(); j++) {
    				list1.add(Timestamps.stampToDate1(liststr.get(j)));
    			}
    			HashSet h = new HashSet(list1);
    			list1.clear();
    			list1.addAll(h);
    			
    			Date d=new Date();
    			SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
    			String date=sf.format(d);//date为当天时间(格式为年月日)
    			
    			list1.remove(date);//将当天时间从list1中移除
    			List<String> listdate=sourceDiscountHistoryMapper.queryDate(sourceName);//当前渠道在历史表的时间（listdate里面的时间为时间戳格式）
    			
    			List<String> list2 = new ArrayList<>();// 用来存时间戳转换后的时间（年月日格式的时间）（历史表的时间）
    			for (int k = 0; k < listdate.size(); k++) {
    				list2.add(Timestamps.stampToDate1(listdate.get(k)));
    			}
    			HashSet h1 = new HashSet(list2);
    			list2.clear();
    			list2.addAll(h1);
    			
    			List<String> intersectionlist=list1.stream().filter(t-> !list2.contains(t)).collect(Collectors.toList());//差集
    			intersectionlist.stream().forEach(System.out::println);
    			
    			if(intersectionlist!=null&&!intersectionlist.isEmpty()){
    				for (int m = 0; m < intersectionlist.size(); m++) {
    					//String startTime = list1.get(i) + " " + "00:00:00";
    					String startTime = intersectionlist.get(m);
    					String startTimestamps = Timestamps.dateToStamp(startTime);
    					//String endTime = list1.get(i) + " " + "23:59:59";
    					String endTime = intersectionlist.get(m);
    					String endTimestamps = (Long.parseLong(Timestamps.dateToStamp(endTime))+86400000)+"";
    					float appnum = sourceMapper.queryApplicationNumber(companyId, sourceName, startTimestamps, endTimestamps);// 得到申请数(该渠道当天在user表的注册数)
    					String discount = sourceMapper.queryDiscount(sourceName, companyId);// 得到折扣率  （比如取到字符串  "80%"）
    					int discount1 = Integer.parseInt(discount.substring(0, discount.length() - 1));//这里取到的折扣率就是80
    					int uv = 0;//uv
    					String cvr = null;//转化率
    					float disAppnum=0;//折扣申请数
    					
    					if (redisClientUtil.getSourceClick(companyId + sourceName + intersectionlist.get(m) + "daichaoKey") == null) {
    						uv = 0;
    					} else {
    						uv = Integer.parseInt(redisClientUtil.getSourceClick(companyId + sourceName + intersectionlist.get(m) + "daichaoKey"));
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
    						cvr = (new DecimalFormat("#.00").format(disAppnum/ uv * 100)) + "%";// 得到转化率
    					}
    					
    					
    					TongjiSorce tongjiSorce=new TongjiSorce();
    					tongjiSorce.setSourcename(sourceName);
    					tongjiSorce.setDate(Timestamps.dateToStamp(intersectionlist.get(m)));
    					tongjiSorce.setUv(uv);
    					tongjiSorce.setRegisternumdis(disAppnum);
    					tongjiSorce.setCvr(cvr);
    					sourceDiscountHistoryMapper.insert(tongjiSorce);//往历史表插入数据
    				}
    			}
    		}
    		
    		//获取前一天的日期
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DATE, -1);
			String dateyes = df.format(calendar.getTime());
			System.out.println("今天日期的前一天："+dateyes);
    		
			String startTime1 = dateyes;
			String startTimestamps1 = Timestamps.dateToStamp(startTime1);//该时间为时间戳格式
			String endTime1 = dateyes;
			String endTimestamps1 = (Long.parseLong(Timestamps.dateToStamp(endTime1))+86400000)+"";
			
			TongjiSorce tongjisourceyes=sourceDiscountHistoryMapper.queryBySourcenameAndDate(sourceName, startTimestamps1,endTimestamps1);
			
			float appnumHistory=tongjisourceyes.getRegisternumdis();//历史表折扣后的注册人数
			String startTimestamps2 = tongjisourceyes.getDate();//该时间为时间戳格式
			
			float appnum = sourceMapper.queryApplicationNumber(companyId, sourceName, startTimestamps2, endTimestamps1);// 得到申请数(该渠道当天在user表的注册数)
			String discount = sourceMapper.queryDiscount(sourceName, companyId);// 得到折扣率  （比如取到字符串  "80%"）
			int discount1 = Integer.parseInt(discount.substring(0, discount.length() - 1));//这里取到的折扣率就是80
			int uv = 0;
			String cvr = null;
			float disAppnum=0;//折扣申请数
				
			if (redisClientUtil.getSourceClick(companyId + sourceName + dateyes + "daichaoKey") == null) {
				uv = 0;
			} else {
				uv = Integer.parseInt(redisClientUtil.getSourceClick(companyId + sourceName + dateyes + "daichaoKey"));
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
			
			TongjiSorce tongjiSorce=new TongjiSorce();
			tongjiSorce.setDate(Timestamps.dateToStamp(dateyes));// 日期
			tongjiSorce.setDate1(tongjisourceyes.getDate());
			tongjiSorce.setSourcename(sourceName);// 渠道名称
			tongjiSorce.setUv(uv);// uv
			tongjiSorce.setRegisternumdis(disAppnum);
			tongjiSorce.setCvr(cvr);// 转化率
			sourceDiscountHistoryMapper.updateByPrimaryKey(tongjiSorce);//说明修改成功
		}
	}
    		
    
    //后台管理----通过渠道名字查询当前渠道在历史表的信息
    public List<TongjiSorce> queryAllBySourceName(String sourceName){
    	List<TongjiSorce> list=sourceDiscountHistoryMapper.queryAllBySourceName(sourceName);
    	return list;
    }
    
    //后台管理 ------查询统计申请数 
    public int queryApplicationNumber(Integer companyId,String sourceName,String startTime,String endTime){
    	int appnum=sourceMapper.queryApplicationNumber(companyId, sourceName, startTime, endTime);
    	return appnum;
    }
    
    //后台管理---通过渠道名称查询出当前渠道的折扣率
    public String queryDiscount(String sourceName,Integer companyId){
    	String discount=sourceMapper.queryDiscount(sourceName, companyId);
    	return discount;
    }

	@Override
	public int getsourceId(String sourceName) {
		int merchantId = sourceMapper.getsourceId(sourceName);
		return merchantId;
	}
    
    //后台管理---通过渠道和时间查询在历史表是否有数据
    public TongjiSorce queryBySourcenameAndDate(String sourcename,String startdate,String enddate){
    	TongjiSorce tongjiSorce=sourceDiscountHistoryMapper.queryBySourcenameAndDate(sourcename, startdate, enddate);
    	return tongjiSorce;
    }
    //后台管理---查询当前渠道下有多少用户是登录过得
    public int queryCount(String sourceName,String startTime,String endTime){
    	int count=sourceMapper.queryCount(sourceName,startTime,endTime);
    	return count;
    }
    //后台管理---当前渠道下所有的用户id
    public List<Integer> queryUserid(String sourceName){
    	List<Integer> list=sourceMapper.queryUserid(sourceName);
    	return list;
    }
    //后台管理---查询当前用户id是否在个人信息认证表有值
    public int queryIfExist(Integer userid){
    	int count=sourceMapper.queryIfExist(userid);
    	return count;
    }
    //后台管理---查询当前用户id是否在银行卡表有值
    public int queryIfExist1(Integer userid){
    	int count=sourceMapper.queryIfExist1(userid);
    	return count;
    }
    //后台管理---查询当前用户id是否在运营商表有值
    public int queryIfExist2(Integer userid){
    	int count=sourceMapper.queryIfExist2(userid);
    	return count;
    }
  	//后台管理---渠道统计模块——申请人数字段
  	public int queryNum(Integer companyId,String sourcename){
  		int count=ordersMapper.queryNum(companyId, sourcename);
  		return count;
  	}
 	//后台管理---查询当前渠道所使用的风控       机审风控分数段的值
    public String querymancon(String sourceName){
    	String airappFractionalSegment=sourceMapper.querymancon(sourceName);
    	return airappFractionalSegment;
    }
    //后台管理---渠道统计模块——机审通过字段
   	public int queryNum1(Integer companyId,String sourcename,String startscore,String endscore){
   		int airappFractionalSegment=ordersMapper.queryNum1(companyId, sourcename, startscore, endscore);
   		return airappFractionalSegment;
   	}
}
