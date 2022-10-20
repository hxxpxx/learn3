package org.bank.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Order implements Serializable {
    private String orderNo;

    private String goodsId;

    private Integer addressId;

    private String accountNo;

    private String title;

    private Integer num;

    private Double price;

    private Double totalAmout;

    private String userId;

    private String status;

    private String addressInfo;

    private String phone;

    private String contacts;

    private Date createtime;

    private Date paytime;

    private Date updatetime;

    private Goods goods;

    private String remark;

    private OrderAddress orderAddress;

    private OrderSnapshot orderSnapshot;

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods == null ? null :goods;
    }


}