package org.bank.vo.req;

import lombok.Data;

@Data
public class AddressPageReqVO {
    /**
     * 第几页
     */
    private int pageNum=1;

    /**
     *分页数量
     */
    private int pageSize=10;

    /**
     * 联系人
     */
    private String contacts;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 联系地址
     */
    private String address;


    /**
     * 是否默认
     */
    private String isDefault;

}
