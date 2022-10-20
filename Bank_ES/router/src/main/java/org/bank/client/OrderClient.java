package org.bank.client;

import org.bank.aop.annotation.LogAnnotation;
import org.bank.constants.Constant;
import org.bank.entity.Order;
import org.bank.entity.OrderAddress;
import org.bank.entity.OrderSnapshot;
import org.bank.utils.DataResult;
import org.bank.vo.req.OrderAddReqVO;
import org.bank.vo.req.OrderPageReqVO;
import org.bank.vo.req.OrderPayReqVO;
import org.bank.vo.resp.PageVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@FeignClient(name = "orderService",contextId = "orderServer")
public interface OrderClient {
    @PostMapping("/order/add")
    public DataResult<Order> addorder(@RequestBody @Valid OrderAddReqVO vo) ;
    @PostMapping("/order/page")
    public DataResult<PageVO<Order>> pageInfo(@RequestBody OrderPageReqVO vo);

    @PostMapping("/order/getOrderNo")
    public DataResult<String> getOrderNo();

    @GetMapping("/order/cancelOrderNo/{orderNo}")
    public DataResult<Boolean> cancelOrderNo(@PathVariable("orderNo")String orderNo) ;

    @GetMapping("/order/cancelOrder/{orderNo}")
    @LogAnnotation(title = "订单管理", action = "取消订单")
    public DataResult cancelOrder(@PathVariable("orderNo")String orderNo);

    @PostMapping("/order/payOrder")
    @LogAnnotation(title = "订单管理", action = "支付订单")
    public DataResult payOrder(@RequestBody OrderPayReqVO orderPayReqVO) ;

    @GetMapping("/order/getOrderAddress/{orderNo}")
    @LogAnnotation(title = "订单管理", action = "获取订单地址")
    public DataResult<OrderAddress> getAddressForOrderNo(@PathVariable("orderNo")String orderNo) ;

    @GetMapping("/order/getOrderSnapshotForOrderNo/{orderNo}")
    @LogAnnotation(title = "订单管理", action = "获取订单交易快照")
    public DataResult<OrderSnapshot> getOrderSnapshotForOrderNo(@PathVariable("orderNo")String orderNo) ;
}
