package org.bank.controller;

import org.bank.aop.annotation.LogAnnotation;
import org.bank.client.AccountClient;
import org.bank.constants.Constant;
import org.bank.entity.Account;
import org.bank.utils.DataResult;
import org.bank.vo.req.AccountAddReqVo;
import org.bank.vo.req.AccountPageReqVO;
import org.bank.vo.req.AccountUpdateReqVO;
import org.bank.vo.resp.PageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * @BelongsProject: Bank_ES
 * @BelongsPackage: org.bank.controller
 * @Author: lizongle
 * @Description:
 * @Version: 1.0
 */
@RequestMapping("/account")
@RestController
public class AccountController {
    @Autowired
    AccountClient accountClient;

    @PostMapping("/add")
    public DataResult<Account> addAccount(@RequestBody @Valid AccountAddReqVo vo) {
        return accountClient.addAccount(vo);
    }

    @DeleteMapping("/delete/{id}")
    public DataResult deleted(@PathVariable("id") int id) {
        return accountClient.deleted(id);
    }

    @PutMapping("/update")
    public DataResult update(@RequestBody @Valid AccountUpdateReqVO vo) {
        return accountClient.update(vo);
    }

    @GetMapping("/detail/{accountNo}")
    public DataResult<Account> detailInfo(@PathVariable("accountNo") String accountNo) {
        return accountClient.detailInfo(accountNo);
    }

    @PostMapping("/page")
    public DataResult<PageVO<Account>> pageInfo(@RequestBody AccountPageReqVO vo) {
        return accountClient.pageInfo(vo);
    }

    @PostMapping("/allList")
    public DataResult<List<Account>> getAll(HttpServletRequest request) {
        return accountClient.getAll();
    }
}
