package org.bank.client;

import org.bank.entity.SysDept;
import org.bank.entity.SysUser;
import org.bank.utils.DataResult;
import org.bank.vo.req.DeptAddReqVO;
import org.bank.vo.req.DeptPageReqVO;
import org.bank.vo.req.DeptUpdateReqVO;
import org.bank.vo.req.UserPageUserByDeptReqVO;
import org.bank.vo.resp.DeptRespNodeVO;
import org.bank.vo.resp.PageVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@FeignClient(name = "orderSystemService", contextId = "deptServer")

public interface DeptClient {
    @PostMapping("/dept/add")
    public DataResult<SysDept> addDept(@RequestBody @Valid DeptAddReqVO vo);

    @DeleteMapping("/dept/{id}")
    public DataResult deleted(@PathVariable("id") String id);


    @RequestMapping(value = "/dept/delete", method = RequestMethod.POST)
    public DataResult batchDeleted(@RequestBody List<String> ids);

    @PutMapping("/dept")
    public DataResult updateDept(@RequestBody @Valid DeptUpdateReqVO vo);

    @GetMapping("/dept/{id}")
    public DataResult<SysDept> detailInfo(@PathVariable("id") String id);

    @PostMapping("/dept/PageInfo")
    public DataResult<PageVO<SysDept>> pageInfo(@RequestBody DeptPageReqVO vo);

    @RequestMapping(value = "/deptForVue", method = RequestMethod.POST)
    public DataResult<PageVO<DeptRespNodeVO>> pageInfoForVue(@RequestBody DeptPageReqVO vo);

    @GetMapping("/dept/tree")
    public DataResult<List<DeptRespNodeVO>> getTree(@RequestParam(required = false) String deptId);

    @RequestMapping(value = "/dept/allTree", method = RequestMethod.GET)
    public DataResult<List<DeptRespNodeVO>> getAllTree(@RequestParam(required = false) String filterDeptId);

    @PostMapping("/dept/users")
    public DataResult<PageVO<SysUser>> pageDeptUserInfos(@RequestBody @Valid UserPageUserByDeptReqVO vo);

    @GetMapping("/dept/list")
    public DataResult<List<SysDept>> getDeptAll();
}
