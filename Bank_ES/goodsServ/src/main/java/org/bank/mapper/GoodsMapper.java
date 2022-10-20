package org.bank.mapper;

import org.bank.entity.Goods;

import java.util.List;

public interface GoodsMapper {
    int deleteByPrimaryKey(String id);

    int insert(Goods record);

    int insertSelective(Goods record);

    Goods selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Goods record);

    int updateByPrimaryKey(Goods record);

    List<Goods> selectAll(Goods vo);

    List<Goods> selectByName(String name);
}