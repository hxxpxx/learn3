package org.bank.client;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.bank.aop.annotation.LogAnnotation;
import org.bank.entity.Account;
import org.bank.utils.DataResult;
import org.bank.vo.req.AccountAddReqVo;
import org.bank.vo.req.AccountPageReqVO;
import org.bank.vo.req.AccountTransactionReqVO;
import org.bank.vo.req.AccountUpdateReqVO;
import org.bank.vo.resp.PageVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@FeignClient(name = "accountService", contextId = "accountServer")
public interface AccountClient {
    @PostMapping("/account/transaction")

    public DataResult<Boolean> transaction(@RequestBody @Valid AccountTransactionReqVO accountTransactionReqVO);

}
