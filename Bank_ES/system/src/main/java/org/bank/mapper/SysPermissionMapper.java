package org.bank.mapper;

import org.bank.entity.SysPermission;
import org.bank.vo.req.PermissionPageReqVO;

import java.util.List;

public interface SysPermissionMapper {
    int deleteByPrimaryKey(String id);

    int insert(SysPermission record);

    int insertSelective(SysPermission record);

    SysPermission selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysPermission record);

    int updateByPrimaryKey(SysPermission record);

    List<SysPermission> selectInfoByIds (List<String> ids);

    List<SysPermission> selectAll(PermissionPageReqVO vo);

    List<SysPermission> selectChild(String pid);
}