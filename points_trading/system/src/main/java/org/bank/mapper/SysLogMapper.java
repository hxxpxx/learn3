package org.bank.mapper;

import org.bank.entity.SysLog;
import org.bank.vo.req.SysLogPageReqVO;

import java.util.List;

/**
 * @BelongsProject: BankCloud
 * @BelongsPackage: org.bank.mapper
 * @Author: lizongle
 * @Description:
 * @Version: 1.0
 */
public interface SysLogMapper {
    int deleteByPrimaryKey(String id);

    int insert(SysLog record);

    int insertSelective(SysLog record);

    SysLog selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysLog record);

    int updateByPrimaryKey(SysLog record);

    List<SysLog> selectAll(SysLogPageReqVO vo);

    void batchDeletedLog(List<String> logIds);
}
