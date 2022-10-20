package org.bank.client;

import org.bank.entity.Address;
import org.bank.utils.DataResult;
import org.bank.vo.req.AddressAddReqVo;
import org.bank.vo.req.AddressPageReqVO;
import org.bank.vo.req.AddressUpdateReqVO;
import org.bank.vo.resp.PageVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
@FeignClient(name = "addressService", contextId = "addressServer")
public interface AddressClient {
    @PostMapping("/address/add")
    public DataResult<Address> addAddress(@RequestBody @Valid AddressAddReqVo vo);

    @DeleteMapping("/address/delete/{id}")
    public DataResult deleted(@PathVariable("id") int id) ;

    @PutMapping("/address/update")
    public DataResult updateDept(@RequestBody @Valid AddressUpdateReqVO vo) ;
    @GetMapping("/address/detail/{id}")
    public DataResult<Address> detailInfo(@PathVariable("id") int id);

    @PostMapping("/address/page")
    public DataResult<PageVO<Address>> pageInfo(@RequestBody AddressPageReqVO vo) ;

    @PostMapping("/address/allList")
    public DataResult<List<Address>> getAll();
}
