package org.bank.client;

import org.bank.entity.Account;
import org.bank.utils.DataResult;
import org.bank.vo.req.AccountAddReqVo;
import org.bank.vo.req.AccountPageReqVO;
import org.bank.vo.req.AccountUpdateReqVO;
import org.bank.vo.resp.PageVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
@FeignClient(name = "accountService", contextId = "accountServer")
public interface AccountClient {
    @PostMapping("/account/add")
    public DataResult<Account> addAccount(@RequestBody @Valid AccountAddReqVo vo);

    @DeleteMapping("/account/delete/{id}")
    public DataResult deleted(@PathVariable("id") int id) ;

    @PutMapping("/account/update")
    public DataResult update(@RequestBody @Valid AccountUpdateReqVO vo);
    @GetMapping("/account/detail/{accountNo}")
    public DataResult<Account> detailInfo(@PathVariable("accountNo") String accountNo);

    @PostMapping("/account/page")
    public DataResult<PageVO<Account>> pageInfo(@RequestBody AccountPageReqVO vo);

    @PostMapping("/account/allList")
    public DataResult<List<Account>> getAll() ;
}
