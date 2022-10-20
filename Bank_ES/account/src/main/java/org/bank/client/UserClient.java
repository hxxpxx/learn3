package org.bank.client;

import org.bank.aop.annotation.LogAnnotation;
import org.bank.utils.DataResult;
import org.bank.vo.req.UserUpdateIntegralReqVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;


@FeignClient(name = "orderSystemService",contextId = "userServer")

public interface UserClient {
    @PutMapping("/updateIntegral")
    @LogAnnotation(title = "用户管理", action = "更新用户积分")
    public DataResult updateIntegral(@RequestBody @Valid UserUpdateIntegralReqVO vo);
}
