package org.bank.vo.req;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AddressUpdateReqVO {

    /**
     * 地址信息ID
     */
    @NotBlank(message = "地址Id不能为空")
    private Integer id;

    /**
     * 联系人
     */
    @NotBlank(message = "联系人不能为空")
    private String contacts;

    /**
     * 联系电话
     */
    @NotBlank(message = "联系电话不能为空")
    private String phone;

    /**
     *联系地址
     */
    @NotBlank(message = "联系地址不能为空")
    private String address;

    /**
     *是否默认地址
     */
    private String isDefault;
}
