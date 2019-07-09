package com.zhita.dao.manage;

import com.zhita.model.manage.Orderdetails;

public interface OrderdetailsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Orderdetails record);

    int insertSelective(Orderdetails record);


    Orderdetails selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Orderdetails record);

    int updateByPrimaryKey(Orderdetails record);
}