package org.bank.mapper;


import org.apache.ibatis.annotations.Param;
import org.bank.entity.SysDept;
import org.bank.service.DeptService;
import org.bank.vo.req.DeptPageReqVO;

import java.util.List;

/**
 * @BelongsProject: BankCloud
 * @BelongsPackage: org.bank.mapper
 * @Author: lizongle
 * @Description:
 * @Version: 1.0
 */
public interface SysDeptMapper {
    int deleteByPrimaryKey(String id);

    int insert(SysDept record);

    int insertSelective(SysDept record);

    SysDept selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysDept record);

    int updateByPrimaryKey(SysDept record);

    /**
     * 多个数据 要用 @Param
     * @param oldStr
     * @param newStr
     * @param relationCode
     * @return
     */
    int updateRelationCode(@Param("oldStr") String oldStr, @Param("newStr") String newStr, @Param("relationCode") String relationCode);

    List<SysDept> selectAll(DeptPageReqVO vo);

    List<String> selectChildIds(String relationCode);

    List<SysDept> selectChilds(String relationCode);

    int deleteFormIds(List<String> ids);

    List<SysDept> selectAllByNotContainChild(List<String> list);

}