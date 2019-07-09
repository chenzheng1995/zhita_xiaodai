package com.zhita.service.manage.order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhita.dao.manage.OrdersMapper;
import com.zhita.model.manage.ManageControlSettings;
import com.zhita.model.manage.Orders;
import com.zhita.util.ListPageUtil;
import com.zhita.util.PageUtil;
import com.zhita.util.Timestamps;

@Service
public class OrderServiceImp implements IntOrderService{
	@Autowired
	private OrdersMapper ordersMapper;
	
	//后台管理----机审订单 (公司id，page，订单号，订单开始时间，订单结束时间，风控反馈)
	public Map<String, Object> queryatrOrders(Integer companyId,Integer page,String orderNumber,String orderStartTime,String orderEndTime,String riskManagemenType){
		ManageControlSettings manageControlSettings=ordersMapper.querymancon(companyId);//查询风控设置管理表正在使用的风控设置信息
		String riskmanagementFraction=manageControlSettings.getAtrntlfractionalsegment();//机审拒绝不放款分数段
		
		String[] strarray=riskmanagementFraction.split("-");
		String startGrade=strarray[0];//风控开始分数
		String endGrade=strarray[1];//风控结束分数
		
		List<Orders> list=new ArrayList<>();
		List<Orders> listto=new ArrayList<>();
		PageUtil pageUtil=null;
		
		list=ordersMapper.queryatrOrders(companyId,orderNumber, orderStartTime, orderEndTime, riskManagemenType, startGrade,endGrade);
		
		
	   	for (int i = 0; i < list.size(); i++) {
	   		list.get(i).setOrderCreateTime(Timestamps.stampToDate(list.get(i).getOrderCreateTime()));
		}
	    	
	    if(list!=null && !list.isEmpty()){
	    	ListPageUtil listPageUtil=new ListPageUtil(list,page,10);
	    	listto.addAll(listPageUtil.getData());
	    		
	    	pageUtil=new PageUtil(listPageUtil.getCurrentPage(), listPageUtil.getPageSize(),listPageUtil.getTotalCount());
	    }
	    	
		HashMap<String,Object> map=new HashMap<>();
		map.put("orderlist", listto);
		map.put("pageutil", pageUtil);
		return map;
	}
}
