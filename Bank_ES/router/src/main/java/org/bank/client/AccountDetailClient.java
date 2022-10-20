package org.bank.client;

import org.bank.entity.AccountDetail;
import org.bank.utils.DataResult;
import org.bank.vo.req.AccountDetailPageReqVO;
import org.bank.vo.resp.PageVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "accountService", contextId = "accountDetailServer")
public interface AccountDetailClient {


    @PostMapping("/accountDetail/page")
    public DataResult<PageVO<AccountDetail>> pageInfo(@RequestBody AccountDetailPageReqVO vo) ;
}
