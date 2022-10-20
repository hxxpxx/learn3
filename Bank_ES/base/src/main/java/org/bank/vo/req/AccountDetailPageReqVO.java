package org.bank.vo.req;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class AccountDetailPageReqVO {
    /**
     * 第几页
     */
    private int pageNum=1;

    /**
     *分页数量
     */
    private int pageSize=10;


    private String userId;

    private String accountNo;

    private String key;

    private Double startAmount;

    private Double endAmount;

    private Date startTime;

    private Date endTime;

    private String type;

    private List<String> createtimes;


}
