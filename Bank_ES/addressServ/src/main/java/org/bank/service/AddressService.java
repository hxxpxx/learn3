package org.bank.service;

import org.bank.entity.Address;
import org.bank.vo.req.AddressAddReqVo;
import org.bank.vo.req.AddressPageReqVO;
import org.bank.vo.req.AddressUpdateReqVO;
import org.bank.vo.resp.PageVO;

import java.util.List;

public interface AddressService {
    Address addAddress(AddressAddReqVo vo, String userId);

    void updateAddress(AddressUpdateReqVO vo);

    Address detailInfo(int id);

    void deletedAddress(int id);

    PageVO<Address> pageInfo(AddressPageReqVO vo, String userId);

    List<Address> getAddressInfoByUserId(String userId);

    List<Address> selectAllAddresss();
}
