package org.bank.vo.req;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ShoppingCartUpdateReqVO {
    @NotBlank(message = "购物车Id不能为空")
    private String id;
    @NotBlank(message = "商品不能为空")
    private String goodsId;
    @NotBlank(message = "单价不能为空")
    private Double price;
    @NotBlank(message = "数量不能为空")
    private Integer num;
    @NotBlank(message = "总价不能为空")
    private Double totalAmout;

}
