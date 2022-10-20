package org.bank.vo.req;


import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class GoodsUpdateReqVO {
    /**
     * 商品ID
     */
    @NotBlank(message = "商品Id不能为空")
    private String id;

    /**
     * 商品名称
     */
    @NotBlank(message = "商品名称不能为空")
    private String name;

    /**
     * 商品描述
     */
    private String describe;

    /**
     *商品价格
     */
    @NotNull(message = "商品价格不能为空")
    private Double price;


    private Integer stock;

    private Integer deleted;
}
