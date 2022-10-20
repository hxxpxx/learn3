package org.bank.controller;


import org.bank.client.UserRoleClient;
import org.bank.utils.DataResult;
import org.bank.vo.req.UserRoleOperationReqVO;
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
public class UserRoleController {
    @Autowired
    private UserRoleClient userRoleClient;
    
    @PostMapping("/user/role")
    public DataResult operationUserRole(@RequestBody @Valid UserRoleOperationReqVO vo){
        return userRoleClient.operationUserRole(vo);
    }
}
