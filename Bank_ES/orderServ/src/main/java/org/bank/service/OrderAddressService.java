package org.bank.service;

import org.bank.entity.OrderAddress;
import org.bank.vo.req.OrderAddressAddReqVO;
import org.bank.vo.req.OrderAddressUpdateReqVO;

import java.util.List;

public interface OrderAddressService {
    OrderAddress addOrderAddress(OrderAddress vo);

    void updateOrderAddress(OrderAddress vo);

    OrderAddress detailInfo(String orderNo);

    void deletedOrderAddress(String orderNo);

    List<OrderAddress> selectAllOrderAddress();
}
