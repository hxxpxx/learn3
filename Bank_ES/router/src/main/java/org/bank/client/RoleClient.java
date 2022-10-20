package org.bank.client;

import org.bank.entity.SysRole;
import org.bank.utils.DataResult;
import org.bank.vo.req.RoleAddReqVO;
import org.bank.vo.req.RolePageReqVO;
import org.bank.vo.req.RoleUpdateReqVO;
import org.bank.vo.resp.PageVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@FeignClient(name = "orderSystemService",contextId = "roleServer")
public interface RoleClient {

    @PostMapping("/role")
    public DataResult<SysRole> addRole(@RequestBody @Valid RoleAddReqVO vo);
    @DeleteMapping("/role/{id}")
    public DataResult deleted(@PathVariable("id") String id);
    @PutMapping("/role")
    public DataResult updateDept(@RequestBody @Valid RoleUpdateReqVO vo);
    @GetMapping("/role/{id}")
    public DataResult<SysRole> detailInfo(@PathVariable("id") String id);
    @PostMapping("/roles")
    public DataResult<PageVO<SysRole>> pageInfo(@RequestBody RolePageReqVO vo);
    @RequestMapping(value = "/role/getAll", method = RequestMethod.GET)
    public DataResult<List<SysRole>> getAll();
}
