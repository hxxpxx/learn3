package org.bank.client;

import org.bank.entity.SysUser;
import org.bank.utils.DataResult;
import org.bank.vo.req.*;
import org.bank.vo.resp.LoginRespVO;
import org.bank.vo.resp.PageVO;
import org.bank.vo.resp.UserInfoForVueRespVO;
import org.bank.vo.resp.UserOwnRoleRespVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@FeignClient(name = "orderSystemService",contextId = "userServer")

public interface UserClient {
    @PostMapping(value = "/user/login")
    public DataResult<LoginRespVO> login(@RequestBody @Valid LoginReqVO vo);

    @PostMapping("/user/getUserForVUE")
    public DataResult<UserInfoForVueRespVO> detailInfoForVue();

    @PostMapping("/user/register")
    public DataResult<String> register(@RequestBody @Valid RegisterReqVO vo);
    @GetMapping("/user/token")
    public DataResult<String> refreshToken();

    @GetMapping("/user/unLogin")
    public DataResult unLogin();

    @PutMapping("/user")
    public DataResult updateUserInfo(@RequestBody @Valid UserUpdateReqVO vo);
    @PutMapping("/user/info")
    public DataResult updateUserInfoById(@RequestBody @Valid UserUpdateReqVO vo);
    @GetMapping("/user/{id}")
    public DataResult<SysUser> detailInfo(@PathVariable("id") String id);
    @GetMapping("/user")
    public DataResult<SysUser> youSelfInfo();
    @PostMapping("/users")
    public DataResult<PageVO<SysUser>> pageInfo(@RequestBody UserPageReqVO vo);
    @PostMapping("/user")
    public DataResult addUser(@RequestBody @Valid UserAddReqVO vo);

    @GetMapping("/user/logout")
    public DataResult logout();

    @PutMapping("/user/pwd")
    public DataResult updatePwd(@RequestBody UpdatePasswordReqVO vo);

    @DeleteMapping("/user")
    public DataResult deletedUser(@RequestBody List<String> userIds);
    @GetMapping("/user/roles/{userId}")
    public DataResult<UserOwnRoleRespVO> getUserOwnRole(@PathVariable("userId")String userId);
    @PutMapping("/user/roles/{userId}")
    public DataResult<UserOwnRoleRespVO> setUserOwnRole(@PathVariable("userId")String userId, @RequestBody List<String> roleIds);
}
