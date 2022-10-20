package org.bank.client;

import org.bank.aop.annotation.LogAnnotation;
import org.bank.entity.Goods;
import org.bank.utils.DataResult;
import org.bank.vo.req.*;
import org.bank.vo.resp.PageVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
@FeignClient(name = "goodsService", contextId = "goodsServer")
public interface GoodsClient {
    @PostMapping("/goods/add")
    public DataResult<Goods> addGoods(@RequestBody @Valid GoodsAddReqVO vo);

    @DeleteMapping("/goods/delete/{id}")
    public DataResult deleted(@PathVariable("id") String id) ;

    @PutMapping("/goods/update")
    public DataResult updateDept(@RequestBody @Valid GoodsUpdateReqVO vo);

    @GetMapping("/goods/detail/{id}")
    public DataResult<Goods> detailInfo(@PathVariable("id") String id) ;

    @PostMapping("/goods/page")
    public DataResult<PageVO<Goods>> pageInfo(@RequestBody GoodsPageReqVO vo);

    @PostMapping("/goods/getStock")
    @LogAnnotation(title = "商品管理", action = "获取商品库存")
    public DataResult<Integer> getStock(@RequestBody GoodsStockReqVO vo) ;

    @PostMapping("/goods/stock/lock")
    public DataResult<Boolean> lockStock(@RequestBody GoodsStockReqVO vo);

    @PostMapping("/goods/stock/unlock")
    public DataResult<Boolean> unLockStock(@RequestBody GoodsStockReqVO vo);

    @PostMapping("/goods/stock/deduct")
    @LogAnnotation(title = "商品管理", action = "扣除商品库存")
    public DataResult deductStock(@RequestBody GoodsDeductStockReqVO vo);
}
