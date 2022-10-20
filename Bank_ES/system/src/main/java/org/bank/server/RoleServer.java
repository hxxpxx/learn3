package org.bank.server;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.bank.aop.annotation.LogAnnotation;
import org.bank.constants.Constant;
import org.bank.entity.SysRole;
import org.bank.service.RoleService;
import org.bank.utils.DataResult;
import org.bank.vo.req.RoleAddReqVO;
import org.bank.vo.req.RolePageReqVO;
import org.bank.vo.req.RoleUpdateReqVO;
import org.bank.vo.resp.PageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;


/**
 * @BelongsProject: BankCloud
 * @BelongsPackage: org.bank.controller
 * @Author: lizongle
 * @Description:
 * @Version: 1.0
 */
@RestController("roleServer")
public class RoleServer {
    @Autowired
    private RoleService roleService;

    @RequestMapping(value = "/role", method = RequestMethod.POST)
    @LogAnnotation(title = "角色管理",action = "新增角色")
    @RequiresPermissions("sys:role:add")
    public DataResult<SysRole> addRole(@RequestBody @Valid RoleAddReqVO vo){
        DataResult<SysRole> result= DataResult.success();
        result.setData(roleService.addRole(vo));
        return result;
    }
    @RequestMapping(value = "/role/{id}", method = RequestMethod.DELETE)
    @LogAnnotation(title = "角色管理",action = "删除角色")
    @RequiresPermissions("sys:role:deleted")
    public DataResult deleted(@PathVariable("id") String id){
        roleService.deletedRole(id);
        return DataResult.success();
    }

    @RequestMapping(value = "/role", method = RequestMethod.PUT)
    @LogAnnotation(title = "角色管理",action = "更新角色信息")
    @RequiresPermissions("sys:role:update")
    public DataResult updateDept(@RequestBody @Valid RoleUpdateReqVO vo, HttpServletRequest request){
        roleService.updateRole(vo,request.getHeader(Constant.ACCESS_TOKEN));
        return DataResult.success();
    }
    @RequestMapping(value = "/role/{id}", method = RequestMethod.GET)
    @LogAnnotation(title = "角色管理",action = "查询角色详情")
    @RequiresPermissions("sys:role:detail")
    public DataResult<SysRole> detailInfo(@PathVariable("id") String id){
        DataResult<SysRole> result=DataResult.success();
        result.setData(roleService.detailInfo(id));
        return result;
    }
    @RequestMapping(value = "/roles", method = RequestMethod.POST)
    @LogAnnotation(title = "角色管理",action = "分页获取角色信息")
    @RequiresPermissions("sys:role:list")
    public DataResult<PageVO<SysRole>> pageInfo(@RequestBody RolePageReqVO vo){
        DataResult<PageVO<SysRole>> result=DataResult.success();
        result.setData(roleService.pageInfo(vo));
        return result;
    }

    @RequestMapping(value = "/role/getAll", method = RequestMethod.GET)
    @LogAnnotation(title = "角色管理",action = "获取所有角色信息")
    @RequiresPermissions("sys:role:list")
    public DataResult<List<SysRole>> getAll(){
        DataResult<List<SysRole>> result=DataResult.success();
        result.setData(roleService.selectAllRoles());
        return result;
    }

}
