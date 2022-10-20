package org.bank.service;


import org.bank.entity.SysDept;
import org.bank.entity.SysDeptVue;
import org.bank.entity.SysUser;
import org.bank.vo.req.DeptAddReqVO;
import org.bank.vo.req.DeptPageReqVO;
import org.bank.vo.req.DeptUpdateReqVO;
import org.bank.vo.req.UserPageUserByDeptReqVO;
import org.bank.vo.resp.DeptRespNodeVO;
import org.bank.vo.resp.PageVO;
import java.util.List;


public interface DeptService {

    SysDept addDept(DeptAddReqVO vo);

    void updateDept(DeptUpdateReqVO vo);

    SysDept detailInfo(String id);

    void deleted(String id);

    void deleted(List<String> ids);

    PageVO<SysDept> pageInfo(DeptPageReqVO vo);

    PageVO<DeptRespNodeVO> pageInfoForVue(DeptPageReqVO vo);

    List<DeptRespNodeVO> deptTreeList(String deptId);

    List<DeptRespNodeVO> getALLdeptTreeList(String filterDeptId);

    PageVO<SysUser> pageDeptUserInfo(UserPageUserByDeptReqVO vo);

    List<SysDept> selectAll();
}
