package org.bank.vo.req;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
public class OrderUpdateReqVO {

    @NotBlank(message = "订单号不能为空")
    private String orderNo;


    private String goodsId;


    private String title;


    private Integer num;


    private Double price;


    private Double totalAmout;


    private String userId;


    private Date paytime;

    private Date updatetime;

    private String status;

    private String remark;
}
