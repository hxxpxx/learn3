package org.bank.controller;


import org.bank.aop.annotation.LogAnnotation;
import org.bank.client.GoodsClient;
import org.bank.entity.Goods;
import org.bank.utils.DataResult;
import org.bank.vo.req.GoodsAddReqVO;
import org.bank.vo.req.GoodsPageReqVO;
import org.bank.vo.req.GoodsStockReqVO;
import org.bank.vo.req.GoodsUpdateReqVO;
import org.bank.vo.resp.PageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RequestMapping("/goods")
@RestController
public class GoodsController {

    @Autowired
    GoodsClient goodsClient;

    @PostMapping("/add")
    public DataResult<Goods> addGoods(@RequestBody @Valid GoodsAddReqVO vo) {
        return goodsClient.addGoods(vo);
    }

    @DeleteMapping("/delete/{id}")
    public DataResult deleted(@PathVariable("id") String id) {
        return goodsClient.deleted(id);
    }

    @PutMapping("/update")
    public DataResult updateDept(@RequestBody @Valid GoodsUpdateReqVO vo) {
        return goodsClient.updateDept(vo);
    }

    @GetMapping("/detail/{id}")
    public DataResult<Goods> detailInfo(@PathVariable("id") String id) {
        return goodsClient.detailInfo(id);
    }

    @PostMapping("/getStock")
    @LogAnnotation(title = "商品管理", action = "获取商品库存")
    public DataResult<Integer> getStock(@RequestBody GoodsStockReqVO vo){
        GoodsStockReqVO req=new GoodsStockReqVO();
        req.setGoodsId("goods_56811ac9-f866-4cf1-9083-605dc384c89d");
        DataResult<Integer> stockResult=goodsClient.getStock(req);
        return stockResult;
    }

    @PostMapping("/page")
    public DataResult<PageVO<Goods>> pageInfo(@RequestBody GoodsPageReqVO vo) {
        return goodsClient.pageInfo(vo);
    }
}
