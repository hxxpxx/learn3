package org.bank.vo.resp;

import lombok.Data;
import org.bank.entity.SysUser;

import java.util.List;

/**
 * @BelongsProject: BankCloud
 * @BelongsPackage: org.bank.vo.resp
 * @Author: lizongle
 * @Description:
 * @Version: 1.0
 */
@Data
public class UserInfoForVueRespVO {

    /**
     * 用户id
     */
    private SysUser user;



    /**
     * 用户角色字符串
     */
    private List<String> roles;


}
