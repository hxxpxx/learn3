package org.bank.client;

import org.bank.entity.SysPermission;
import org.bank.utils.DataResult;
import org.bank.vo.req.PermissionAddReqVO;
import org.bank.vo.req.PermissionPageReqVO;
import org.bank.vo.req.PermissionUpdateReqVO;
import org.bank.vo.resp.PageVO;
import org.bank.vo.resp.PermissionRespNode;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@FeignClient(name = "orderSystemService",contextId = "permissionServer")
public interface PermissionClient {

    @PostMapping("/permission")
    public DataResult<SysPermission> addPermission(@RequestBody @Valid PermissionAddReqVO vo);

    @DeleteMapping("/permission/{id}")
    public DataResult deleted(@PathVariable("id") String id);

    @RequestMapping(value = "/permission/delete", method = RequestMethod.POST)
    public DataResult batchDeleted(@RequestBody List<String> ids);

    @PutMapping("/permission")
    public DataResult updatePermission(@RequestBody @Valid PermissionUpdateReqVO vo);

    @GetMapping("/permission/{id}")
    public DataResult<SysPermission> detailInfo(@PathVariable("id") String id);

    @PostMapping("/permissions")
    public DataResult<PageVO<SysPermission>> pageInfo(@RequestBody PermissionPageReqVO vo);

    @RequestMapping(value = "/permissionsForVue", method = RequestMethod.POST)
    public DataResult<PageVO<PermissionRespNode>> pageInfoForVue(@RequestBody PermissionPageReqVO vo);

    @GetMapping("/permissions")
    public DataResult<List<SysPermission>> getAllMenusPermission();

    @GetMapping("/permission/tree")
    public DataResult<List<PermissionRespNode>> getAllMenusPermissionTree(@RequestParam(required = false) String permissionId);

    @RequestMapping(value = "/permission/allTree", method = RequestMethod.GET)
    public DataResult<List<PermissionRespNode>> getAllTree(@RequestParam(required = false) String filterMenuId);

    @GetMapping("/permission/tree/all")
    public DataResult<List<PermissionRespNode>> getAllPermissionTree();

    @RequestMapping(value = "/permission/getChildrenIds", method = RequestMethod.GET)
    public DataResult<List<String>> getChildrenIds(@RequestParam(required = false) String permId);
}
