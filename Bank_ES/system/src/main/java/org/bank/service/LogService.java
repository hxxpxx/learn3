package org.bank.service;

import org.bank.entity.SysLog;
import org.bank.vo.req.SysLogPageReqVO;
import org.bank.vo.resp.PageVO;

import java.util.List;


public interface LogService {

    PageVO<SysLog> pageInfo(SysLogPageReqVO vo);

    void deleted(List<String> logIds);
}
