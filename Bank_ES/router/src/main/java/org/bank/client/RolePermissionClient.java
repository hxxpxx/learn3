package org.bank.client;


import org.bank.utils.DataResult;
import org.bank.vo.req.RolePermissionOperationReqVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;


@FeignClient(name = "orderSystemService",contextId = "rolePermissionServer")
public interface RolePermissionClient {

    @PostMapping("/role/permission")
    public DataResult operationRolePermission(@RequestBody @Valid RolePermissionOperationReqVO vo);
}
