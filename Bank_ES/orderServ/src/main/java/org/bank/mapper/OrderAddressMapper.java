package org.bank.mapper;

import org.bank.entity.OrderAddress;

public interface OrderAddressMapper {
    int deleteByPrimaryKey(String orderNo);

    int insert(OrderAddress record);

    int insertSelective(OrderAddress record);

    OrderAddress selectByPrimaryKey(String orderNo);

    int updateByPrimaryKeySelective(OrderAddress record);

    int updateByPrimaryKey(OrderAddress record);
}