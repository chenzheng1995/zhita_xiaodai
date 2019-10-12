package com.zhita.service.manage.user;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.zhita.dao.manage.BlacklistUserMapper;
import com.zhita.dao.manage.MaillistMapper;
import com.zhita.dao.manage.OrdersMapper;
import com.zhita.dao.manage.UserMapper;
import com.zhita.model.manage.Bankcard;
import com.zhita.model.manage.BlacklistUser;
import com.zhita.model.manage.DeferredAndOrder;
import com.zhita.model.manage.Maillist;
import com.zhita.model.manage.OrderQueryParameter;
import com.zhita.model.manage.Orders;
import com.zhita.model.manage.Source;
import com.zhita.model.manage.User;
import com.zhita.model.manage.UserAttestation;
import com.zhita.model.manage.UserLikeParameter;
import com.zhita.util.PageUtil2;
import com.zhita.util.PhoneDeal;
import com.zhita.util.PostAndGet;
import com.zhita.util.Timestamps;
import com.zhita.util.TuoMinUtil;

@Service
public class UserServiceImp implements IntUserService{
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private OrdersMapper ordersMapper;
	@Autowired
	private BlacklistUserMapper blacklistUserMapper;
	
	@Autowired
	private MaillistMapper maillistMapper;
	
	//后台管理----用户列表(公司id，page,姓名，手机号，注册开始时间，注册结束时间，用户认证状态，银行卡认证状态，运营商认证状态)
	public Map<String, Object> queryUserList(UserLikeParameter userLikeParameter){
		PhoneDeal pd = new PhoneDeal();//手机号加密解密工具类
		TuoMinUtil tm = new TuoMinUtil();//脱敏工具类
		if((userLikeParameter.getRegisteTimeStart()!=null&&!"".equals(userLikeParameter.getRegisteTimeStart()))&&(userLikeParameter.getRegisteTimeEnd()!=null&&!"".equals(userLikeParameter.getRegisteTimeEnd()))){
				try {
					userLikeParameter.setRegisteTimeStart(Timestamps.dateToStamp(userLikeParameter.getRegisteTimeStart()));
					userLikeParameter.setRegisteTimeEnd((Long.parseLong(Timestamps.dateToStamp(userLikeParameter.getRegisteTimeEnd()))+86400000)+"");
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		if(userLikeParameter.getPhone()!=null&&!"".equals(userLikeParameter.getPhone())){
			userLikeParameter.setPhone(pd.encryption(userLikeParameter.getPhone()));
		}
		int companyId=userLikeParameter.getCompanyId();//页面传过来的公司id
		int page=userLikeParameter.getPage();//页面传进来的当前页
		int totalCount=userMapper.queryUserListcount(userLikeParameter);//查询总条数
		PageUtil2 pageUtil=new PageUtil2(page,totalCount);
    	if(page<1) {
    		page=1;
    		pageUtil.setPage(page);
    	}
    	else if(page>pageUtil.getTotalPageCount()) {
    		if(totalCount==0) {
    			page=pageUtil.getTotalPageCount()+1;
    		}else {
    			page=pageUtil.getTotalPageCount();
    		}
    		pageUtil.setPage(page);
    	}
    	int pages=(page-1)*pageUtil.getPageSize();
    	userLikeParameter.setPage(pages);
    	userLikeParameter.setPagesize(pageUtil.getPageSize());
    	List<User> list=userMapper.queryUserList(userLikeParameter);//查询list集合
    	for (int i = 0; i <list.size(); i++) {
    		list.get(i).setPhone(tm.mobileEncrypt(pd.decryption(list.get(i).getPhone())));//将手机号进行脱敏
    		list.get(i).setRegistetime(Timestamps.stampToDate(list.get(i).getRegistetime()));
    		list.get(i).setLogintime(Timestamps.stampToDate(list.get(i).getLogintime()));
    		if(list.get(i).getUserattestationstatus()==null||"".equals(list.get(i).getUserattestationstatus())){
    			list.get(i).setUserattestationstatus("0");
    		}
    		if(list.get(i).getBankattestationstatus()==null||"".equals(list.get(i).getBankattestationstatus())){
    			list.get(i).setBankattestationstatus("0");
    		}
    		if(list.get(i).getOperaattestationstatus()==null||"".equals(list.get(i).getOperaattestationstatus())){
    			list.get(i).setOperaattestationstatus("0");
    		}
		}
    	List<Source> listsource=ordersMapper.querysource(companyId);	
		Map<String,Object> map=new HashMap<>();
		map.put("userlist", list);
		map.put("listsource", listsource);
		map.put("pageutil", pageUtil);
		return map;
		
	}
	//后台管理----用户列表(公司id，page,pagesize,姓名，手机号，注册开始时间，注册结束时间，用户认证状态，银行卡认证状态，运营商认证状态)——查询数量
	public int queryUserListcount(UserLikeParameter userLikeParameter){
		return userMapper.queryUserListcount(userLikeParameter);
	}
			
	//后台管理----用户列表（导出Excel）
	public List<User> queryUserListExcel(UserLikeParameter userLikeParameter){
		return userMapper.queryUserListExcel(userLikeParameter);
	}
	
	//后台管理---添加黑名单-人工添加
	@Transactional
	public int insertBlacklist(Integer companyId,Integer userId,Integer operator){
		PhoneDeal pd = new PhoneDeal();//手机号加密解密工具类
		Orders orders=userMapper.qeuryorder(userId);
		if(orders==null){
			userMapper.upaBlacklistStatus(userId);//修改该用户在用户表的黑名单状态
			String operationTime=System.currentTimeMillis()+"";//获取当前时间戳
			String blackType="8";//黑名单类型（8：人工添加）
			BlacklistUser blacklistUser=userMapper.queryByUserid(userId);
			blacklistUser.setPhone(pd.decryption(blacklistUser.getPhone()));
			blacklistUser.setCompanyid(companyId);
			blacklistUser.setOperator(operator);
			blacklistUser.setOperationtime(operationTime);
			blacklistUser.setBlackType(blackType);
			blacklistUser.setUserid(userId);
			blacklistUserMapper.insert(blacklistUser);//将该用户添加进黑名单表
			return 1;
		}else{
			return 0;
		}
	}
	
	//后台管理---添加黑名单-人审拒绝
	@Transactional
	public int insertBlacklistno(Integer companyId,Integer userId,Integer operator){
		PhoneDeal pd = new PhoneDeal();//手机号加密解密工具类
		Orders orders=userMapper.qeuryorder(userId);
		if(orders==null){
			userMapper.upaBlacklistStatus(userId);//修改该用户在用户表的黑名单状态
			String operationTime=System.currentTimeMillis()+"";//获取当前时间戳
			String blackType="6";//黑名单类型（6：人审拒绝）
			BlacklistUser blacklistUser=userMapper.queryByUserid(userId);
			blacklistUser.setPhone(pd.decryption(blacklistUser.getPhone()));
			blacklistUser.setCompanyid(companyId);
			blacklistUser.setOperator(operator);
			blacklistUser.setOperationtime(operationTime);
			blacklistUser.setBlackType(blackType);
			blacklistUser.setUserid(userId);
			blacklistUserMapper.insert(blacklistUser);//将该用户添加进黑名单表
			return 1;
		}else{
			return 0;
		}
	}
	
	//后台管理---解除黑名单
	@Transactional
	public int removeBlacklist(Integer companyId,Integer userId){
		int num=userMapper.upaBlacklistStatus1(userId);
		userMapper.upaBlacklist(userId);
		return num;
	}
	
	//后台管理----用户认证信息——借款信息    订单 查询（公司id，page,订单号，姓名，手机号，注册开始时间，注册结束时间     渠道id  用户id）
  	public Map<String,Object> queryAllOrdersByUserid(OrderQueryParameter orderQueryParameter){
  		PhoneDeal pd = new PhoneDeal();//手机号加密解密工具类
		TuoMinUtil tm = new TuoMinUtil();//脱敏工具类
		if((orderQueryParameter.getRegistestarttime()!=null&&!"".equals(orderQueryParameter.getRegistestarttime()))&&(orderQueryParameter.getRegisteendtime()!=null&&!"".equals(orderQueryParameter.getRegisteendtime()))){
			try {
				orderQueryParameter.setRegistestarttime(Timestamps.dateToStamp(orderQueryParameter.getRegistestarttime()));
				orderQueryParameter.setRegisteendtime((Long.parseLong(Timestamps.dateToStamp(orderQueryParameter.getRegisteendtime()))+86400000)+"");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(orderQueryParameter.getPhone()!=null&&!"".equals(orderQueryParameter.getPhone())){
			orderQueryParameter.setPhone(pd.encryption(orderQueryParameter.getPhone()));
		}
		int page=orderQueryParameter.getPage();//页面传进来的当前页
		int companyId=orderQueryParameter.getCompanyid();//公司id
		int totalCount=ordersMapper.queryAllOrdersByUseridcount(orderQueryParameter);//查询总条数
		PageUtil2 pageUtil=new PageUtil2(page,totalCount);
    	if(page<1) {
    		page=1;
    		pageUtil.setPage(page);
    	}
    	else if(page>pageUtil.getTotalPageCount()) {
    		if(totalCount==0) {
    			page=pageUtil.getTotalPageCount()+1;
    		}else {
    			page=pageUtil.getTotalPageCount();
    		}
    		pageUtil.setPage(page);
    	}
    	int pages=(page-1)*pageUtil.getPageSize();
    	orderQueryParameter.setPage(pages);
    	orderQueryParameter.setPagesize(pageUtil.getPageSize());
    	List<Orders> list=ordersMapper.queryAllOrdersByUserid(orderQueryParameter);//查询list集合
    	for (int i = 0; i <list.size(); i++) {
    		list.get(i).getUser().setPhone(tm.mobileEncrypt(pd.decryption(list.get(i).getUser().getPhone())));//将手机号进行脱敏
    		list.get(i).setShouldReturnTime(Timestamps.stampToDate(list.get(i).getShouldReturnTime()));
    		list.get(i).setOrderCreateTime(Timestamps.stampToDate(list.get(i).getOrderCreateTime()));
    		list.get(i).getUser().setRegistetime(Timestamps.stampToDate(list.get(i).getUser().getRegistetime()));
    		
    		//list.get(i).setHowManyTimesBorMoney(ordersMapper.queryHow(list.get(i).getUserId()));//第几次借款
    		
    		List<DeferredAndOrder> listdefer=ordersMapper.queryDefer(list.get(i).getId());
    		BigDecimal deferrMoney = new BigDecimal("0.00");//延期金额
			if(listdefer.size()!=0){
				list.get(i).setDeferrTime(listdefer.size());//延期次数
				for (int j = 0; j < listdefer.size(); j++) {
					deferrMoney=deferrMoney.add(listdefer.get(j).getInterestOnArrears());
				}
				list.get(i).setDeferrMoney(deferrMoney);
				Orders os=ordersMapper.qeuryFinalDefertime(list.get(i).getId());
				list.get(i).setDeferAfterReturntime(Timestamps.stampToDate(os.getDeferAfterReturntime()));//延期后还款时间
				list.get(i).setPostponeDate(os.getPostponeDate());//每次延期的天数
			}
		}
    	 List<Source> listsource=ordersMapper.querysource(companyId);	
		Map<String,Object> map=new HashMap<>();
		map.put("listorders", list);
		map.put("listsource", listsource);
		map.put("pageutil", pageUtil);
		return map;
	}
  	
	//后台管理----订单 查询（公司id  page，姓名，手机号，身份证号，注册开始时间，注册结束时间     渠道id）——黑名单用户  机审判定黑名单
  	public Map<String,Object> queryAllOrdersByUserid1(OrderQueryParameter orderQueryParameter){
		PhoneDeal pd = new PhoneDeal();//手机号加密解密工具类
		TuoMinUtil tm = new TuoMinUtil();//脱敏工具类
		if((orderQueryParameter.getRegistestarttime()!=null&&!"".equals(orderQueryParameter.getRegistestarttime()))&&(orderQueryParameter.getRegisteendtime()!=null&&!"".equals(orderQueryParameter.getRegisteendtime()))){
			try {
				orderQueryParameter.setRegistestarttime(Timestamps.dateToStamp(orderQueryParameter.getRegistestarttime()));
				orderQueryParameter.setRegisteendtime((Long.parseLong(Timestamps.dateToStamp(orderQueryParameter.getRegisteendtime()))+86400000)+"");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(orderQueryParameter.getPhone()!=null&&!"".equals(orderQueryParameter.getPhone())){
			orderQueryParameter.setPhone(pd.encryption(orderQueryParameter.getPhone()));
		}
		int page=orderQueryParameter.getPage();//页面传进来的当前页
		int companyId=orderQueryParameter.getCompanyid();//公司id
		int totalCount=ordersMapper.queryAllOrdersByUserid1count(orderQueryParameter);//查询总条数
		PageUtil2 pageUtil=new PageUtil2(page,totalCount);
    	if(page<1) {
    		page=1;
    		pageUtil.setPage(page);
    	}
    	else if(page>pageUtil.getTotalPageCount()) {
    		if(totalCount==0) {
    			page=pageUtil.getTotalPageCount()+1;
    		}else {
    			page=pageUtil.getTotalPageCount();
    		}
    		pageUtil.setPage(page);
    	}
    	int pages=(page-1)*pageUtil.getPageSize();
    	orderQueryParameter.setPage(pages);
    	orderQueryParameter.setPagesize(pageUtil.getPageSize());
    	List<Orders> list=ordersMapper.queryAllOrdersByUserid1(orderQueryParameter);//查询list集合
    	for (int i = 0; i <list.size(); i++) {
    		list.get(i).getUser().setPhone(tm.mobileEncrypt(pd.decryption(list.get(i).getUser().getPhone())));//将手机号进行脱敏
    		list.get(i).getUser().setRegistetime(Timestamps.stampToDate(list.get(i).getUser().getRegistetime()));;
    		list.get(i).setOrderCreateTime(Timestamps.stampToDate(list.get(i).getOrderCreateTime()));
    		/*List<DeferredAndOrder> listdefer=ordersMapper.queryDefer(list.get(i).getId());
			if(listdefer.size()!=0){
				list.get(i).setDeferrTime(listdefer.size());//延期次数
				list.get(i).setDeferAfterReturntime(Timestamps.stampToDate(listdefer.get(listdefer.size()-1).getDeferAfterReturntime()));//延期后还款时间
			}*/
    		
    		list.get(i).setHowManyTimesBorMoney(ordersMapper.queryHow(list.get(i).getUserId()));//第几次借款
    		
    		List<DeferredAndOrder> listdefer=ordersMapper.queryDefer(list.get(i).getId());
    		BigDecimal deferrMoney = new BigDecimal("0.00");//延期金额
			if(listdefer.size()!=0){
				list.get(i).setDeferrTime(listdefer.size());//延期次数
				for (int j = 0; j < listdefer.size(); j++) {
					deferrMoney=deferrMoney.add(listdefer.get(j).getInterestOnArrears());
				}
				list.get(i).setDeferrMoney(deferrMoney);
				Orders os=ordersMapper.qeuryFinalDefertime(list.get(i).getId());
				list.get(i).setDeferAfterReturntime(Timestamps.stampToDate(os.getDeferAfterReturntime()));//延期后还款时间
				list.get(i).setPostponeDate(os.getPostponeDate());//每次延期的天数
			}
    		
		}
    	 List<Source> listsource=ordersMapper.querysource(companyId);	
		Map<String,Object> map=new HashMap<>();
		map.put("listorders", list);
		map.put("listsource", listsource);
		map.put("pageutil", pageUtil);
		return map;
	}
  	
	//后台管理----订单 查询（公司id，姓名，手机号，身份证号，注册开始时间，注册结束时间     渠道id）——查询数量
  	public int queryAllOrdersByUserid1count(OrderQueryParameter orderQueryParameter){
  		return ordersMapper.queryAllOrdersByUserid1count(orderQueryParameter);
  	}
  	
  	//后台管理----订单 查询（导出Excel）
  	public List<Orders> queryAllOrdersByUserid1Excel(OrderQueryParameter orderQueryParameter){
  		return ordersMapper.queryAllOrdersByUserid1Excel(orderQueryParameter);
  	}
  	
	//后台管理---用户认证信息
	public Map<String,Object> queryUserAttesta(Integer userid){
		List<Maillist> listmaill=maillistMapper.queryByUserid(userid);//通讯录信息
		PhoneDeal pd = new PhoneDeal();//手机号加密解密工具类
		UserAttestation userAttestation=userMapper.queryUserAttesta(userid);//用户认证信息对象
		if(userAttestation!=null){
			userAttestation.setPhone(pd.decryption(userAttestation.getPhone()));//解密手机号
			Calendar date = Calendar.getInstance();
			String dateyear = String.valueOf(date.get(Calendar.YEAR));//获取当前系统的年份
			String idcardyear=userAttestation.getBirthYear();
			int age=Integer.parseInt(dateyear)-Integer.parseInt(idcardyear);
			userAttestation.setAge(age);//年龄
			userAttestation.setProvince(userAttestation.getAddress().substring(0,3));//省份
		}
		
		Bankcard bankcard=userMapper.queryBankcard(userid);//用户银行卡信息
		
		//Operator operator=userMapper.queryOperator(userid);//运营商信息
		PostAndGet pGet = new PostAndGet();
		String rString = pGet.sendGet("http://fk.rong51dai.com/zhita_heitong_Fengkong/authen/queryauthen?userid="+userid);
		JSONObject object = JSONObject.parseObject(rString);
		System.out.println("*************************************************"+rString);
		if(null==object){
			System.out.println("object is null");
		}
		HashMap<String,Object> map=new HashMap<>();
		map.put("listmaill", listmaill);
		map.put("userAttestation", userAttestation);
		map.put("bankcard",bankcard );
		//map.put("listapplier", object.getString("listapplier"));//申请人基本信息
		//map.put("listbill", object.getString("listbill"));//消费记录
		//map.put("listcommonth", object.getString("listcommonth"));//通话月份分布
		//map.put("listcombuck", object.getString("listcombuck"));//通话时间段分布
		//map.put("listcomdur", object.getString("listcomdur"));//通话时长分布
		//map.put("listrech", object.getString("listrech"));//充值记录
		//map.put("listrepo", object.getString("listrepo"));//报告基本信息
		//map.put("listsoc", object.getString("listsoc"));//社交关系
		//map.put("top10call", object.getString("top10call"));//通话次数前10 表
		//map.put("top10time", object.getString("top10time"));//通话总时长前10表
		//map.put("top10sing", object.getString("top10sing"));//单次通话时长前10 表
		map.put("listopera", object.getString("listopera"));//运营商基本信息
		map.put("listcomdet", object.getString("listcomdet"));//通信检测
		map.put("listemecon", object.getString("listemecon"));//紧急联系人
		map.put("listcomcity", object.getString("listcomcity"));//通话区域分布(省级)
		//map.put("operator", operator);
		return map;
	}
	
	//后台管理---用户认证信息
	public Map<String,Object> queryauthenconcity(Integer userid,Integer page){
		PostAndGet pGet = new PostAndGet();
		String rString = pGet.sendGet("http://fk.rong51dai.com/zhita_heitong_Fengkong/authen/queryauthenconcity?userid="+userid+"&page="+page);
		JSONObject object = JSONObject.parseObject(rString);
		
		HashMap<String,Object> map=new HashMap<>();
		map.put("listconcity", object.getString("listconcity"));//通话区域分布（城市）表
		map.put("pageUtil", object.getString("pageUtil"));
		return map;
	}
	
	//后台管理---用户认证信息
	public Map<String,Object> queryauthenave(Integer userid,Integer page){
		PostAndGet pGet = new PostAndGet();
		String rString = pGet.sendGet("http://fk.rong51dai.com/zhita_heitong_Fengkong/authen/queryauthenave?userid="+userid+"&page="+page);
		JSONObject object = JSONObject.parseObject(rString);
			
		HashMap<String,Object> map=new HashMap<>();
		map.put("listave", object.getString("listave"));//出行分析表
		map.put("pageUtil", object.getString("pageUtil"));
		return map;
	}
	
	//后台管理---用户认证信息
	public Map<String,Object> queryauthenlabel(Integer userid,Integer page){
		PostAndGet pGet = new PostAndGet();
		String rString = pGet.sendGet("http://fk.rong51dai.com/zhita_heitong_Fengkong/authen/queryauthenconlabel?userid="+userid+"&page="+page);
		JSONObject object = JSONObject.parseObject(rString);
				
		HashMap<String,Object> map=new HashMap<>();
		map.put("listlabel", object.getString("listlabel"));//通话数据分析表
		map.put("pageUtil", object.getString("pageUtil"));
		return map;
	}
	

	@Override
	public void updateScore(int score,int userId,String shareOfState) {
		userMapper.updateScore(score,userId,shareOfState);
		
	}

	@Override
	public Integer getRiskControlPoints(int userId) {
		 Integer riskControlPoints = userMapper.getRiskControlPoints(userId);
		return riskControlPoints;
	}

	@Override
	public String getshareOfState(int userId) {
		String shareOfState = userMapper.getshareOfState(userId);
		return shareOfState;
	}

	@Override
	public int getdelayTimes(int userId) {
		int currentDelays = userMapper.getdelayTimes(userId);
		return currentDelays;
	}

	@Override
	public void updateshareOfState(int userId, String shareOfState) {
		userMapper.updateshareOfState(userId, shareOfState);
		
	}

	@Override
	public void setuser(int userId, String timStamp, String applynumber) {
		userMapper.setuser(userId,timStamp,applynumber);
		
	}

	@Override
	public void updatename(String name, int userId) {
		userMapper.updatename(name,userId);
		
	}

	@Override
	public void updateifBlacklist(int userId) {
		userMapper.updateifBlacklist(userId);
		
	}

	@Override
	public String getphone(int id) {
		String phone1 = userMapper.getphone(id);
		return phone1;
	}

	@Override
	public void updateCanBorrowLines(BigDecimal finalLine, int userId) {
		userMapper.updateCanBorrowLines(finalLine,userId);
		
	}

	@Override
	public BigDecimal getcanBorrowLines(Integer userId) {
		BigDecimal canBorrowlines = userMapper.getcanBorrowLines(userId);
		return canBorrowlines;
	}

	@Override
	public int getsourceId(int userId) {
		int sourceId = userMapper.getsourceId(userId);
		return sourceId;
	}

	@Override
	public void updateScore1(int userId, String shareOfState) {
		userMapper.updateScore1(userId,shareOfState);
		
	}

	@Override
	public String getifBlacklist2(int userId) {
		 String ifBlacklist = userMapper.getifBlacklist2(userId);
		return ifBlacklist;
	}

	@Override
	public String getapplynumber(int userId) {
		String applynumber = userMapper.getapplynumber(userId);
		return applynumber;
	}

	@Override
	public void updateuser(int userId, String timStamp, String applynumber) {
		userMapper.updateuser(userId, timStamp, applynumber);
		
	}

	@Override
	public void updateapplyState(String applyState, int userId) {
		userMapper.updateapplyState(applyState,userId);
		
	}

	@Override
	public String getapplyState(int userId) {
		String applyState = userMapper.getapplyState(userId);
		return applyState;
	}

	@Override
	public String getregisteTime(int userId) {
		String registeTime = userMapper.getregisteTime(userId);
		return registeTime;
	}

	@Override
	public void setRiskControlPoints(int userId,int riskControlPoints) {
		userMapper.setRiskControlPoints(userId,riskControlPoints);
		
	}

	@Override
	public void setModel(int userId, String rString) {
		userMapper.setModel(userId,rString);
		
	}

	@Override
	public String getModel(int userId) {
		 String model = userMapper.getModel(userId);
		return model;
	}
	@Override
	public void updateUserAuthenStatus(int userId,String userAuthenStatus) {
		userMapper.updateUserAuthenStatus(userId,userAuthenStatus);
		
	}
	@Override
	public void updateOperatorAuthenStatus(String attestationStatus, int userId) {
		userMapper.updateOperatorAuthenStatus(attestationStatus, userId);
		
	}


}
