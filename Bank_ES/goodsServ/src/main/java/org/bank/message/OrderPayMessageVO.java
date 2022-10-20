package org.bank.message;

import lombok.Data;

/**
 * @BelongsProject: points_trading
 * @BelongsPackage: org.bank.message
 * @Author: lizongle
 * @CreateTime: 2022-07-21  15:09
 * @Description:
 * @Version: 1.0
 */
@Data
public class OrderPayMessageVO {
    private String orderNo;

    private String goodsId;

    private String title;

    private Double price;

    private Integer num;

    private  Double totalAmout;

}
