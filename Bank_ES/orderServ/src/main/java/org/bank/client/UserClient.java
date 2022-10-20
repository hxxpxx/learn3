package org.bank.client;

import org.bank.aop.annotation.LogAnnotation;
import org.bank.constants.Constant;
import org.bank.entity.SysUser;
import org.bank.utils.DataResult;
import org.bank.utils.JwtTokenUtil;
import org.bank.vo.req.*;
import org.bank.vo.resp.LoginRespVO;
import org.bank.vo.resp.PageVO;
import org.bank.vo.resp.UserInfoForVueRespVO;
import org.bank.vo.resp.UserOwnRoleRespVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;


@FeignClient(name = "orderSystemService",contextId = "userServer")

public interface UserClient {
    @PutMapping("/updateIntegral")
    @LogAnnotation(title = "用户管理", action = "更新用户积分")
    public DataResult updateIntegral(@RequestBody @Valid UserUpdateIntegralReqVO vo);
}
