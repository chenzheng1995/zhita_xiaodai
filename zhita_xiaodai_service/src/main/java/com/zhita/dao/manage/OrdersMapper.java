package com.zhita.dao.manage;

import java.util.List;

import com.zhita.model.manage.ManageControlSettings;
import com.zhita.model.manage.Orders;

public interface OrdersMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Orders record);

    int insertSelective(Orders record);

    Orders selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Orders record);

    int updateByPrimaryKey(Orders record);
    
    //后台管理---查询风控设置管理表正在使用的风控设置
    ManageControlSettings querymancon(Integer companyId);
    
    //后台管理----机审订单     (公司id，page，订单号，订单开始时间，订单结束时间，风控反馈，风控分数开始，风控分数结束)
  	public List<Orders> queryatrOrders(Integer companyId,String orderNumber,String orderStartTime,String orderEndTime,String riskManagemenType,String riskmanagementFractionStart,String riskmanagementFractionEnd);
}