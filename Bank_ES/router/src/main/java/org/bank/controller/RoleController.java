package org.bank.controller;

import org.bank.client.RoleClient;
import org.bank.entity.SysRole;
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
@RequestMapping("/sys")
@RestController
public class RoleController {
    @Autowired
    private RoleClient roleClient;

    @PostMapping("/role")
    public DataResult<SysRole> addRole(@RequestBody @Valid RoleAddReqVO vo) {
        return roleClient.addRole(vo);
    }

    @DeleteMapping("/role/{id}")
    public DataResult deleted(@PathVariable("id") String id) {
        return roleClient.deleted(id);
    }

    @PutMapping("/role")
    public DataResult updateDept(@RequestBody @Valid RoleUpdateReqVO vo, HttpServletRequest request) {
        return roleClient.updateDept(vo);
    }

    @GetMapping("/role/{id}")
    public DataResult<SysRole> detailInfo(@PathVariable("id") String id) {
        return roleClient.detailInfo(id);
    }


    @PostMapping("/roles")
    public DataResult<PageVO<SysRole>> pageInfo(@RequestBody RolePageReqVO vo) {
        return roleClient.pageInfo(vo);
    }

    @GetMapping("/role/getAll")
    public DataResult<List<SysRole>> getAll(){
        return roleClient.getAll();
    }
}
