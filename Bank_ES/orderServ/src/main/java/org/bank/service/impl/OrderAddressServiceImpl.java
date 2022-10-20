package org.bank.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.bank.entity.OrderAddress;
import org.bank.exception.BusinessException;
import org.bank.exception.code.BaseResponseCode;
import org.bank.mapper.OrderAddressMapper;
import org.bank.service.OrderAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Slf4j
public class OrderAddressServiceImpl implements OrderAddressService {
    @Autowired
    OrderAddressMapper orderAddressMapper;

    @Transactional(propagation= Propagation.REQUIRED,rollbackFor = {Exception.class})
    @Override
    public OrderAddress addOrderAddress(OrderAddress vo) {
        int insertSize= orderAddressMapper.insertSelective(vo);
        if(insertSize==0){
            throw new BusinessException(BaseResponseCode.OPERATION_ERRO);
        }
        return vo;
    }
    @Transactional(propagation= Propagation.REQUIRED,rollbackFor = {Exception.class})
    @Override
    public void updateOrderAddress(OrderAddress vo) {
        OrderAddress addressInfo=orderAddressMapper.selectByPrimaryKey(vo.getOrderNo());
        if(addressInfo==null){
            log.error("传入 的 orderNo:{}不合法",vo.getOrderNo());
            throw new BusinessException(BaseResponseCode.DATA_ERROR);
        }
        int updateSize=orderAddressMapper.updateByPrimaryKeySelective(vo);
        if(updateSize==0){
            throw new BusinessException(BaseResponseCode.OPERATION_ERRO);
        }
    }

    @Override
    public OrderAddress detailInfo(String orderNo) {
        OrderAddress addressInfo=orderAddressMapper.selectByPrimaryKey(orderNo);
        if(addressInfo==null){
            log.error("传入 的 orderNo:{}不合法",orderNo);
            throw new BusinessException(BaseResponseCode.DATA_ERROR);
        }
        return addressInfo;
    }

    @Override
    public void deletedOrderAddress(String orderNo) {

    }

    @Override
    public List<OrderAddress> selectAllOrderAddress() {
        return null;
    }
}
