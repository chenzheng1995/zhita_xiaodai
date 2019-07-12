package com.zhita.service.manage.order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhita.dao.manage.OrdersMapper;
import com.zhita.model.manage.ManageControlSettings;
import com.zhita.model.manage.OrderQueryParameter;
import com.zhita.model.manage.Orders;
import com.zhita.util.DateListUtil;
import com.zhita.util.ListPageUtil;
import com.zhita.util.PageUtil;
import com.zhita.util.Timestamps;

@Service
public class OrderServiceImp implements IntOrderService{
	@Autowired
	private OrdersMapper ordersMapper;
	
	//后台管理----机审订单     (公司id，page，订单号，订单开始时间，订单结束时间，风控反馈)
	public Map<String, Object> queryatrOrders(OrderQueryParameter orderQueryParameter){
		System.out.println("service:"+orderQueryParameter);
		List<Orders> listorder=new ArrayList<>();
		List<Orders> listorderfor=new ArrayList<>();
		List<Orders> listorderto=new ArrayList<>();
		PageUtil pageUtil=null;
		
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
		
		for (int i = 0; i <listorderfor.size(); i++) {
			listorderfor.get(i).setOrderCreateTime(Timestamps.stampToDate(listorderfor.get(i).getOrderCreateTime()));
		}
		
		DateListUtil.ListSort2(listorderfor);
		
	    if(listorderfor!=null && !listorderfor.isEmpty()){
	    	ListPageUtil listPageUtil=new ListPageUtil(listorderfor,page,10);
	    	listorderto.addAll(listPageUtil.getData());
	    		
	    	pageUtil=new PageUtil(listPageUtil.getCurrentPage(), listPageUtil.getPageSize(),listPageUtil.getTotalCount());
	    }
	    	
		HashMap<String,Object> map=new HashMap<>();
		map.put("listorderto", listorderto);
		map.put("pageutil", pageUtil);
		return map;
		
	}
	
	//后台管理----机审拒绝未人审订单     (公司id，page，订单号，订单开始时间，订单结束时间)
	public Map<String, Object> queryroaOrders(OrderQueryParameter orderQueryParameter){
		List<Orders> listorder=new ArrayList<>();
		List<Orders> listorderfor=new ArrayList<>();
		List<Orders> listorderto=new ArrayList<>();
		PageUtil pageUtil=null;
		
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
		for (int i = 0; i <listorderfor.size(); i++) {
			listorderfor.get(i).setOrderCreateTime(Timestamps.stampToDate(listorderfor.get(i).getOrderCreateTime()));
		}
		DateListUtil.ListSort2(listorderfor);
		
	    if(listorderfor!=null && !listorderfor.isEmpty()){
	    	ListPageUtil listPageUtil=new ListPageUtil(listorderfor,page,10);
	    	listorderto.addAll(listPageUtil.getData());
	    		
	    	pageUtil=new PageUtil(listPageUtil.getCurrentPage(), listPageUtil.getPageSize(),listPageUtil.getTotalCount());
	    }
	    	
		HashMap<String,Object> map=new HashMap<>();
		map.put("listorderto", listorderto);
		map.put("pageutil", pageUtil);
		return map;
	}
	
	//后台管理----机审拒绝未人审订单——审核通过操作
  	public int upaOrderIfpeopleWhose(Integer orderid,String assessor){
  		int num=ordersMapper.upaOrderIfpeopleWhose(orderid, assessor);
  		return num;
  	}
  	
    //后台管理----已机审已人审（公司id，订单号，订单开始时间，订单结束时间      审核员 ）
  	public Map<String, Object> queryroasOrders(OrderQueryParameter orderQueryParameter){
		List<Orders> listorder=new ArrayList<>();
		List<Orders> listorderfor=new ArrayList<>();
		List<Orders> listorderto=new ArrayList<>();
		PageUtil pageUtil=null;
		
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
		for (int i = 0; i <listorderfor.size(); i++) {
			listorderfor.get(i).setOrderCreateTime(Timestamps.stampToDate(listorderfor.get(i).getOrderCreateTime()));
		}
		DateListUtil.ListSort2(listorderfor);
		
	    if(listorderfor!=null && !listorderfor.isEmpty()){
	    	ListPageUtil listPageUtil=new ListPageUtil(listorderfor,page,10);
	    	listorderto.addAll(listPageUtil.getData());
	    		
	    	pageUtil=new PageUtil(listPageUtil.getCurrentPage(), listPageUtil.getPageSize(),listPageUtil.getTotalCount());
	    }
	    	
		HashMap<String,Object> map=new HashMap<>();
		map.put("listorderto", listorderto);
		map.put("pageutil", pageUtil);
		return map;
	
  	}
  	
  	//后台管理----订单 查询（公司id，page,订单号，订单开始时间，订单结束时间     渠道id）
  	public Map<String, Object> queryAllOrders(OrderQueryParameter orderQueryParameter){
		List<Orders> listorder=new ArrayList<>();
		List<Orders> listorderfor=new ArrayList<>();
		List<Orders> listorderto=new ArrayList<>();
		PageUtil pageUtil=null;
		
		Integer companyId=orderQueryParameter.getCompanyid();//得到公司id
		Integer page=orderQueryParameter.getPage();//得到page
		List<String> list=ordersMapper.queryRiskcontrolname(companyId);//查询订单表所有的风控名
		for (int i = 0; i < list.size(); i++) {
			String riskcontrolname=list.get(i);//风控名
			orderQueryParameter.setRiskcontrolname(riskcontrolname);//将风控名封装进实体类
			
			listorder=ordersMapper.queryAllOrders(orderQueryParameter);
			listorderfor.addAll(listorder);
			
		}
		for (int i = 0; i <listorderfor.size(); i++) {
			listorderfor.get(i).setOrderCreateTime(Timestamps.stampToDate(listorderfor.get(i).getOrderCreateTime()));
			listorderfor.get(i).getUser().setRegistetime(Timestamps.stampToDate(listorderfor.get(i).getUser().getRegistetime()));
		}
		DateListUtil.ListSort2(listorderfor);
		
	    if(listorderfor!=null && !listorderfor.isEmpty()){
	    	ListPageUtil listPageUtil=new ListPageUtil(listorderfor,page,10);
	    	listorderto.addAll(listPageUtil.getData());
	    		
	    	pageUtil=new PageUtil(listPageUtil.getCurrentPage(), listPageUtil.getPageSize(),listPageUtil.getTotalCount());
	    }
	    	
		HashMap<String,Object> map=new HashMap<>();
		map.put("listorderto", listorderto);
		map.put("pageutil", pageUtil);
		return map;
	
  	
  	}
}
