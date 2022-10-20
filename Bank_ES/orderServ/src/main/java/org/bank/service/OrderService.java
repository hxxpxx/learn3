package org.bank.service;

import org.bank.entity.Order;
import org.bank.entity.OrderAddress;
import org.bank.entity.OrderSnapshot;
import org.bank.esEntity.OrderES;
import org.bank.vo.req.OrderAddReqVO;
import org.bank.vo.req.OrderPageReqVO;
import org.bank.vo.req.OrderPayReqVO;
import org.bank.vo.req.OrderUpdateReqVO;
import org.bank.vo.resp.PageVO;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.util.List;

public interface OrderService {

    String issueOrderNo();

    boolean cancelOrderNo(String orderNo);

    Order addOrder(OrderAddReqVO vo,String userId);

    void cancleOrder(String orderNo);

    void cancleOrder(String orderNo,String userId);

    void payOrder(OrderPayReqVO orderPayReqVO, String userId);

    void updateOrder(OrderUpdateReqVO vo) throws Exception;

    Order detailInfo(String orderNo,String userId);

    Order detailInfo(String orderNo);

    PageVO<OrderES> pageInfo(OrderPageReqVO vo) throws IOException;

    OrderAddress getAddressForOrderNo(String orderNo);

    OrderSnapshot getOrderSnapshotForOrderNo(String orderNo);


}
