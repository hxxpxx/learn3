package org.bank.server;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.bank.aop.annotation.LogAnnotation;
import org.bank.entity.SysLog;
import org.bank.service.LogService;
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
@RestController("sysLogServer")
public class SysLogServer {

    @Autowired
    private LogService logService;

    @RequestMapping(value = "/logs", method = RequestMethod.POST)
    @LogAnnotation(title = "系统操作日志管理",action = "分页查询系统操作日志")
    @RequiresPermissions("sys:log:list")
    public DataResult<PageVO<SysLog>> pageInfo(@RequestBody SysLogPageReqVO vo){
        PageVO<SysLog> sysLogPageVO = logService.pageInfo(vo);
        DataResult<PageVO<SysLog>> result=DataResult.success();
        result.setData(sysLogPageVO);
        return result;
    }

    @RequestMapping(value = "/logs", method = RequestMethod.DELETE)
    @LogAnnotation(title = "系统操作日志管理",action = "删除系统操作日志")
    @RequiresPermissions("sys:log:deleted")
    public DataResult deleted(@RequestBody List<String> logIds){
        logService.deleted(logIds);
        return DataResult.success();
    }
}
