package org.bank.controller;

import org.bank.client.PermissionClient;
import org.bank.entity.SysPermission;
import org.bank.utils.DataResult;
import org.bank.vo.req.PermissionAddReqVO;
import org.bank.vo.req.PermissionPageReqVO;
import org.bank.vo.req.PermissionUpdateReqVO;
import org.bank.vo.resp.PageVO;
import org.bank.vo.resp.PermissionRespNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @BelongsProject: BankCloud
 * @BelongsPackage: org.bank.controller
 * @Author: lizongle
 * @Description:
 * @Version: 1.0
 */
@RequestMapping("/sys")
@RestController
public class PermissionController {
    @Autowired
    private PermissionClient permissionClient;

    @PostMapping("/permission")

    public DataResult<SysPermission> addPermission(@RequestBody @Valid PermissionAddReqVO vo) {
        return permissionClient.addPermission(vo);
    }

    @DeleteMapping("/permission/{id}")
    public DataResult deleted(@PathVariable("id") String id) {
        return permissionClient.deleted(id);
    }

    @PostMapping("/permission/delete")
    public DataResult batchDeleted(@RequestBody List<String> ids) {
        return permissionClient.batchDeleted(ids);
    }

    @PutMapping("/permission")
    public DataResult updatePermission(@RequestBody @Valid PermissionUpdateReqVO vo) {
        return permissionClient.updatePermission(vo);
    }

    @GetMapping("/permission/{id}")
    public DataResult<SysPermission> detailInfo(@PathVariable("id") String id) {
        return permissionClient.detailInfo(id);
    }

    @PostMapping("/permissionsForVue")
    public DataResult<PageVO<PermissionRespNode>> pageInfoForVue(@RequestBody PermissionPageReqVO vo) {
        return permissionClient.pageInfoForVue(vo);
    }

    @PostMapping("/permissions")
    public DataResult<PageVO<SysPermission>> pageInfo(@RequestBody PermissionPageReqVO vo) {
        return permissionClient.pageInfo(vo);
    }

    @GetMapping("/permissions")
    public DataResult<List<SysPermission>> getAllMenusPermission() {
        return permissionClient.getAllMenusPermission();
    }

    @GetMapping("/permission/tree")
    public DataResult<List<PermissionRespNode>> getAllMenusPermissionTree(@RequestParam(required = false) String permissionId) {
        return permissionClient.getAllMenusPermissionTree(permissionId);
    }

    @GetMapping("/permission/allTree")
    public DataResult<List<PermissionRespNode>> getAllTree(@RequestParam(required = false) String filterMenuId) {
        return permissionClient.getAllTree(filterMenuId);
    }

    @GetMapping("/permission/tree/all")
    public DataResult<List<PermissionRespNode>> getAllPermissionTree() {
        return permissionClient.getAllPermissionTree();
    }

    @GetMapping("/permission/getChildrenIds")
    public DataResult<List<String>> getChildrenIds(String permId) {
        return permissionClient.getChildrenIds(permId);
    }
}
