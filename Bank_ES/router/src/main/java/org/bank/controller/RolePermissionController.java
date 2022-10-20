package org.bank.controller;

import org.bank.client.RolePermissionClient;
import org.bank.utils.DataResult;
import org.bank.vo.req.RolePermissionOperationReqVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @BelongsProject: BankCloud
 * @BelongsPackage: org.bank.controller
 * @Author: lizongle
 * @Description:
 * @Version: 1.0
 */
@RequestMapping("/sys")
@RestController
public class RolePermissionController {
    @Autowired
    private RolePermissionClient rolePermissionClient;

    @PostMapping("/role/permission")

    public DataResult operationRolePermission(@RequestBody @Valid RolePermissionOperationReqVO vo){
        return rolePermissionClient.operationRolePermission(vo);
    }
}
