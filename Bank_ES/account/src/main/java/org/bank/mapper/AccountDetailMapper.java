package org.bank.mapper;

import org.bank.entity.AccountDetail;

public interface AccountDetailMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AccountDetail record);

    int insertSelective(AccountDetail record);

    AccountDetail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AccountDetail record);

    int updateByPrimaryKey(AccountDetail record);
}