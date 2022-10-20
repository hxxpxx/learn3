package org.bank.vo.req;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class OrderSnapshotAddReqVO {
    @NotBlank(message = "订单号不能为空")
    private String orderNo;

    @NotBlank(message = "商品不能为空")
    private String goodsId;

    @NotBlank(message = "商品名称不能为空")
    private String title;

    @NotBlank(message = "商品数量不能为空")
    private Integer num;

    @NotBlank(message = "商品单价不能为空")
    private Double price;


    private String describe;


}
