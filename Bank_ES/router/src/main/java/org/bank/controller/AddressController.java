package org.bank.controller;

import org.bank.aop.annotation.LogAnnotation;
import org.bank.client.AddressClient;
import org.bank.constants.Constant;
import org.bank.entity.Address;
import org.bank.entity.OrderAddress;
import org.bank.utils.DataResult;
import org.bank.vo.req.AddressAddReqVo;
import org.bank.vo.req.AddressPageReqVO;
import org.bank.vo.req.AddressUpdateReqVO;
import org.bank.vo.resp.PageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RequestMapping("/address")
@RestController
public class AddressController {
    @Autowired
    AddressClient addressClient;

    @PostMapping("/add")
    public DataResult<Address> addAddress(@RequestBody @Valid AddressAddReqVo vo) {
        return addressClient.addAddress(vo);
    }

    @DeleteMapping("/delete/{id}")
    public DataResult deleted(@PathVariable("id") int id) {
        return addressClient.deleted(id);
    }

    @PutMapping("/update")
    public DataResult updateDept(@RequestBody @Valid AddressUpdateReqVO vo) {
        return addressClient.updateDept(vo);
    }

    @GetMapping("/detail/{id}")
    public DataResult<Address> detailInfo(@PathVariable("id") int id) {
        return addressClient.detailInfo(id);
    }

    @PostMapping("/page")
    public DataResult<PageVO<Address>> pageInfo(@RequestBody AddressPageReqVO vo) {
        return addressClient.pageInfo(vo);
    }

    @PostMapping("/allListForUserId")
    public DataResult<List<Address>> getAll() {
        return addressClient.getAll();
    }


}
