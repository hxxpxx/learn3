package org.bank.mapper;

import org.bank.entity.OrderSnapshot;

public interface OrderSnapshotMapper {
    int deleteByPrimaryKey(String orderNo);

    int insert(OrderSnapshot record);

    int insertSelective(OrderSnapshot record);

    OrderSnapshot selectByPrimaryKey(String orderNo);

    int updateByPrimaryKeySelective(OrderSnapshot record);

    int updateByPrimaryKey(OrderSnapshot record);
}