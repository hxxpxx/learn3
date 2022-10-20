package org.bank.vo.req;


import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class GoodsDeductStockReqVO {
    /**
     * 商品ID
     */
    @NotBlank(message = "商品Id不能为空")
    private String id;
    @NotBlank(message = "商品库存不能为空")
    private Integer stock;

}
