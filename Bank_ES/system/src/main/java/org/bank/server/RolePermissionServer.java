package org.bank.server;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.bank.aop.annotation.LogAnnotation;
import org.bank.service.RolePermissionService;
import org.bank.utils.DataResult;
import org.bank.vo.req.RolePermissionOperationReqVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @BelongsProject: BankCloud
 * @BelongsPackage: org.bank.controller
 * @Author: lizongle
 * @Description:
 * @Version: 1.0
 */
@RestController("rolePermissionServer")
public class RolePermissionServer {
    @Autowired
    private RolePermissionService rolePermissionService;

    @RequestMapping(value="/role/permission",method = RequestMethod.POST)
    @LogAnnotation(title = "角色和菜单关联接口",action = "修改或者新增角色菜单权限")
    @RequiresPermissions(value = {"sys:role:update","sys:role:add"},logical = Logical.OR)
    public DataResult operationRolePermission(@RequestBody @Valid RolePermissionOperationReqVO vo){
        rolePermissionService.addRolePermission(vo);
        return DataResult.success();
    }
}
