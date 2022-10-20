package org.bank.vo.req;

import lombok.Data;

@Data
public class ShoppingCartPageReqVO {
    /**
     * 第几页
     */
    private int pageNum=1;

    /**
     *分页数量
     */
    private int pageSize=10;

    /**
     * 商品
     */
    private String goods_id;


}
