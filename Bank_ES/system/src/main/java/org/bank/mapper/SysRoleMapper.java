package org.bank.mapper;



import org.bank.entity.SysRole;
import org.bank.vo.req.RolePageReqVO;

import java.util.List;

/**
 * @BelongsProject: BankCloud
 * @BelongsPackage: org.bank.mapper
 * @Author: lizongle
 * @Description:
 * @Version: 1.0
 */
public interface SysRoleMapper {
    int deleteByPrimaryKey(String id);

    int insert(SysRole record);

    int insertSelective(SysRole record);

    SysRole selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysRole record);

    int updateByPrimaryKey(SysRole record);

    List<SysRole> selectAll(RolePageReqVO vo);

    List<SysRole> getRoleInfoByIds(List<String> ids);


}