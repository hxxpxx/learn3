package org.bank.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.bank.entity.OrderSnapshot;
import org.bank.exception.BusinessException;
import org.bank.exception.code.BaseResponseCode;
import org.bank.mapper.OrderSnapshotMapper;
import org.bank.service.OrderSnapshotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class OrderSnapshotServiceImpl implements OrderSnapshotService {
    @Autowired
    OrderSnapshotMapper orderSnapshotMapper;

    @Transactional(propagation= Propagation.REQUIRED,rollbackFor = {Exception.class})
    @Override
    public OrderSnapshot addOrderSnapshot(OrderSnapshot vo) {
        int insertSize= orderSnapshotMapper.insertSelective(vo);
        if(insertSize==0){
            throw new BusinessException(BaseResponseCode.OPERATION_ERRO);
        }
        return vo;
    }
    @Transactional(propagation= Propagation.REQUIRED,rollbackFor = {Exception.class})
    @Override
    public OrderSnapshot detailInfo(String orderNo) {
        OrderSnapshot orderSnapshot=orderSnapshotMapper.selectByPrimaryKey(orderNo);
        if(orderSnapshot==null){
            log.error("传入 的 orderNo:{}不合法",orderNo);
            throw new BusinessException(BaseResponseCode.DATA_ERROR);
        }
        return orderSnapshot;
    }

    @Override
    public void deleteOrderSnapshot(String orderNo) {

    }
}
