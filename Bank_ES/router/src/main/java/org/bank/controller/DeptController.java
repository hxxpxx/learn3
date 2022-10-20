package org.bank.controller;

import org.bank.entity.SysDept;
import org.bank.entity.SysUser;
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
 * @BelongsProject: BankCloud
 * @BelongsPackage: org.bank.controller
 * @Author: lizongle
 * @Description:
 * @Version: 1.0
 */
@RequestMapping("/sys")
@RestController
public class DeptController {
    @Autowired
    private org.bank.client.DeptClient DeptClient;

    @PostMapping("/dept")
    public DataResult<SysDept> addDept(@RequestBody @Valid DeptAddReqVO vo){
        return DeptClient.addDept(vo);
    }

    @DeleteMapping("/dept/{id}")
    public DataResult deleted(@PathVariable("id") String id){
        return DeptClient.deleted(id);
    }

    @PostMapping("/dept/delete")
    public DataResult batchDeleted(@RequestBody List<String> ids){
        return DeptClient.batchDeleted(ids);
    }

    @PutMapping("/dept")
    public DataResult updateDept(@RequestBody @Valid DeptUpdateReqVO vo){
        return DeptClient.updateDept(vo);
    }

    @GetMapping("/dept/{id}")
    public DataResult<SysDept> detailInfo(@PathVariable("id") String id){
        return DeptClient.detailInfo(id);
    }

    @PostMapping("/depts")
    public DataResult<PageVO<SysDept>> pageInfo(@RequestBody DeptPageReqVO vo){
       return DeptClient.pageInfo(vo);
    }

    @PostMapping("/deptForVue")
    public DataResult<PageVO<DeptRespNodeVO>> pageInfoForVue(@RequestBody DeptPageReqVO vo){
        return DeptClient.pageInfoForVue(vo);
    }

    @GetMapping("/dept/tree")
    public DataResult<List<DeptRespNodeVO>> getTree(@RequestParam(required = false) String deptId){
        return DeptClient.getTree(deptId);
    }

    @GetMapping("/dept/allTree")
    public DataResult<List<DeptRespNodeVO>> getAllTree(@RequestParam(required = false) String filterDeptId){
        return DeptClient.getAllTree(filterDeptId);
    }

    @PostMapping("/dept/users")
    public DataResult<PageVO<SysUser>> pageDeptUserInfos(@RequestBody @Valid UserPageUserByDeptReqVO vo){
        return DeptClient.pageDeptUserInfos(vo);
    }

    @GetMapping("/depts")
    public DataResult<List<SysDept>> getDeptAll(){
       return DeptClient.getDeptAll();
    }
}
