package org.bank.vo.req;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @BelongsProject: Bank_ES
 * @BelongsPackage: org.bank.vo.req
 * @Author: lizongle
 * @Description:
 * @Version: 1.0
 */
@Data
public class OrderPayReqVO {
    @NotBlank(message = "订单号不可为空")
    private String orderNo;
    @NotBlank(message = "付款账号不可为空")
    private String accountNo;
}
