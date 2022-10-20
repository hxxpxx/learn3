package org.bank.server;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.bank.aop.annotation.LogAnnotation;
import org.bank.constants.Constant;
import org.bank.entity.Address;
import org.bank.service.AddressService;
import org.bank.utils.DataResult;
import org.bank.utils.JwtTokenUtil;
import org.bank.vo.req.AddressAddReqVo;
import org.bank.vo.req.AddressPageReqVO;
import org.bank.vo.req.AddressUpdateReqVO;
import org.bank.vo.resp.PageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;


@RestController("addressServer")
public class AddressServer {
    @Autowired
    AddressService addressService;

    @PostMapping("/address/add")
    @LogAnnotation(title = "地址管理", action = "新增地址")
    @RequiresPermissions("address:add")
    public DataResult<Address> addAddress(@RequestBody @Valid AddressAddReqVo vo, HttpServletRequest request) {
        DataResult<Address> result = DataResult.success();
        String userId = JwtTokenUtil.getUserId(request.getHeader(Constant.ACCESS_TOKEN));
        result.setData(addressService.addAddress(vo, userId));
        return result;
    }

    @DeleteMapping("/address/delete/{id}")
    @LogAnnotation(title = "地址管理", action = "删除地址")
    @RequiresPermissions("address:deleted")
    public DataResult deleted(@PathVariable("id") int id) {
        addressService.deletedAddress(id);
        return DataResult.success();
    }

    @PutMapping("/address/update")
    @LogAnnotation(title = "地址管理", action = "更新地址信息")
    @RequiresPermissions("address:update")
    public DataResult updateDept(@RequestBody @Valid AddressUpdateReqVO vo, HttpServletRequest request) {
        addressService.updateAddress(vo);
        return DataResult.success();
    }

    @GetMapping("/address/detail/{id}")
    @LogAnnotation(title = "地址管理", action = "查询地址详情")
    @RequiresPermissions("address:list")
    public DataResult<Address> detailInfo(@PathVariable("id") int id) {
        DataResult<Address> result = DataResult.success();
        result.setData(addressService.detailInfo(id));
        return result;
    }

    @PostMapping("/address/page")
    @LogAnnotation(title = "地址管理", action = "分页获取地址信息")
    @RequiresPermissions("address:list")
    public DataResult<PageVO<Address>> pageInfo(@RequestBody AddressPageReqVO vo, HttpServletRequest request) {
        DataResult<PageVO<Address>> result = DataResult.success();
        String userId = JwtTokenUtil.getUserId(request.getHeader(Constant.ACCESS_TOKEN));
        result.setData(addressService.pageInfo(vo,userId));
        return result;
    }

    @PostMapping("/address/allList")
    @LogAnnotation(title = "地址管理", action = "获取当前用户所有地址信息")
    @RequiresPermissions("address:list")
    public DataResult<List<Address>> getAll(HttpServletRequest request) {
        DataResult<List<Address>> result = DataResult.success();
        String userId = JwtTokenUtil.getUserId(request.getHeader(Constant.ACCESS_TOKEN));
        result.setData(addressService.getAddressInfoByUserId(userId));
        return result;
    }


}
