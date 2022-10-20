package org.bank.controller;


import org.bank.client.UserClient;
import org.bank.entity.SysUser;
import org.bank.utils.DataResult;
import org.bank.vo.req.*;
import org.bank.vo.resp.LoginRespVO;
import org.bank.vo.resp.PageVO;
import org.bank.vo.resp.UserInfoForVueRespVO;
import org.bank.vo.resp.UserOwnRoleRespVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * @BelongsProject: BankCloud
 * @BelongsPackage: org.bank.controller
 * @Author: lizongle
 * @Description:
 * @Version: 1.0
 */
@RestController
@RequestMapping("/sys")
public class UserController {
    @Autowired
    private UserClient userClient;


    @PostMapping(value = "/user/login")
    public DataResult<LoginRespVO> login(@RequestBody @Valid LoginReqVO vo) {
        return userClient.login(vo);
    }

    @PostMapping("/user/getUserForVUE")
    public DataResult<UserInfoForVueRespVO> detailInfoForVue(){
        return userClient.detailInfoForVue();
    }

    @PostMapping("/user/register")
    public DataResult<String> register(@RequestBody @Valid RegisterReqVO vo) {
        return userClient.register(vo);
    }

    @GetMapping("/user/token")
    public DataResult<String> refreshToken(HttpServletRequest request) {
        return userClient.refreshToken();
    }

    @GetMapping("/user/unLogin")
    public DataResult unLogin() {
        return userClient.unLogin();
    }

    @PutMapping("/user")
    public DataResult updateUserInfo(@RequestBody @Valid UserUpdateReqVO vo, HttpServletRequest request) {
        return userClient.updateUserInfo(vo);
    }

    @PutMapping("/user/info")
    public DataResult updateUserInfoById(@RequestBody @Valid UserUpdateReqVO vo, HttpServletRequest request) {
        return userClient.updateUserInfo(vo);
    }

    @GetMapping("/user/{id}")
    public DataResult<SysUser> detailInfo(@PathVariable("id") String id) {
        return userClient.detailInfo(id);
    }

    @GetMapping("/user")
    public DataResult<SysUser> youSelfInfo(HttpServletRequest request) {
        return userClient.youSelfInfo();
    }

    @PostMapping("/users")
    public DataResult<PageVO<SysUser>> pageInfo(@RequestBody UserPageReqVO vo) {
        return userClient.pageInfo(vo);
    }

    @PostMapping("/user")
    public DataResult addUser(@RequestBody @Valid UserAddReqVO vo) {
        return userClient.addUser(vo);
    }

    @GetMapping("/user/logout")
    public DataResult logout(HttpServletRequest request) {
        return userClient.logout();
    }

    @PutMapping("/user/pwd")
    public DataResult updatePwd(@RequestBody UpdatePasswordReqVO vo, HttpServletRequest request) {
        return userClient.updatePwd(vo);
    }

    @DeleteMapping("/user")
    public DataResult deletedUser(@RequestBody List<String> userIds, HttpServletRequest request) {
        return userClient.deletedUser(userIds);
    }

    @GetMapping("/user/roles/{userId}")
    public DataResult<UserOwnRoleRespVO> getUserOwnRole(@PathVariable("userId") String userId) {
        return userClient.getUserOwnRole(userId);
    }

    @PutMapping("/user/roles/{userId}")
    public DataResult<UserOwnRoleRespVO> setUserOwnRole(@PathVariable("userId") String userId, @RequestBody List<String> roleIds) {
        System.out.println("准备调用");
        return userClient.setUserOwnRole(userId, roleIds);
    }
}
