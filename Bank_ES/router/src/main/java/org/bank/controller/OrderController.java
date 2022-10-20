package org.bank.controller;

import org.bank.aop.annotation.LogAnnotation;
import org.bank.client.OrderClient;
import org.bank.entity.Order;
import org.bank.entity.OrderAddress;
import org.bank.entity.OrderSnapshot;
import org.bank.utils.DataResult;
import org.bank.vo.req.OrderAddReqVO;
import org.bank.vo.req.OrderPageReqVO;
import org.bank.vo.req.OrderPayReqVO;
import org.bank.vo.resp.PageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/order")
@RestController
public class OrderController {

    @Autowired
    OrderClient orderClient;
    @PostMapping("/add")
    public DataResult<Order> addorder(@RequestBody @Valid OrderAddReqVO vo){
        return orderClient.addorder(vo);
    }
    @PostMapping("/page")
    public DataResult<PageVO<Order>> pageInfo(@RequestBody OrderPageReqVO vo){
        return orderClient.pageInfo(vo);
    }

    @PostMapping("/getOrderNo")
    public DataResult<String> getOrderNo(){
        return orderClient.getOrderNo();
    }

    @GetMapping("/cancelOrderNo/{orderNo}")
    public DataResult<Boolean> cancelOrderNo(@PathVariable("orderNo")String orderNo){
        return orderClient.cancelOrderNo(orderNo);
    }
    @GetMapping("/cancelOrder/{orderNo}")
    @LogAnnotation(title = "订单管理", action = "取消订单")
    public DataResult cancelOrder(@PathVariable("orderNo")String orderNo){
        return orderClient.cancelOrder(orderNo);
    }

    @PostMapping("/payOrder")
    @LogAnnotation(title = "订单管理", action = "支付订单")
    public DataResult payOrder(@RequestBody OrderPayReqVO orderPayReqVO) {
        return orderClient.payOrder(orderPayReqVO);
    }

    @GetMapping("/getOrderAddress/{orderNo}")
    @LogAnnotation(title = "订单管理", action = "获取订单地址")
    public DataResult<OrderAddress> getAddressForOrderNo(@PathVariable("orderNo")String orderNo) {
        return orderClient.getAddressForOrderNo(orderNo);
    }

    @GetMapping("/getOrderSnapshotForOrderNo/{orderNo}")
    @LogAnnotation(title = "订单管理", action = "获取订单交易快照")
    public DataResult<OrderSnapshot> getOrderSnapshotForOrderNo(@PathVariable("orderNo")String orderNo) {
        return orderClient.getOrderSnapshotForOrderNo(orderNo);
    }
}
