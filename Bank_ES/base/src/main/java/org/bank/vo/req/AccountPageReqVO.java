package org.bank.vo.req;

import lombok.Data;

@Data
public class AccountPageReqVO {
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


}
