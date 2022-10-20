package org.bank.server;


import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.bank.aop.annotation.LogAnnotation;
import org.bank.entity.SysDept;
import org.bank.entity.SysUser;
import org.bank.service.DeptService;
import org.bank.utils.DataResult;
import org.bank.vo.req.DeptAddReqVO;
import org.bank.vo.req.DeptPageReqVO;
import org.bank.vo.req.DeptUpdateReqVO;
import org.bank.vo.req.UserPageUserByDeptReqVO;
import org.bank.vo.resp.DeptRespNodeVO;
import org.bank.vo.resp.PageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


/**
 * 将此DeptController注册到注册中心
 *
 * @BelongsProject: BankCloud
 * @BelongsPackage: org.bank.controller
 * @Author: lizongle
 * @Description:
 * @Version: 1.0
 */

@RestController("deptServer")
public class DeptServer {
    @Autowired
    private DeptService deptService;


    @RequestMapping(value = "/dept/add", method = RequestMethod.POST)
    @LogAnnotation(title = "机构管理", action = "新增组织")
    @RequiresPermissions("sys:dept:add")
    public DataResult<SysDept> addDept(@RequestBody @Valid DeptAddReqVO vo) {
        DataResult<SysDept> result = DataResult.success();
        result.setData(deptService.addDept(vo));
        return result;
    }

    @RequestMapping(value = "/dept/{id}", method = RequestMethod.DELETE)
    @LogAnnotation(title = "机构管理", action = "删除组织")
    @RequiresPermissions("sys:dept:deleted")
    public DataResult deleted(@PathVariable("id") String id) {
        deptService.deleted(id);
        return DataResult.success();
    }

    @RequestMapping(value = "/dept/delete", method = RequestMethod.POST)
    @LogAnnotation(title = "机构管理", action = "删除组织")
    @RequiresPermissions("sys:dept:deleted")
    public DataResult batchDeleted(@RequestBody List<String> ids) {
        deptService.deleted(ids);
        return DataResult.success();
    }

    @RequestMapping(value = "/dept", method = RequestMethod.PUT)
    @LogAnnotation(title = "机构管理", action = "更新组织信息")
    @RequiresPermissions("sys:dept:update")
    public DataResult updateDept(@RequestBody @Valid DeptUpdateReqVO vo) {
        deptService.updateDept(vo);
        return DataResult.success();
    }

    @RequestMapping(value = "/dept/{id}", method = RequestMethod.GET)
    @LogAnnotation(title = "机构管理", action = "查询组织详情")
    @RequiresPermissions("sys:dept:detail")
    public DataResult<SysDept> detailInfo(@PathVariable("id") String id) {
        DataResult<SysDept> result = DataResult.success();
        result.setData(deptService.detailInfo(id));
        return result;
    }

    @RequestMapping(value = "/dept/PageInfo", method = RequestMethod.POST)
    @LogAnnotation(title = "机构管理", action = "分页获取组织信息")
    @RequiresPermissions("sys:dept:list")
    public DataResult<PageVO<SysDept>> pageInfo(@RequestBody DeptPageReqVO vo) {
        DataResult<PageVO<SysDept>> result = DataResult.success();
        result.setData(deptService.pageInfo(vo));
        return result;
    }

    @RequestMapping(value = "/deptForVue", method = RequestMethod.POST)
    @LogAnnotation(title = "机构管理",action = "vue页面获取组织信息")
    @RequiresPermissions("sys:dept:list")
    public DataResult<PageVO<DeptRespNodeVO>> pageInfoForVue(@RequestBody DeptPageReqVO vo){
        DataResult<PageVO<DeptRespNodeVO>> result=DataResult.success();
        result.setData(deptService.pageInfoForVue(vo));
        return result;
    }

    @RequestMapping(value = "/dept/tree", method = RequestMethod.GET)
    @LogAnnotation(title = "机构管理", action = "树型组织列表")
    @RequiresPermissions(value = {"sys:user:update", "sys:user:add", "sys:dept:add", "sys:dept:update"}, logical = Logical.OR)
    public DataResult<List<DeptRespNodeVO>> getTree(@RequestParam(required = false) String deptId) {
        DataResult<List<DeptRespNodeVO>> result = DataResult.success();
        result.setData(deptService.deptTreeList(deptId));
        return result;
    }

    @RequestMapping(value = "/dept/allTree", method = RequestMethod.GET)
    @LogAnnotation(title = "机构管理",action = "VUE树型组织列表")
    @RequiresPermissions(value = {"sys:user:update","sys:user:add","sys:dept:add","sys:dept:update"},logical = Logical.OR)
    public DataResult<List<DeptRespNodeVO>> getAllTree(@RequestParam(required = false) String filterDeptId){
        DataResult<List<DeptRespNodeVO>> result=DataResult.success();
        result.setData(deptService.getALLdeptTreeList(filterDeptId));
        return result;
    }

    @RequestMapping(value = "/dept/users", method = RequestMethod.POST)
    @LogAnnotation(title = "机构管理", action = "分页获取组织下所有用户")
    @RequiresPermissions("sys:dept:user:list")
    public DataResult<PageVO<SysUser>> pageDeptUserInfos(@RequestBody @Valid UserPageUserByDeptReqVO vo) {
        DataResult<PageVO<SysUser>> result = DataResult.success();
        result.setData(deptService.pageDeptUserInfo(vo));
        return result;
    }

    @RequestMapping(value = "/dept/list", method = RequestMethod.GET)
    @LogAnnotation(title = "机构管理", action = "获取所有组织机构")
    @RequiresPermissions("sys:dept:list")
    public DataResult<List<SysDept>> getDeptAll() {
        DataResult<List<SysDept>> result = DataResult.success();
        result.setData(deptService.selectAll());
        return result;
    }
}
