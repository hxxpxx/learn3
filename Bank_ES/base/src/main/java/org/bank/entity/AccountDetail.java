package org.bank.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
public class  AccountDetail implements Serializable {
    private Integer id;

    private String accountNo;

    private String userName;

    private String title;

    private String type;

    private Double amount;

    private Double balance;

    private String orderNo;

    private Date createtime;

    private String transactionTime;

    private Integer deleted;


}