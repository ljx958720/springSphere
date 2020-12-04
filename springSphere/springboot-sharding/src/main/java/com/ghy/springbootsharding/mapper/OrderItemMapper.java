package com.ghy.springbootsharding.mapper;


import com.ghy.springbootsharding.entity.OrderItem;

public interface OrderItemMapper {

    int insert(OrderItem record);

    OrderItem selectByPrimaryKey(Integer itemId);


}