package org.bank.controller;

import org.bank.client.AccountDetailClient;
import org.bank.entity.AccountDetail;
import org.bank.utils.DataResult;
import org.bank.vo.req.AccountDetailPageReqVO;
import org.bank.vo.resp.PageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/accountDetail")
@RestController
public class AccountDetailController {
    @Autowired
    AccountDetailClient accountDetailClient;


    @PostMapping("/page")
    public DataResult<PageVO<AccountDetail>> pageInfo(@RequestBody AccountDetailPageReqVO vo) {
        return accountDetailClient.pageInfo(vo);
    }
}
