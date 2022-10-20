package org.bank.server;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.bank.aop.annotation.LogAnnotation;
import org.bank.constants.Constant;
import org.bank.entity.AccountDetail;
import org.bank.service.AccountDetailService;
import org.bank.utils.DataResult;
import org.bank.utils.JwtTokenUtil;
import org.bank.vo.req.AccountDetailPageReqVO;
import org.bank.vo.resp.PageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController("accountDetailServer")
public class AccountDetailServer {
    @Autowired
    AccountDetailService accountDetailService;

    @PostMapping("/accountDetail/add")
    @LogAnnotation(title = "账户明细管理", action = "新增账户明细")
    public DataResult<AccountDetail> addAccountDetail(@RequestBody @Valid AccountDetail vo, HttpServletRequest request) {
        DataResult<AccountDetail> result = DataResult.success();
        String userId = JwtTokenUtil.getUserId(request.getHeader(Constant.ACCESS_TOKEN));
        result.setData(accountDetailService.addAccountDetail(vo, userId));
        return result;
    }

    @PostMapping("/accountDetail/page")
    @LogAnnotation(title = "账户明细管理", action = "分页获取账户明细信息")
    @RequiresPermissions("account:list")
    public DataResult<PageVO<AccountDetail>> pageInfo(@RequestBody AccountDetailPageReqVO vo, HttpServletRequest request) {
        DataResult<PageVO<AccountDetail>> result = DataResult.success();
        String userId = JwtTokenUtil.getUserId(request.getHeader(Constant.ACCESS_TOKEN));
        result.setData(accountDetailService.pageInfo(vo,userId));
        return result;
    }
}
