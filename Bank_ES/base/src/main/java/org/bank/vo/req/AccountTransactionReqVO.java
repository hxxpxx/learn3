package org.bank.vo.req;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class AccountTransactionReqVO {
    @NotBlank(message = "交易账号不可为空")
    private String accountNo;
    @NotBlank(message = "交易单号不可为空")
    private String orderNo;
    @NotBlank(message = "交易信息不可为空")
    private String title;
    @NotBlank(message = "交易账号身份认证信息不可为空")
    private String userId;
    @NotNull(message = "交易金额不可为空")
    private Double amount;
    //type true 扣款   false  退款
    private boolean type;
}
