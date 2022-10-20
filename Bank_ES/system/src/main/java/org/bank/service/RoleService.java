package org.bank.service;



import org.bank.entity.SysRole;
import org.bank.vo.req.RoleAddReqVO;
import org.bank.vo.req.RolePageReqVO;
import org.bank.vo.req.RoleUpdateReqVO;
import org.bank.vo.resp.PageVO;

import java.util.List;

public interface RoleService {

    SysRole addRole(RoleAddReqVO vo);

    void updateRole(RoleUpdateReqVO vo, String accessToken);

    SysRole detailInfo(String id);

    void deletedRole(String id);

    PageVO<SysRole> pageInfo(RolePageReqVO vo);

    List<SysRole> getRoleInfoByUserId(String userId);

    List<String> getRoleNames(String userId);

    List<SysRole> selectAllRoles();
}
