package org.bank.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.bank.constants.Constant;
import org.bank.entity.Goods;
import org.bank.exception.BusinessException;
import org.bank.exception.code.BaseResponseCode;
import org.bank.lock.redisson.RedissonLock;
import org.bank.mapper.GoodsMapper;
import org.bank.service.GoodsService;
import org.bank.service.RedisService;
import org.bank.utils.PageUtils;
import org.bank.vo.req.GoodsAddReqVO;
import org.bank.vo.req.GoodsDeductStockReqVO;
import org.bank.vo.req.GoodsPageReqVO;
import org.bank.vo.req.GoodsUpdateReqVO;
import org.bank.vo.resp.PageVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class GoodsServiceImpl implements GoodsService {
    @Autowired
    GoodsMapper goodsMapper;

    @Autowired
    RedisService redisService;

    @Autowired
    RedissonLock redissonLock;

    @Override
    public Goods addGoods(GoodsAddReqVO vo) {
        //查询是否有此商品
        List<Goods> list = goodsMapper.selectByName(vo.getName());
        Goods addGoods = new Goods();
        BeanUtils.copyProperties(vo, addGoods);
        addGoods.setId("goods_" + UUID.randomUUID().toString());
        addGoods.setCreatetime(new Date());
        if (list != null && list.size() > 0) {
            throw new BusinessException(BaseResponseCode.GOODS_ISEXIT);
        }
        int insertSize = goodsMapper.insertSelective(addGoods);
        if (insertSize <= 0) {
            throw new BusinessException(BaseResponseCode.OPERATION_ERRO);
        }
        redisService.set(Constant.GOODS_CACHE_KEY + addGoods.getId(), addGoods);
        return addGoods;
    }

    @Override
    public Integer getStock(String goodsId) {
        try {
            //获取锁，锁定库存
            if (redissonLock.lock(Constant.GOODS_STOCK_LOCK_KEY+ goodsId, 10)) {
                //先取商品
                Goods goods = null;
                if (redisService.hasKey(Constant.GOODS_CACHE_KEY + goodsId)) {
                    goods = JSONObject.parseObject(redisService.get(Constant.GOODS_CACHE_KEY + goodsId).toString(),Goods.class);
                } else {
                    goods = detailInfo(goodsId);
                }
                if (goods == null) {
                    throw new BusinessException(BaseResponseCode.GOODS_ISNOTEXIT);
                }
                //返回库存
                Integer lockStockNum = redisService.get(Constant.GOODS_STOCK_LOCKED_KEY + goodsId) == null ? 0 : Integer.parseInt(redisService.get(Constant.GOODS_STOCK_LOCKED_KEY + goodsId).toString());
                Integer goodStock = goods.getStock()==null?0:goods.getStock();
                return goodStock-lockStockNum;
            } else {
                throw new BusinessException(BaseResponseCode.SYSTEM_BUSY);
            }
        } catch (Exception e) {
            if(e instanceof  BusinessException){
                throw e;
            }else{
                log.info("获取商品库存错误,错误信息为{}",e.getMessage());
                throw new BusinessException(BaseResponseCode.SYSTEM_BUSY);
            }
        }finally {
            //释放锁
            redissonLock.release(Constant.GOODS_STOCK_LOCK_KEY + goodsId);
        }
    }

    @Override
    public boolean lockStock(Integer stock, String goodsId) {
        if (stock == null || stock == 0) {
            return true;
        }
        try {
            //获取锁，锁定库存
            if (redissonLock.lock(Constant.GOODS_STOCK_LOCK_KEY + goodsId, 10)) {
                //先取商品
                Goods goods = null;
                if (redisService.hasKey(Constant.GOODS_CACHE_KEY + goodsId)) {
                    goods = JSONObject.parseObject(redisService.get(Constant.GOODS_CACHE_KEY + goodsId).toString(),Goods.class);
                } else {
                    goods = detailInfo(goodsId);
                }
                if (goods == null) {
                    throw new BusinessException(BaseResponseCode.GOODS_ISNOTEXIT);
                }
                //判断库存是否满足
                Integer lockStockNum = redisService.get(Constant.GOODS_STOCK_LOCKED_KEY + goodsId) == null ? 0 : Integer.parseInt(redisService.get(Constant.GOODS_STOCK_LOCKED_KEY + goodsId).toString());
                Integer goodStock = goods.getStock()==null?0:goods.getStock();
                if (lockStockNum + stock <= goodStock) {
                    redisService.set(Constant.GOODS_STOCK_LOCKED_KEY + goodsId, lockStockNum + stock);
                    return true;
                } else {
                    throw new BusinessException(BaseResponseCode.GOODS_STOCK_IS_OUT);
                }
            } else {
                throw new BusinessException(BaseResponseCode.SYSTEM_BUSY);
            }
        }catch (Exception e) {
            if(e instanceof  BusinessException){
                throw e;
            }else{
                log.info("锁定商品库存错误,错误信息为{}",e.getMessage());
                throw new BusinessException(BaseResponseCode.SYSTEM_BUSY);
            }
        }finally {
            //释放锁
            redissonLock.release(Constant.GOODS_STOCK_LOCK_KEY+ goodsId);
        }
    }

    @Override
    public boolean unLockStock(Integer stock, String goodsId) {
        try {
            //获取锁，解锁库存
            if (redissonLock.lock(Constant.GOODS_STOCK_LOCK_KEY + goodsId, 10)) {
                Integer lockStockNum = redisService.get(Constant.GOODS_STOCK_LOCKED_KEY + goodsId) == null ? 0 : Integer.parseInt(redisService.get(Constant.GOODS_STOCK_LOCKED_KEY + goodsId).toString());
                if (stock < lockStockNum) {
                    redisService.set(Constant.GOODS_STOCK_LOCKED_KEY + goodsId, lockStockNum - stock);
                } else {
                    redisService.set(Constant.GOODS_STOCK_LOCKED_KEY + goodsId, 0);
                }
                return true;
            } else {
                throw new BusinessException(BaseResponseCode.SYSTEM_BUSY);
            }
        }catch (Exception e) {
            if(e instanceof  BusinessException){
                throw e;
            }else{
                log.info("释放商品库存错误,错误信息为{}",e.getMessage());
                throw new BusinessException(BaseResponseCode.SYSTEM_BUSY);
            }
        }finally {
            //释放锁
            redissonLock.release(Constant.GOODS_STOCK_LOCK_KEY + goodsId);
        }
    }

    @Override
    public void updateGoods(GoodsUpdateReqVO vo) {
        Goods goods = goodsMapper.selectByPrimaryKey(vo.getId());
        if (goods == null) {
            log.error("传入 的 id:{}不合法", vo.getId());
            throw new BusinessException(BaseResponseCode.DATA_ERROR);
        }
        List<Goods> list = goodsMapper.selectByName(vo.getName());
        if (list != null && list.size() > 0 && !goods.getId().equals(list.get(0).getId())) {
            throw new BusinessException(BaseResponseCode.GOODS_ISEXIT);
        }
        Goods update = new Goods();
        BeanUtils.copyProperties(vo, update);
        update.setUpdatetime(new Date());
        int count = goodsMapper.updateByPrimaryKeySelective(update);
        if (count != 1) {
            throw new BusinessException(BaseResponseCode.OPERATION_ERRO);
        }
        //重置
        //更新缓存
        redisService.set(Constant.GOODS_CACHE_KEY + vo.getId(), detailInfo(vo.getId()));
    }

    @Override
    public void deductStock(GoodsDeductStockReqVO vo) {
        //获取商品信息
        Goods goods=detailInfo(vo.getId());
        if(goods==null){
            throw  new  BusinessException(BaseResponseCode.GOODS_ISNOTEXIT);
        }
        String goodsId=goods.getId();
        try {
            Integer deductStock=vo.getStock();
            //获取锁，解锁库存
            if (redissonLock.lock(Constant.GOODS_STOCK_LOCK_KEY + goodsId, 10)) {
                Integer goodsStock=goods.getStock()==null?0:goods.getStock();
                if(goodsStock-deductStock<0){
                    throw  new  BusinessException(BaseResponseCode.GOODS_STOCK_IS_OUT);
                }
                Goods update=new Goods();
                update.setId(goodsId);
                update.setStock(goodsStock-deductStock);
                update.setUpdatetime(new Date());
                int updatesize=goodsMapper.updateByPrimaryKeySelective(update);
                if(updatesize==0){
                    throw  new  BusinessException(BaseResponseCode.PAY_ORDER_STOCK_DEDUCT_ERRPR);
                }
                goods.setStock(goodsStock-deductStock);
                Integer lockStockNum = redisService.get(Constant.GOODS_STOCK_LOCKED_KEY + goodsId) == null ? 0 : Integer.parseInt(redisService.get(Constant.GOODS_STOCK_LOCKED_KEY + goodsId).toString());
                if (deductStock < lockStockNum) {
                    redisService.set(Constant.GOODS_STOCK_LOCKED_KEY + goodsId, lockStockNum - deductStock);
                } else {
                    redisService.set(Constant.GOODS_STOCK_LOCKED_KEY + goodsId, 0);
                }
                redisService.set(Constant.GOODS_CACHE_KEY + goodsId,goods);
            } else {
                throw new BusinessException(BaseResponseCode.SYSTEM_BUSY);
            }
        }catch (Exception e) {
            log.info("商品库存扣除错误,错误信息为{}",e.getMessage());
            if(e instanceof  BusinessException){
                throw e;
            }else{
                throw new BusinessException(BaseResponseCode.SYSTEM_BUSY);
            }
        }finally {
            //释放锁
            redissonLock.release(Constant.GOODS_STOCK_LOCK_KEY + goodsId);
        }
    }

    @Override
    public Goods detailInfo(String id) {
        if(redisService.hasKey(Constant.GOODS_CACHE_KEY+id)){
            return  JSONObject.parseObject(redisService.get(Constant.GOODS_CACHE_KEY + id).toString(),Goods.class);
        }
        Goods goods = goodsMapper.selectByPrimaryKey(id);
        if (goods == null) {
            log.error("传入 的 id:{}不合法", id);
            throw new BusinessException(BaseResponseCode.DATA_ERROR);
        }
        return goods;
    }

    @Override
    public void deletedGoods(String id) {
        Goods goods = goodsMapper.selectByPrimaryKey(id);
        if (goods == null) {
            log.error("传入 的 id:{}不合法", id);
            throw new BusinessException(BaseResponseCode.DATA_ERROR);
        }
        Goods update = new Goods();
        update.setId(id);
        update.setDeleted(0);
        update.setUpdatetime(new Date());
        int updateSize = goodsMapper.updateByPrimaryKeySelective(update);
        if (updateSize <= 0) {
            throw new BusinessException(BaseResponseCode.OPERATION_ERRO);
        }
        //更新缓存
        if (redisService.hasKey(Constant.GOODS_CACHE_KEY + id)) {
            redisService.delete(Constant.GOODS_CACHE_KEY + id);
        }
    }

    @Override
    public PageVO<Goods> pageInfo(GoodsPageReqVO vo) {
        PageHelper.startPage(vo.getPageNum(), vo.getPageSize());
        Goods query = new Goods();
        BeanUtils.copyProperties(vo, query);
        List<Goods> goods = goodsMapper.selectAll(query);
        return PageUtils.getPageVO(goods);
    }

    @Override
    public List<Goods> selectAllGoods() {
        return goodsMapper.selectAll(null);
    }
}
