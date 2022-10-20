package org.bank.server;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.bank.aop.annotation.LogAnnotation;
import org.bank.constants.Constant;
import org.bank.entity.Order;
import org.bank.entity.OrderAddress;
import org.bank.entity.OrderSnapshot;
import org.bank.esEntity.OrderES;
import org.bank.service.OrderService;
import org.bank.utils.DataResult;
import org.bank.utils.JwtTokenUtil;
import org.bank.vo.req.OrderAddReqVO;
import org.bank.vo.req.OrderPageReqVO;
import org.bank.vo.req.OrderPayReqVO;
import org.bank.vo.resp.PageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;

@RestController("orderServer")
public class OrderServer {
    @Autowired
    OrderService orderService;

    @PostMapping("/order/add")
    @LogAnnotation(title = "订单管理", action = "订单新增")
    public DataResult<Order> addorder(@RequestBody @Valid OrderAddReqVO vo, HttpServletRequest request) {
        DataResult<Order> result = DataResult.success();
        String userId = JwtTokenUtil.getUserId(request.getHeader(Constant.ACCESS_TOKEN));
        result.setData(orderService.addOrder(vo,userId));
        return result;
    }
    @PostMapping("/order/page")
    @LogAnnotation(title = "订单管理", action = "分页获取订单信息")
    public DataResult<PageVO<OrderES>> pageInfo(@RequestBody OrderPageReqVO vo, HttpServletRequest request) throws IOException {

        DataResult<PageVO<OrderES>> result = DataResult.success();
        String userId = JwtTokenUtil.getUserId(request.getHeader(Constant.ACCESS_TOKEN));
        vo.setUserId(userId);
        result.setData(orderService.pageInfo(vo));
        return result;
    }

    @GetMapping("/order/getOrderAddress/{orderNo}")
    @LogAnnotation(title = "订单管理", action = "获取订单地址")
    public DataResult<OrderAddress> getAddressForOrderNo(@PathVariable("orderNo")String orderNo) {
        DataResult<OrderAddress> result = DataResult.success();
        result.setData(orderService.getAddressForOrderNo(orderNo));
        return result;
    }
    @GetMapping("/order/getOrderSnapshotForOrderNo/{orderNo}")
    @LogAnnotation(title = "订单管理", action = "获取订单交易快照")
    public DataResult<OrderSnapshot> getOrderSnapshotForOrderNo(@PathVariable("orderNo")String orderNo) {
        DataResult<OrderSnapshot> result = DataResult.success();
        result.setData(orderService.getOrderSnapshotForOrderNo(orderNo));
        return result;
    }

    @PostMapping("/order/getOrderNo")
    @LogAnnotation(title = "订单管理", action = "获取订单号")
    public DataResult<String> getOrderNo() {
        DataResult<String> result = DataResult.success();
        result.setData(orderService.issueOrderNo());
        return result;
    }

    @GetMapping("/order/cancelOrderNo/{orderNo}")
    @LogAnnotation(title = "订单管理", action = "释放订单号")
    public DataResult<Boolean> cancelOrderNo(@PathVariable("orderNo")String orderNo ) {
        DataResult<Boolean> result = DataResult.success();
        result.setData(orderService.cancelOrderNo(orderNo));
        return result;
    }

    @GetMapping("/order/cancelOrder/{orderNo}")
    @LogAnnotation(title = "订单管理", action = "取消订单")
    public DataResult cancelOrder(@PathVariable("orderNo")String orderNo,HttpServletRequest request) {
        DataResult result= DataResult.success();
        String userId = JwtTokenUtil.getUserId(request.getHeader(Constant.ACCESS_TOKEN));
        orderService.cancleOrder(orderNo,userId);
        return result;
    }

    @PostMapping("/order/payOrder")
    @LogAnnotation(title = "订单管理", action = "支付订单")
    public DataResult payOrder(@RequestBody OrderPayReqVO orderPayReqVO, HttpServletRequest request) {
        DataResult result = DataResult.success();
        String userId = JwtTokenUtil.getUserId(request.getHeader(Constant.ACCESS_TOKEN));
        orderService.payOrder(orderPayReqVO,userId);
        return result;
    }

}
