package org.bank.client;

import org.bank.utils.DataResult;
import org.bank.vo.req.UserRoleOperationReqVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@FeignClient(name = "orderSystemService",contextId = "userRoleServer")
public interface UserRoleClient {
    @PostMapping("/user/role")
    public DataResult operationUserRole(@RequestBody @Valid UserRoleOperationReqVO vo);
}
