package org.bank.service;


import org.bank.entity.SysUser;
import org.bank.vo.req.*;
import org.bank.vo.resp.LoginRespVO;
import org.bank.vo.resp.PageVO;
import org.bank.vo.resp.UserInfoForVueRespVO;
import org.bank.vo.resp.UserOwnRoleRespVO;

import java.util.List;


public interface UserService {

    String register(RegisterReqVO vo);

    LoginRespVO login(LoginReqVO vo);


    String loginForOuth(LoginReqVO vo) ;


    String refreshToken(String refreshToken,String accessToken);

    void updateUserInfo(UserUpdateReqVO vo, String operationId);

    void updateIntegral(UserUpdateIntegralReqVO vo);

    PageVO<SysUser> pageInfo(UserPageReqVO vo);

    SysUser detailInfo(String userId);

    UserInfoForVueRespVO detailInfoForVue(String userId);



    PageVO<SysUser> selectUserInfoByDeptIds(int pageNum, int pageSize, List<String> deptIds);

    void addUser(UserAddReqVO vo);

    void logout(String accessToken,String refreshToken);

    void updatePwd(UpdatePasswordReqVO vo,String userId,String accessToken, String refreshToken);

    List<SysUser> getUserListByDeptId(String deptId);

    List<SysUser> getUserListByDeptIds(List<String> deptIds);

    void deletedUsers(List<String> userIds,String operationId);

    UserOwnRoleRespVO getUserOwnRole(String userId);

    void setUserOwnRole(String userId,List<String> roleIds);
}
