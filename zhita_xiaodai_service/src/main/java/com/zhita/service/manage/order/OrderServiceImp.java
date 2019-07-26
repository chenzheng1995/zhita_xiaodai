package com.zhita.service.manage.order;


import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhita.dao.manage.OrdersMapper;
import com.zhita.dao.manage.UserMapper;
import com.zhita.model.manage.DeferredAndOrder;
import com.zhita.model.manage.ManageControlSettings;
import com.zhita.model.manage.OrderQueryParameter;
import com.zhita.model.manage.Orders;
import com.zhita.model.manage.Source;
import com.zhita.model.manage.SysUser;
import com.zhita.model.manage.User;
import com.zhita.model.manage.UserLikeParameter;
import com.zhita.util.DateListUtil;
import com.zhita.util.ListPageUtil;
import com.zhita.util.PageUtil2;
import com.zhita.util.Timestamps;

@Service
public class OrderServiceImp implements IntOrderService{
	@Autowired
	private OrdersMapper ordersMapper;
	@Autowired
	private UserMapper userMapper;
	
	//后台管理----机审订单      (公司id，page，订单号，姓名，手机号，订单开始时间，订单结束时间，风控反馈)
	public Map<String, Object> queryatrOrders(OrderQueryParameter orderQueryParameter){
		if((orderQueryParameter.getOrderstarttime()!=null&&!"".equals(orderQueryParameter.getOrderstarttime()))&&(orderQueryParameter.getOrderendtime()!=null&&!"".equals(orderQueryParameter.getOrderendtime()))){
			try {
				orderQueryParameter.setOrderstarttime(Timestamps.dateToStamp(orderQueryParameter.getOrderstarttime()));
				orderQueryParameter.setOrderendtime((Long.parseLong(Timestamps.dateToStamp(orderQueryParameter.getOrderendtime()))+86400000)+"");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		List<Orders> listorder=new ArrayList<>();
		List<Orders> listorderfor=new ArrayList<>();
		List<Orders> listorderto=new ArrayList<>();
		PageUtil2 pageUtil=null;
		
		Integer companyId=orderQueryParameter.getCompanyid();//得到公司id
		Integer page=orderQueryParameter.getPage();//得到page
		List<String> list=ordersMapper.queryRiskcontrolname(companyId);//查询订单表所有的风控名
		for (int i = 0; i < list.size(); i++) {
			String riskcontrolname=list.get(i);//风控名
			orderQueryParameter.setRiskcontrolname(riskcontrolname);//将风控名封装进实体类
			ManageControlSettings manageControlSettings=ordersMapper.querymancon(companyId, riskcontrolname);//通过风控名查询风控信息
			String atrntlFractionalSegment=manageControlSettings.getAtrntlfractionalsegment();//机审拒绝不放款分数段
			String airappFractionalSegment=manageControlSettings.getAirappfractionalsegment();//机审通过分数段
			
			String[] str1=atrntlFractionalSegment.split("-");
			String atrStart=str1[0];
			String atrEnd=str1[1];
			
			String[] str2=airappFractionalSegment.split("-");
			String airStart=str2[0];
			String airEnd=str2[1];
			orderQueryParameter.setStart(atrEnd);//将开始时间封装进实体类
			orderQueryParameter.setEnd(airStart);//将结束时间封装进实体类
			
			listorder=ordersMapper.queryatrOrders(orderQueryParameter);
			listorderfor.addAll(listorder);
			
		}
		
	    if(listorderfor!=null && !listorderfor.isEmpty()){
	    	ListPageUtil listPageUtil=new ListPageUtil(listorderfor,page,10);
	    	listorderto.addAll(listPageUtil.getData());
	    	
	    	for (int i = 0; i < listorderto.size(); i++) {
				listorderto.get(i).setOrderCreateTime(Timestamps.stampToDate(listorderto.get(i).getOrderCreateTime()));
				List<DeferredAndOrder> listdefer=ordersMapper.queryDefer(listorderto.get(i).getId());
				if(listdefer.size()!=0){
					listorderto.get(i).setDeferrTime(listdefer.size());
					listorderto.get(i).setDeferAfterReturntime(listdefer.get(listdefer.size()-1).getDeferAfterReturntime());
				}
			}
		    DateListUtil.ListSort2(listorderto);
	    		

//	    	pageUtil=new PageUtil(listPageUtil.getCurrentPage(), listPageUtil.getPageSize());

	    	pageUtil=new PageUtil2(listPageUtil.getCurrentPage(), listPageUtil.getPageSize(),listPageUtil.getTotalCount());
	    }else{
	    	pageUtil=new PageUtil2(1, 10, 0);

	    }
	    
	    
	    
		HashMap<String,Object> map=new HashMap<>();
		map.put("listorderto", listorderto);
		map.put("pageutil", pageUtil);
		return map;
		
	}
	
	//后台管理----机审拒绝未人审订单     (公司id，page，订单号，姓名，手机号，订单开始时间，订单结束时间)
	public Map<String, Object> queryroaOrders(OrderQueryParameter orderQueryParameter){
		if((orderQueryParameter.getOrderstarttime()!=null&&!"".equals(orderQueryParameter.getOrderstarttime()))&&(orderQueryParameter.getOrderendtime()!=null&&!"".equals(orderQueryParameter.getOrderendtime()))){
			try {
				orderQueryParameter.setOrderstarttime(Timestamps.dateToStamp(orderQueryParameter.getOrderstarttime()));
				orderQueryParameter.setOrderendtime((Long.parseLong(Timestamps.dateToStamp(orderQueryParameter.getOrderendtime()))+86400000)+"");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		List<Orders> listorder=new ArrayList<>();
		List<Orders> listorderfor=new ArrayList<>();
		List<Orders> listorderto=new ArrayList<>();
		PageUtil2 pageUtil=null;
		
		Integer companyId=orderQueryParameter.getCompanyid();//得到公司id
		Integer page=orderQueryParameter.getPage();//得到page
		List<String> list=ordersMapper.queryRiskcontrolname(companyId);//查询订单表所有的风控名
		for (int i = 0; i < list.size(); i++) {
			String riskcontrolname=list.get(i);//风控名
			orderQueryParameter.setRiskcontrolname(riskcontrolname);//将风控名封装进实体类
			ManageControlSettings manageControlSettings=ordersMapper.querymancon(companyId, riskcontrolname);//通过风控名查询风控信息
			String roatnptFractionalSegment=manageControlSettings.getRoatnptfractionalsegment();//机审拒绝需人审分数段
			
			String[] str1=roatnptFractionalSegment.split("-");
			String roaStart=str1[0];
			String roaEnd=str1[1];
			orderQueryParameter.setStart(roaStart);//将开始时间封装进实体类
			orderQueryParameter.setEnd(roaEnd);//将结束时间封装进实体类
			
			
			listorder=ordersMapper.queryroaOrders(orderQueryParameter);
			listorderfor.addAll(listorder);
			
		}
		
		if(listorderfor!=null && !listorderfor.isEmpty()){
	    	ListPageUtil listPageUtil=new ListPageUtil(listorderfor,page,10);
	    	listorderto.addAll(listPageUtil.getData());
	    	
	    	for (int i = 0; i < listorderto.size(); i++) {
				listorderto.get(i).setOrderCreateTime(Timestamps.stampToDate(listorderto.get(i).getOrderCreateTime()));
				List<DeferredAndOrder> listdefer=ordersMapper.queryDefer(listorderto.get(i).getId());
				if(listdefer.size()!=0){
					listorderto.get(i).setDeferrTime(listdefer.size());
					listorderto.get(i).setDeferAfterReturntime(listdefer.get(listdefer.size()-1).getDeferAfterReturntime());
				}
			}
		    DateListUtil.ListSort2(listorderto);
	    		

//	    	pageUtil=new PageUtil(listPageUtil.getCurrentPage(), listPageUtil.getPageSize());

	    	pageUtil=new PageUtil2(listPageUtil.getCurrentPage(), listPageUtil.getPageSize(),listPageUtil.getTotalCount());
	    }else{
	    	pageUtil=new PageUtil2(1, 10, 0);

	    }
	    
		HashMap<String,Object> map=new HashMap<>();
		map.put("listorderto", listorderto);
		map.put("pageutil", pageUtil);
		return map;
	}
	
	//后台管理----机审拒绝未人审订单——审核通过操作
  	public int upaOrderIfpeopleWhose(Integer orderid,Integer assessor){
  		int num=ordersMapper.upaOrderIfpeopleWhose(orderid, assessor);
  		return num;
  	}
  	
    //后台管理----已机审已人审（公司id，订单号，姓名，手机号，订单开始时间，订单结束时间      审核员 id）
  	public Map<String, Object> queryroasOrders(OrderQueryParameter orderQueryParameter){
  		if((orderQueryParameter.getOrderstarttime()!=null&&!"".equals(orderQueryParameter.getOrderstarttime()))&&(orderQueryParameter.getOrderendtime()!=null&&!"".equals(orderQueryParameter.getOrderendtime()))){
			try {
				orderQueryParameter.setOrderstarttime(Timestamps.dateToStamp(orderQueryParameter.getOrderstarttime()));
				orderQueryParameter.setOrderendtime((Long.parseLong(Timestamps.dateToStamp(orderQueryParameter.getOrderendtime()))+86400000)+"");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		List<Orders> listorder=new ArrayList<>();
		List<Orders> listorderfor=new ArrayList<>();
		List<Orders> listorderto=new ArrayList<>();
		PageUtil2 pageUtil=null;
		
		Integer companyId=orderQueryParameter.getCompanyid();//得到公司id
		Integer page=orderQueryParameter.getPage();//得到page
		List<String> list=ordersMapper.queryRiskcontrolname(companyId);//查询订单表所有的风控名
		for (int i = 0; i < list.size(); i++) {
			String riskcontrolname=list.get(i);//风控名
			orderQueryParameter.setRiskcontrolname(riskcontrolname);//将风控名封装进实体类
			ManageControlSettings manageControlSettings=ordersMapper.querymancon(companyId, riskcontrolname);//通过风控名查询风控信息
			String roatnptFractionalSegment=manageControlSettings.getRoatnptfractionalsegment();//机审拒绝需人审分数段
			
			String[] str1=roatnptFractionalSegment.split("-");
			String roaStart=str1[0];
			String roaEnd=str1[1];
			orderQueryParameter.setStart(roaStart);//将开始时间封装进实体类
			orderQueryParameter.setEnd(roaEnd);//将结束时间封装进实体类
			
			listorder=ordersMapper.queryroasOrders(orderQueryParameter);
			listorderfor.addAll(listorder);
			
		}
		if(listorderfor!=null && !listorderfor.isEmpty()){
	    	ListPageUtil listPageUtil=new ListPageUtil(listorderfor,page,10);
	    	listorderto.addAll(listPageUtil.getData());
	    		

//	    	pageUtil=new PageUtil(listPageUtil.getCurrentPage(), listPageUtil.getPageSize());

	    	for (int i = 0; i < listorderto.size(); i++) {
				listorderto.get(i).setOrderCreateTime(Timestamps.stampToDate(listorderto.get(i).getOrderCreateTime()));
				List<DeferredAndOrder> listdefer=ordersMapper.queryDefer(listorderto.get(i).getId());
				if(listdefer.size()!=0){
					listorderto.get(i).setDeferrTime(listdefer.size());
					listorderto.get(i).setDeferAfterReturntime(listdefer.get(listdefer.size()-1).getDeferAfterReturntime());
				}
			}
		    DateListUtil.ListSort2(listorderto);
	    	
	    	pageUtil=new PageUtil2(listPageUtil.getCurrentPage(), listPageUtil.getPageSize(),listPageUtil.getTotalCount());
	    }else{
	    	pageUtil=new PageUtil2(1, 10, 0);

	    }
	    
	    
	    
	    List<SysUser> listacount=ordersMapper.queryname(companyId);
	    	
		HashMap<String,Object> map=new HashMap<>();
		map.put("listorderto", listorderto);
		map.put("operatorlist", listacount);
		map.put("pageutil", pageUtil);
		return map;
	
  	}
  	
  	//后台管理----订单 查询（公司id，page,订单号，姓名，手机号，注册开始时间，注册结束时间     渠道id）
  	public Map<String, Object> queryAllOrders(OrderQueryParameter orderQueryParameter){
  		if((orderQueryParameter.getRegistestarttime()!=null&&!"".equals(orderQueryParameter.getRegistestarttime()))&&(orderQueryParameter.getRegisteendtime()!=null&&!"".equals(orderQueryParameter.getRegisteendtime()))){
			try {
				orderQueryParameter.setRegistestarttime(Timestamps.dateToStamp(orderQueryParameter.getRegistestarttime()));
				orderQueryParameter.setRegisteendtime((Long.parseLong(Timestamps.dateToStamp(orderQueryParameter.getRegisteendtime()))+86400000)+"");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		List<Orders> listorder=new ArrayList<>();
		List<Orders> listorderfor=new ArrayList<>();
		List<Orders> listorderto=new ArrayList<>();
		PageUtil2 pageUtil=null;
		
		Integer companyId=orderQueryParameter.getCompanyid();//得到公司id
		Integer page=orderQueryParameter.getPage();//得到page
		List<String> list=ordersMapper.queryRiskcontrolname(companyId);//查询订单表所有的风控名
		for (int i = 0; i < list.size(); i++) {
			String riskcontrolname=list.get(i);//风控名
			orderQueryParameter.setRiskcontrolname(riskcontrolname);//将风控名封装进实体类
			
			listorder=ordersMapper.queryAllOrders(orderQueryParameter);
			listorderfor.addAll(listorder);
			
		}
		if(listorderfor!=null && !listorderfor.isEmpty()){
	    	ListPageUtil listPageUtil=new ListPageUtil(listorderfor,page,10);
	    	listorderto.addAll(listPageUtil.getData());
	    		

//	    	pageUtil=new PageUtil(listPageUtil.getCurrentPage(), listPageUtil.getPageSize());

	    	for (int i = 0; i < listorderto.size(); i++) {
				listorderto.get(i).setOrderCreateTime(Timestamps.stampToDate(listorderto.get(i).getOrderCreateTime()));
				List<DeferredAndOrder> listdefer=ordersMapper.queryDefer(listorderto.get(i).getId());
				if(listdefer.size()!=0){
					listorderto.get(i).setDeferrTime(listdefer.size());
					listorderto.get(i).setDeferAfterReturntime(listdefer.get(listdefer.size()-1).getDeferAfterReturntime());
				}
			}
		    DateListUtil.ListSort2(listorderto);
	    	
	    	pageUtil=new PageUtil2(listPageUtil.getCurrentPage(), listPageUtil.getPageSize(),listPageUtil.getTotalCount());
	    }else{
	    	pageUtil=new PageUtil2(1, 10, 0);

	    }
	    
	    
	    
	    List<Source> listsource=ordersMapper.querysource(companyId);	    	
		HashMap<String,Object> map=new HashMap<>();
		map.put("listorderto", listorderto);
		map.put("sourcelist", listsource);
		map.put("pageutil", pageUtil);
		return map;
	
  	
  	}

	@Override
	public int borrowNumber(int userId, int companyId) {
		int borrowNumber = ordersMapper.borrowNumber(userId,companyId);
		return borrowNumber;
	}

	@Override
	public int getOrdersId(int userId, int companyId) {
		 int ordersId =  ordersMapper.getOrdersId(userId,companyId);
		return ordersId;
	}

	@Override
	public int setOrder(int companyId, int userId, String orderNumber, String orderCreateTime, int lifeOfLoan,
			int howManyTimesBorMoney, String shouldReturned, int riskmanagementFraction,String borrowMoneyWay) {
		int num = ordersMapper.setOrder(companyId,userId,orderNumber,orderCreateTime,lifeOfLoan,howManyTimesBorMoney,shouldReturned,riskmanagementFraction,borrowMoneyWay);
		return num;
	}

	@Override
	public int getOrderId(String orderNumber) {
		int orderId = ordersMapper.getOrderId(orderNumber);
		return orderId;
	}

	@Override
	public Map<String, Object> getReimbursement(int userId, int companyId) {
		Map<String, Object> map  = ordersMapper.getReimbursement(userId,companyId);
		return map;
	}

	@Override
	public Map<String, Object> getRepayment(int userId, int companyId) {
		Map<String, Object> map1  = ordersMapper.getRepayment(userId,companyId);
		return map1;
	}


	@Override
	public int getBorrowTimeLimit(int userId, int companyId) {
		int borrowTimeLimit  = ordersMapper.getBorrowTimeLimit(userId,companyId);
		return borrowTimeLimit;
	}

	@Override
	public int getorderStatus(int userId, int companyId) {
		int num = ordersMapper.getorderStatus(userId,companyId);
		return num;
	}

	@Override
	public String getorderStatus1(int userId, int companyId) {
		String orderStatus = ordersMapper.getorderStatus1(userId, companyId);
		return orderStatus;
	}


	/**
	 * 订单查询【机审通过和人审通过的用户    在放款后在订单表产生的订单数据】（公司id，page,订单号，姓名，手机号，注册开始时间，注册结束时间，渠道id）
	 */
	public Map<String,Object> queryAllordersByLike(OrderQueryParameter orderQueryParameter){
		if((orderQueryParameter.getRegistestarttime()!=null&&!"".equals(orderQueryParameter.getRegistestarttime()))&&(orderQueryParameter.getRegisteendtime()!=null&&!"".equals(orderQueryParameter.getRegisteendtime()))){
			try {
				orderQueryParameter.setRegistestarttime(Timestamps.dateToStamp(orderQueryParameter.getRegistestarttime()));
				orderQueryParameter.setRegisteendtime((Long.parseLong(Timestamps.dateToStamp(orderQueryParameter.getRegisteendtime()))+86400000)+"");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		int page=orderQueryParameter.getPage();//页面传进来的当前页
		int companyId=orderQueryParameter.getCompanyid();//公司id
		int totalCount=ordersMapper.queryAllordersByLikeCount(orderQueryParameter);//查询总条数
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
    	List<Orders> list=ordersMapper.queryAllordersByLike(orderQueryParameter);//查询list集合
    	for (int i = 0; i <list.size(); i++) {
    		list.get(i).setOrderCreateTime(Timestamps.stampToDate(list.get(i).getOrderCreateTime()));
    		list.get(i).getUser().setRegistetime(Timestamps.stampToDate(list.get(i).getUser().getRegistetime()));
    		List<DeferredAndOrder> listdefer=ordersMapper.queryDefer(list.get(i).getId());
			if(listdefer.size()!=0){
				list.get(i).setDeferrTime(listdefer.size());//延期次数
				list.get(i).setDeferAfterReturntime(Timestamps.stampToDate(listdefer.get(listdefer.size()-1).getDeferAfterReturntime()));//延期后还款时间
			}
		}
    	 List<Source> listsource=ordersMapper.querysource(companyId);	
		Map<String,Object> map=new HashMap<>();
		map.put("listorders", list);
		map.put("listsource", listsource);
		map.put("pageutil", pageUtil);
		return map;
	}
	
	/**
	 * 机审状态用户【包含机审拒绝和机审通过用户】（公司id，page,申请编号，姓名，手机号，申请时间开始，申请时间结束）
	 */
	public Map<String, Object> queryAllUser(UserLikeParameter userLikeParameter){
		if((userLikeParameter.getApplytimestart()!=null&&!"".equals(userLikeParameter.getApplytimestart()))&&(userLikeParameter.getApplytimeend()!=null&&!"".equals(userLikeParameter.getApplytimeend()))){
			try {
				userLikeParameter.setApplytimestart(Timestamps.dateToStamp(userLikeParameter.getApplytimestart()));
				userLikeParameter.setApplytimeend(Timestamps.dateToStamp(userLikeParameter.getApplytimeend()));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		int page=userLikeParameter.getPage();//页面传进来的当前页
		int totalCount=userMapper.queryAllUsercount(userLikeParameter);//查询总数量
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
    	List<User> list=userMapper.queryAllUser(userLikeParameter);//查询list集合
    	for (int i = 0; i <list.size(); i++) {
    		list.get(i).setApplytime(Timestamps.stampToDate(list.get(i).getApplytime()));
		}
    	
		Map<String,Object> map=new HashMap<>();
		map.put("listuser", list);
		map.put("pageutil", pageUtil);
		return map;
		
	}
	
	
	/**
	 * 人审状态用户【包含机审拒绝和机审通过用户】（公司id，page,申请编号，姓名，手机号，申请时间开始，申请时间结束）
	 */
	public Map<String, Object> queryAllUserPeople(UserLikeParameter userLikeParameter){
		if((userLikeParameter.getApplytimestart()!=null&&!"".equals(userLikeParameter.getApplytimestart()))&&(userLikeParameter.getApplytimeend()!=null&&!"".equals(userLikeParameter.getApplytimeend()))){
			try {
				userLikeParameter.setApplytimestart(Timestamps.dateToStamp(userLikeParameter.getApplytimestart()));
				userLikeParameter.setApplytimeend(Timestamps.dateToStamp(userLikeParameter.getApplytimeend()));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		int page=userLikeParameter.getPage();//页面传进来的当前页
		int totalCount=userMapper.queryAllUserPeoplecount(userLikeParameter);//查询总数量
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
    	List<User> list=userMapper.queryAllUserPeople(userLikeParameter);//查询list集合
    	for (int i = 0; i <list.size(); i++) {
    		list.get(i).setApplytime(Timestamps.stampToDate(list.get(i).getApplytime()));
		}
    	
		Map<String,Object> map=new HashMap<>();
		map.put("listuser", list);
		map.put("pageutil", pageUtil);
		return map;
		
	}
	
	/**
	 * 人审通过按钮
	 */
	public int updateShareOfState(Integer sysuserid,Integer userid){
		String operationTime=System.currentTimeMillis()+"";//获取当前时间戳
		int num=userMapper.updateShareOfState(sysuserid,operationTime,userid);
		return num;
	}
	
	/**
	 * 人审不通过按钮
	 */
	public int updateShareOfStateNo(Integer sysuserid,Integer userid){
		String operationTime=System.currentTimeMillis()+"";//获取当前时间戳
		int num=userMapper.updateShareOfStateNo(sysuserid,operationTime,userid);
		return num;
	}
	
	/**
	 * 人审过后状态用户【包括人审不通过和人审通过】（公司id，page,申请编号，姓名，手机号，申请时间开始，申请时间结束，审核员id）
	 */
	public Map<String, Object> queryAllUserPeopleYet(UserLikeParameter userLikeParameter){
		if((userLikeParameter.getApplytimestart()!=null&&!"".equals(userLikeParameter.getApplytimestart()))&&(userLikeParameter.getApplytimeend()!=null&&!"".equals(userLikeParameter.getApplytimeend()))){
			try {
				userLikeParameter.setApplytimestart(Timestamps.dateToStamp(userLikeParameter.getApplytimestart()));
				userLikeParameter.setApplytimeend(Timestamps.dateToStamp(userLikeParameter.getApplytimeend()));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		int page=userLikeParameter.getPage();//页面传进来的当前页
		int companyId=userLikeParameter.getCompanyId();//公司id
		int totalCount=userMapper.queryAllUserPeopleYetcount(userLikeParameter);//查询总数量
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
    	List<User> list=userMapper.queryAllUserPeopleYet(userLikeParameter);//查询list集合
    	for (int i = 0; i <list.size(); i++) {
    		list.get(i).setApplytime(Timestamps.stampToDate(list.get(i).getApplytime()));
		}
    	List<SysUser> listacount=ordersMapper.queryname(companyId);
		Map<String,Object> map=new HashMap<>();
		map.put("listuser", list);
		map.put("listacount", listacount);
		map.put("pageutil", pageUtil);
		return map;
	}

	@Override
	public String getshouldReturnTime(int userId,int companyId) {
		String beforeTime = ordersMapper.getshouldReturnTime(userId,companyId);
		return beforeTime;
	}

}
