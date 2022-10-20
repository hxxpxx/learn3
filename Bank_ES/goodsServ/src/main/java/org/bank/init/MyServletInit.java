package org.bank.init;

import org.bank.constants.Constant;
import org.bank.entity.Goods;
import org.bank.service.GoodsService;
import org.bank.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;


@Component
public class MyServletInit {
    @Autowired
    GoodsService goodsService;

    @Autowired
    RedisService redisService;
    @PostConstruct
    public void init(){
        //将商品添加到redis中去
        initGoods();
    }

    public void initGoods(){
        List<Goods> goodsList=goodsService.selectAllGoods();
        if(goodsList!=null&&goodsList.size()>0){
            for(Goods goods:goodsList){
                redisService.set(Constant.GOODS_CACHE_KEY+goods.getId(),goods);
            }
        }
    }
}
