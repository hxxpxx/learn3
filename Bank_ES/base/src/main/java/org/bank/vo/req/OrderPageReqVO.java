package org.bank.vo.req;

import lombok.Data;

@Data
public class OrderPageReqVO {
    /**
     * 第几页
     */
    private int pageNum=1;

    /**
     *分页数量
     */
    private int pageSize=10;

    private String orderNo;

    private String goodsId;

    private String key;

    private  String userId;

    /**
     * 订单状态
     */
    private String status;



}
