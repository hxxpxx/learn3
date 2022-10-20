package org.bank.vo.req;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class OrderAddReqVO {
    @NotBlank(message = "订单号不能为空")
    private String orderNo;

    @NotBlank(message = "商品不能为空")
    private String goodsId;

    private String title;
    @NotNull(message = "收货地址不可为空")
    private Integer addressId;

    @NotNull
    private Integer num;

    private Double price;

    private Double totalAmout;

    private String userId;

}
