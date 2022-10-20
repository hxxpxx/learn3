package org.bank.client;

import org.bank.entity.SysLog;
import org.bank.utils.DataResult;
import org.bank.vo.req.SysLogPageReqVO;
import org.bank.vo.resp.PageVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "orderSystemService",contextId = "sysLogServer")
public interface SysLogClient {

    @PostMapping("/logs")
    public DataResult<PageVO<SysLog>> pageInfo(@RequestBody SysLogPageReqVO vo);

    @DeleteMapping("/logs")
    public DataResult deleted(@RequestBody List<String> logIds);
}
