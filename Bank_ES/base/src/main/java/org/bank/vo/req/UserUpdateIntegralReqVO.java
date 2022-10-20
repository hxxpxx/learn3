package org.bank.vo.req;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @BelongsProject: BankCloud
 * @BelongsPackage: org.bank.vo.req
 * @Author: lizongle
 * @Description:
 * @Version: 1.0
 */
@Data
public class UserUpdateIntegralReqVO {

    /**
     * 用户id
     */
    @NotBlank(message = "用户id不能为空")
    private String id;

    /**
     * 积分
     */
    private Double integral;


    /**
     *  true 加分 false 减分
     */
    private boolean type;
}
