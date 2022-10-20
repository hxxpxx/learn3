package org.bank.vo.req;


import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class GoodsPageReqVO {
    /**
     * 第几页
     */
    private int pageNum=1;

    /**
     *分页数量
     */
    private int pageSize=10;

    /**
     * 商品名称
     */
    @NotBlank(message = "商品名称不能为空")
    private String name;



}
