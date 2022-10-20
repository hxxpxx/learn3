package org.bank.service;

import org.bank.entity.Goods;
import org.bank.vo.req.GoodsAddReqVO;
import org.bank.vo.req.GoodsDeductStockReqVO;
import org.bank.vo.req.GoodsPageReqVO;
import org.bank.vo.req.GoodsUpdateReqVO;
import org.bank.vo.resp.PageVO;

import java.util.List;

public interface GoodsService {
    Goods addGoods(GoodsAddReqVO vo);

    Integer getStock(String goodsId);

    boolean lockStock(Integer stock,String goodsId);

    boolean unLockStock(Integer stock,String goodsId);

    void updateGoods(GoodsUpdateReqVO vo);

    void deductStock(GoodsDeductStockReqVO vo);

    Goods detailInfo(String id);

    void deletedGoods(String id);

    PageVO<Goods> pageInfo(GoodsPageReqVO vo);

    List<Goods> selectAllGoods();
}
