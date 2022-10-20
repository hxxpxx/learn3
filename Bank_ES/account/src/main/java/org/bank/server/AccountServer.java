package org.bank.server;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.bank.aop.annotation.LogAnnotation;
import org.bank.constants.Constant;
import org.bank.entity.Account;
import org.bank.service.AccountService;
import org.bank.utils.DataResult;
import org.bank.utils.JwtTokenUtil;
import org.bank.vo.req.AccountAddReqVo;
import org.bank.vo.req.AccountPageReqVO;
import org.bank.vo.req.AccountTransactionReqVO;
import org.bank.vo.req.AccountUpdateReqVO;
import org.bank.vo.resp.PageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * @BelongsProject: Bank_ES
 * @BelongsPackage: org.bank.server
 * @Author: lizongle
 * @Description:
 * @Version: 1.0
 */
@RestController("accountServer")
public class AccountServer {
    @Autowired
    AccountService accountService;
    
    @PostMapping("/account/add")
    @LogAnnotation(title = "账户管理", action = "新增账户")
    @RequiresPermissions("account:add")
    public DataResult<Account> addAccount(@RequestBody @Valid AccountAddReqVo vo, HttpServletRequest request) {
        DataResult<Account> result = DataResult.success();
        String userId = JwtTokenUtil.getUserId(request.getHeader(Constant.ACCESS_TOKEN));
        result.setData(accountService.addAccount(vo, userId));
        return result;
    }

    @DeleteMapping("/account/delete/{id}")
    @LogAnnotation(title = "账户管理", action = "删除账户")
    @RequiresPermissions("account:deleted")
    public DataResult deleted(@PathVariable("id") int id,HttpServletRequest request) {
        String userId=JwtTokenUtil.getUserId(request.getHeader(Constant.ACCESS_TOKEN));
        accountService.deletedAccount(id,userId);
        return DataResult.success();
    }

    @PutMapping("/account/update")
    @LogAnnotation(title = "账户管理", action = "更新账户信息")
    @RequiresPermissions("account:update")
    public DataResult update(@RequestBody @Valid AccountUpdateReqVO vo, HttpServletRequest request) {
        String userId=JwtTokenUtil.getUserId(request.getHeader(Constant.ACCESS_TOKEN));
        accountService.updateAccount(vo,userId);
        return DataResult.success();
    }

    @GetMapping("/account/detail/{accountNo}")
    @LogAnnotation(title = "账户管理", action = "查询账户详情")
    @RequiresPermissions("account:list")
    public DataResult<Account> detailInfo(@PathVariable("accountNo") String accountNo) {
        DataResult<Account> result = DataResult.success();
        result.setData(accountService.detailInfoForAccountNo(accountNo));
        return result;
    }



    @PostMapping("/account/page")
    @LogAnnotation(title = "账户管理", action = "分页获取账户信息")
    @RequiresPermissions("account:list")
    public DataResult<PageVO<Account>> pageInfo(@RequestBody AccountPageReqVO vo, HttpServletRequest request) {
        DataResult<PageVO<Account>> result = DataResult.success();
        String userId = JwtTokenUtil.getUserId(request.getHeader(Constant.ACCESS_TOKEN));
        result.setData(accountService.pageInfo(vo,userId));
        return result;
    }

    @PostMapping("/account/allList")
    @LogAnnotation(title = "账户管理", action = "获取当前用户所有账户信息")
    @RequiresPermissions("account:list")
    public DataResult<List<Account>> getAll(HttpServletRequest request) {
        DataResult<List<Account>> result = DataResult.success();
        String userId = JwtTokenUtil.getUserId(request.getHeader(Constant.ACCESS_TOKEN));
        result.setData(accountService.getAccountInfoByUserId(userId));
        return result;
    }

    @PostMapping("/account/transaction")
    @LogAnnotation(title = "账户管理", action = "账户交易")
    @RequiresPermissions("account:list")
    public DataResult<Boolean> transaction(@RequestBody @Valid AccountTransactionReqVO accountTransactionReqVO) {
        DataResult<Boolean> result = DataResult.success();
        result.setData(accountService.deductionByAccountNo(accountTransactionReqVO));
        return result;
    }
}
