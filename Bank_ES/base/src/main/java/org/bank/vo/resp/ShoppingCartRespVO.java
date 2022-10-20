package org.bank.vo.resp;

import lombok.Data;

import java.util.Date;

@Data
public class ShoppingCartRespVO {
    private Integer id;

    private String goodsId;

    private Double price;

    private Integer num;

    private Double totalAmout;

    private String userId;
    /**
     * 商品名称
     */
    private String name;

    private Date createtime;

    private Date updatetime;
}
