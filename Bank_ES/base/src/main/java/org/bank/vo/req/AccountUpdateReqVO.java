package org.bank.vo.req;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class AccountUpdateReqVO {

    /**
     * 账户
     */
    @NotNull(message = "账户Id不能为空")
    private Integer id;
    @NotBlank(message = "联系人不能为空")
    private String userId;
    @NotBlank(message = "账号不能为空")
    private String accountNo;
    @NotNull
    private Double balance;
}
