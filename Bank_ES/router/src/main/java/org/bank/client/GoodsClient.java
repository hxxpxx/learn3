package org.bank.client;

import org.bank.aop.annotation.LogAnnotation;
import org.bank.entity.Goods;
import org.bank.utils.DataResult;
import org.bank.vo.req.GoodsAddReqVO;
import org.bank.vo.req.GoodsPageReqVO;
import org.bank.vo.req.GoodsStockReqVO;
import org.bank.vo.req.GoodsUpdateReqVO;
import org.bank.vo.resp.PageVO;
import org.springframework.cloud.openfeign.FeignClient;
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

    @PostMapping("/goods/getStock")
    @LogAnnotation(title = "商品管理", action = "获取商品库存")
    public DataResult<Integer> getStock(@RequestBody GoodsStockReqVO vo) ;

    @GetMapping("/goods/detail/{id}")
    public DataResult<Goods> detailInfo(@PathVariable("id") String id) ;

    @PostMapping("/goods/page")
    public DataResult<PageVO<Goods>> pageInfo(@RequestBody GoodsPageReqVO vo);

}
