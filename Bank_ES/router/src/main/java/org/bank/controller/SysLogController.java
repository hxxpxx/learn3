package org.bank.controller;

import org.bank.client.SysLogClient;
import org.bank.entity.SysLog;
import org.bank.utils.DataResult;
import org.bank.vo.req.SysLogPageReqVO;
import org.bank.vo.resp.PageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @BelongsProject: BankCloud
 * @BelongsPackage: org.bank.controller
 * @Author: lizongle
 * @Description:
 * @Version: 1.0
 */
@RequestMapping("/sys")
@RestController
public class SysLogController {

    @Autowired
    private SysLogClient sysLogClient;

    @PostMapping("/logs")

    public DataResult<PageVO<SysLog>> pageInfo(@RequestBody SysLogPageReqVO vo) {
        return sysLogClient.pageInfo(vo);
    }

    @DeleteMapping("/logs")

    public DataResult deleted(@RequestBody List<String> logIds) {
        return sysLogClient.deleted(logIds);
    }
}
