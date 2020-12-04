package com.ghy.springbootsharding.mapper;


import com.ghy.springbootsharding.entity.Order;

public interface OrderMapper {


    int insert(Order record);
    Order selectByPrimaryKey(Integer orderId);

}