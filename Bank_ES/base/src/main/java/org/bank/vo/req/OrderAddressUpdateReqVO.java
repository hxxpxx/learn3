package org.bank.vo.req;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class OrderAddressUpdateReqVO {
    @NotBlank(message = "订单号不能为空")
    private String orderNo;
    /**
     * 联系人
     */
    @NotBlank(message = "联系人不能为空")
    private String contacts;

    /**
     * 联系电话
     */
    @NotBlank(message = "联系电话不能为空")
    private String phone;

    /**
     *联系地址
     */
    @NotBlank(message = "联系地址不能为空")
    private String address;
}
