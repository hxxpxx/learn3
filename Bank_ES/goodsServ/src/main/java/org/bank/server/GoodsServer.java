package org.bank.server;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.bank.aop.annotation.LogAnnotation;
import org.bank.entity.Goods;
import org.bank.service.GoodsService;
import org.bank.utils.DataResult;
import org.bank.vo.req.*;
import org.bank.vo.resp.PageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController("goodsServer")
public class GoodsServer {

    @Autowired
    GoodsService goodsService;

    @PostMapping("/goods/add")
    @LogAnnotation(title = "商品管理", action = "商品新增")
    @RequiresPermissions("goods:goods:add")
    public DataResult<Goods> addGoods(@RequestBody @Valid GoodsAddReqVO vo) {
        DataResult<Goods> result = DataResult.success();
        result.setData(goodsService.addGoods(vo));
        return result;
    }

    @DeleteMapping("/goods/delete/{id}")
    @LogAnnotation(title = "商品管理", action = "删除商品")
    @RequiresPermissions("goods:goods:deleted")
    public DataResult deleted(@PathVariable("id") String id) {
        goodsService.deletedGoods(id);
        return DataResult.success();
    }

    @PutMapping("/goods/update")
    @LogAnnotation(title = "商品管理", action = "更新商品信息")
    @RequiresPermissions("goods:goods:update")
    public DataResult updateDept(@RequestBody @Valid GoodsUpdateReqVO vo) {
        goodsService.updateGoods(vo);
        return DataResult.success();
    }

    @GetMapping("/goods/detail/{id}")
    @LogAnnotation(title = "商品管理", action = "查询商品详情")
    @RequiresPermissions("goods:goods:list")
    public DataResult<Goods> detailInfo(@PathVariable("id") String id) {
        DataResult<Goods> result = DataResult.success();
        result.setData(goodsService.detailInfo(id));
        return result;
    }

    @PostMapping("/goods/page")
    @LogAnnotation(title = "商品管理", action = "分页获取商品信息")
    @RequiresPermissions("goods:goods:list")
    public DataResult<PageVO<Goods>> pageInfo(@RequestBody GoodsPageReqVO vo) {
        DataResult<PageVO<Goods>> result = DataResult.success();
        result.setData(goodsService.pageInfo(vo));
        return result;
    }


    @PostMapping("/goods/getStock")
    @LogAnnotation(title = "商品管理", action = "获取商品库存")
    public DataResult<Integer> getStock(@RequestBody GoodsStockReqVO vo) {
        DataResult<Integer> result = DataResult.success();
        result.setData(goodsService.getStock(vo.getGoodsId()));
        return result;
    }

    @PostMapping("/goods/stock/lock")
    @LogAnnotation(title = "商品管理", action = "锁定商品库存")
    public DataResult<Boolean> lockStock(@RequestBody GoodsStockReqVO vo) {
        DataResult<Boolean> result = DataResult.success();
        result.setData(goodsService.lockStock(vo.getStackNum(),vo.getGoodsId()));
        return result;
    }

    @PostMapping("/goods/stock/unlock")
    @LogAnnotation(title = "商品管理", action = "解锁商品库存")
    public DataResult<Boolean> unLockStock(@RequestBody GoodsStockReqVO vo) {
        DataResult<Boolean> result = DataResult.success();
        result.setData(goodsService.unLockStock(vo.getStackNum(),vo.getGoodsId()));
        return result;
    }

    @PostMapping("/goods/stock/deduct")
    @LogAnnotation(title = "商品管理", action = "扣除商品库存")
    public DataResult deductStock(@RequestBody GoodsDeductStockReqVO vo) {
        DataResult result = DataResult.success();
        goodsService.deductStock(vo);
        return result;
    }


}
