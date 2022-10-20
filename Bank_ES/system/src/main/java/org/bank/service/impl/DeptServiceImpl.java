package org.bank.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.bank.constants.Constant;
import org.bank.entity.SysDept;
import org.bank.entity.SysUser;
import org.bank.exception.BusinessException;
import org.bank.exception.code.BaseResponseCode;
import org.bank.mapper.SysDeptMapper;
import org.bank.service.DeptService;
import org.bank.service.RedisService;
import org.bank.service.UserService;
import org.bank.utils.CodeUtil;
import org.bank.utils.PageUtils;
import org.bank.vo.req.DeptAddReqVO;
import org.bank.vo.req.DeptPageReqVO;
import org.bank.vo.req.DeptUpdateReqVO;
import org.bank.vo.req.UserPageUserByDeptReqVO;
import org.bank.vo.resp.DeptRespNodeVO;
import org.bank.vo.resp.PageVO;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@Service
@Slf4j
public class DeptServiceImpl implements DeptService {
    @Autowired
    private RedisService redisService;
    @Autowired
    private SysDeptMapper sysDeptMapper;
    @Autowired
    private UserService userService;
    @Override
    public SysDept addDept(DeptAddReqVO vo) {
        String relationCode;
        long result=redisService.incrby(Constant.DEPT_CODE_KEY,1);
        String deptCode= CodeUtil.deptCode(String.valueOf(result),6,"0");
        SysDept parent=sysDeptMapper.selectByPrimaryKey(vo.getPid());
        if(vo.getPid().equals("0")){
            relationCode=deptCode;
        }else if(null==parent) {
            log.error("传入的 pid:{}不合法",vo.getPid());
            throw new BusinessException(BaseResponseCode.DATA_ERROR);
        }else {
            relationCode=parent.getRelationCode()+deptCode;
        }
        SysDept sysDept=new SysDept();
        BeanUtils.copyProperties(vo,sysDept);
        sysDept.setCreateTime(new Date());
        sysDept.setId(UUID.randomUUID().toString());
        sysDept.setDeptNo(deptCode);
        sysDept.setRelationCode(relationCode);
        int count=sysDeptMapper.insertSelective(sysDept);
        if (count!=1){
            throw new BusinessException(BaseResponseCode.OPERATION_ERRO);
        }
        return sysDept;
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateDept(DeptUpdateReqVO vo) {

        SysDept sysDept=sysDeptMapper.selectByPrimaryKey(vo.getId());
        if(null==sysDept){
            log.error("传入 的 id:{}不合法",vo.getId());
            throw new BusinessException(BaseResponseCode.DATA_ERROR);
        }
        SysDept update=new SysDept();
        BeanUtils.copyProperties(vo,update);
        update.setUpdateTime(new Date());
        int count=sysDeptMapper.updateByPrimaryKeySelective(update);
        if(count!=1){
            throw new BusinessException(BaseResponseCode.OPERATION_ERRO);
        }

        /**
         * 说明层级发生了变化
         */
        if(!StringUtils.isEmpty(vo.getPid())&&!vo.getPid().equals(sysDept.getPid())){
            SysDept parent=sysDeptMapper.selectByPrimaryKey(vo.getPid());
            if(!vo.getPid().equals("0")&&null==parent){
                log.error("传入 的 pid:{}不合法",vo.getId());
                throw new BusinessException(BaseResponseCode.DATA_ERROR);
            }
            SysDept oldParent=sysDeptMapper.selectByPrimaryKey(sysDept.getPid());
            String oldRelationCode;
            String newRelationCode;
            /**
             * 根目录降到其他目录
             */
            if (sysDept.getPid().equals("0")){
                oldRelationCode=sysDept.getDeptNo();
                newRelationCode=parent.getRelationCode()+sysDept.getDeptNo();
            }else if(vo.getPid().equals("0")){//其他目录升级到跟目录
                oldRelationCode=sysDept.getRelationCode();
                newRelationCode=sysDept.getDeptNo();
            }else {
                oldRelationCode=oldParent.getRelationCode();
                newRelationCode=parent.getRelationCode();
            }
            sysDeptMapper.updateRelationCode(oldRelationCode,newRelationCode,sysDept.getRelationCode());
        }
    }
    @Override
    public SysDept detailInfo(String id) {
        return sysDeptMapper.selectByPrimaryKey(id);
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED,rollbackFor = {Exception.class})
    public void deleted(String id) {
        SysDept sysDept=sysDeptMapper.selectByPrimaryKey(id);
        if (null==sysDept){
            log.error("传入 的 id:{}不合法",id);
            throw new BusinessException(BaseResponseCode.DATA_ERROR);
        }
        List<String> deptIds = sysDeptMapper.selectChildIds(sysDept.getRelationCode());
        List<SysUser> list = userService.getUserListByDeptIds(deptIds);
        if(!list.isEmpty()){
            throw new BusinessException(BaseResponseCode.NOT_PERMISSION_DELETED_DEPT);
        }
        sysDept.setDeleted(0);
        sysDept.setUpdateTime(new Date());
        int count=sysDeptMapper.updateByPrimaryKeySelective(sysDept);
        if (count!=1){
            throw new BusinessException(BaseResponseCode.OPERATION_ERRO);
        }
        if(deptIds!=null&&deptIds.size()>0){
            count=sysDeptMapper.deleteFormIds(deptIds);
            if(count==0){
                throw new BusinessException(BaseResponseCode.OPERATION_ERRO);
            }
        }
    }
    @Transactional(propagation= Propagation.REQUIRED,rollbackFor = {Exception.class})
    @Override
    public void deleted(List<String> ids) {
        if(ids!=null){
            for(String id:ids){
                deleted(id);
            }
        }
    }

    @Override
    public PageVO<SysDept> pageInfo(DeptPageReqVO vo) {
        PageHelper.startPage(vo.getPageNum(),vo.getPageSize());
        List<SysDept> sysDepts = sysDeptMapper.selectAll(vo);
        return PageUtils.getPageVO(sysDepts);
    }

    @Override
    public PageVO<DeptRespNodeVO> pageInfoForVue(DeptPageReqVO vo) {
        Page<DeptRespNodeVO> page=PageHelper.startPage(vo.getPageNum(),vo.getPageSize());
        if(StringUtils.isEmpty(vo.getPid())&&StringUtils.isEmpty(vo.getName())&&StringUtils.isEmpty(vo.getStatus())){
            vo.setPid("0");
        }
        List<SysDept> list=sysDeptMapper.selectAll(vo);
        //组装LIST
        List<DeptRespNodeVO> sysDepts =null;
        if(list!=null&&list.size()>0){
            sysDepts=new ArrayList<>();
            for(SysDept dept: list){
                List<SysDept> childDeptList=new ArrayList<>();
                if(dept!=null){
                    childDeptList=sysDeptMapper.selectChilds(dept.getRelationCode());
                }
                DeptRespNodeVO deptTree=new DeptRespNodeVO();
                BeanUtils.copyProperties(dept,deptTree);
                deptTree.setTitle(dept.getName());
                deptTree.setLabel(dept.getName());
                deptTree.setSpread(true);
                List<DeptRespNodeVO> childrenList=getChild(dept.getId(),childDeptList);
                //此处为了兼容vue页面选择
                deptTree.setChildren(null);
                if(childrenList!=null&&childrenList.size()>0){
                    deptTree.setHasChildren(true);
                }else{
                    deptTree.setHasChildren(false);
                }
                sysDepts.add(deptTree);
            }
        }
        PageVO<DeptRespNodeVO> result = new PageVO<>();
        result.setTotalRows(sysDepts==null?0l:sysDepts.size());
        result.setTotalPages(1);
        result.setPageNum(page.getPageNum());
        result.setPageSize(page.getPageSize());
        result.setCurPageSize(page.getPageSize());
        result.setList(sysDepts);
        return result;
    }
    @Override
    public List<DeptRespNodeVO> deptTreeList(String deptId) {
        List<SysDept> list;
        if(StringUtils.isEmpty(deptId)){
            list=sysDeptMapper.selectAll(null);
        }else {
            SysDept sysDept = sysDeptMapper.selectByPrimaryKey(deptId);
            if (sysDept==null){
                throw new BusinessException(BaseResponseCode.DATA_ERROR);
            }
            List<String> childIds = sysDeptMapper.selectChildIds(sysDept.getRelationCode());
            list=sysDeptMapper.selectAllByNotContainChild(childIds);
        }
        //默认加一个顶级方便新增顶级部门
        DeptRespNodeVO respNodeVO=new DeptRespNodeVO();
        respNodeVO.setTitle("默认顶级部门");
        respNodeVO.setName("默认顶级部门");
        respNodeVO.setLabel("默认顶级部门");
        respNodeVO.setId("0");
        respNodeVO.setSpread(true);
        List<DeptRespNodeVO> childrenList=getTree(list);
        respNodeVO.setChildren(childrenList);
        if(childrenList!=null&&childrenList.size()>0){
            respNodeVO.setHasChildren(true);
        }else{
            respNodeVO.setHasChildren(false);
        }
        List<DeptRespNodeVO> result=new ArrayList<>();
        result.add(respNodeVO);
        return result;
    }

    /**
     * 获取所有子叶子节点
     * @param filterDeptId
     * @return
     */
    @Override
    public List<DeptRespNodeVO> getALLdeptTreeList(String filterDeptId) {
        DeptPageReqVO vo=new DeptPageReqVO();
        vo.setPid("0");
        List<SysDept> list=sysDeptMapper.selectAll(vo);
        //组装LIST
        List<DeptRespNodeVO> sysDepts =null;
        if(list!=null&&list.size()>0){
            sysDepts=new ArrayList<>();
            for(SysDept dept: list){
                if(!StringUtils.isEmpty(filterDeptId)&&filterDeptId.equals(dept.getId())){
                    continue;
                }
                List<SysDept> childDeptList=new ArrayList<>();
                if(dept!=null){
                    childDeptList=sysDeptMapper.selectChilds(dept.getRelationCode());
                }
                DeptRespNodeVO deptTree=new DeptRespNodeVO();
                BeanUtils.copyProperties(dept,deptTree);
                deptTree.setTitle(dept.getName());
                deptTree.setLabel(dept.getName());
                deptTree.setSpread(true);
                List<DeptRespNodeVO> childrenList=getChild(dept.getId(),childDeptList);
                //此处为了兼容vue页面选择
                deptTree.setChildren(childrenList);
                if(childrenList!=null&&childrenList.size()>0){
                    deptTree.setHasChildren(true);
                }else{
                    deptTree.setHasChildren(false);
                }
                sysDepts.add(deptTree);
            }
        }
        //默认加一个顶级方便新增顶级部门
        DeptRespNodeVO respNodeVO=new DeptRespNodeVO();
        respNodeVO.setTitle("默认顶级部门");
        respNodeVO.setName("默认顶级部门");
        respNodeVO.setLabel("默认顶级部门");
        respNodeVO.setId("0");
        respNodeVO.setSpread(true);

        if(sysDepts!=null&&sysDepts.size()>0){
            respNodeVO.setHasChildren(true);
            respNodeVO.setChildren(sysDepts);
        }else{
            respNodeVO.setHasChildren(false);
        }
        List<DeptRespNodeVO> result=new ArrayList<>();
        result.add(respNodeVO);
        return result;
    }



    private List<DeptRespNodeVO> getTree(List<SysDept> all){
        List<DeptRespNodeVO> list=new ArrayList<>();
        for(SysDept sysDept:all){
            if(sysDept.getPid().equals("0")){
                DeptRespNodeVO deptTree=new DeptRespNodeVO();
                BeanUtils.copyProperties(sysDept,deptTree);
                deptTree.setTitle(sysDept.getName());
                deptTree.setName(sysDept.getName());
                deptTree.setLabel(sysDept.getName());
                deptTree.setSpread(true);
                deptTree.setChildren(getChild(sysDept.getId(),all));
                list.add(deptTree);
            }
        }
        return list;
    }
    private List<DeptRespNodeVO>getChild(String id, List<SysDept> all){
        List<DeptRespNodeVO> list=new ArrayList<>();
        for(SysDept sysDept:all){
            if(sysDept.getPid().equals(id)){
                DeptRespNodeVO deptTree=new DeptRespNodeVO();
                BeanUtils.copyProperties(sysDept,deptTree);
                deptTree.setTitle(sysDept.getName());
                deptTree.setLabel(sysDept.getName());
                List<DeptRespNodeVO> childrenList=getChild(sysDept.getId(),all);
                //此处为了兼容vue页面选择
                deptTree.setChildren(childrenList);
                if(childrenList!=null&&childrenList.size()>0){
                    deptTree.setHasChildren(true);
                }else{
                    deptTree.setHasChildren(false);
                }
                list.add(deptTree);
            }
        }
        return list;
    }

    private List<DeptRespNodeVO> getChildFilterDepeId(String id, List<SysDept> all,String filterDeptId){
        List<DeptRespNodeVO> list=new ArrayList<>();
        for(SysDept sysDept:all){
            if(!StringUtils.isEmpty(filterDeptId)&&filterDeptId.equals(sysDept.getId())){
                continue;
            }
            if(sysDept.getPid().equals(id)){
                DeptRespNodeVO deptTree=new DeptRespNodeVO();
                BeanUtils.copyProperties(sysDept,deptTree);
                deptTree.setTitle(sysDept.getName());
                deptTree.setLabel(sysDept.getName());
                List<DeptRespNodeVO> childrenList=getChild(sysDept.getId(),all);
                //此处为了兼容vue页面选择
                deptTree.setChildren(childrenList);
                if(childrenList!=null&&childrenList.size()>0){
                    deptTree.setHasChildren(true);
                }else{
                    deptTree.setHasChildren(false);
                }
                list.add(deptTree);
            }
        }
        return list;
    }

    @Override
    public PageVO<SysUser> pageDeptUserInfo(UserPageUserByDeptReqVO vo) {

        SysDept sysDept=sysDeptMapper.selectByPrimaryKey(vo.getDeptId());
        if (null==sysDept){
            log.error("传入 的 id:{}不合法",vo.getDeptId());
            throw new BusinessException(BaseResponseCode.DATA_ERROR);
        }
        List<String> strings = sysDeptMapper.selectChildIds(sysDept.getRelationCode());

        return userService.selectUserInfoByDeptIds(vo.getPageNum(), vo.getPageSize(),strings);
    }

    @Override
    public List<SysDept> selectAll() {
        List<SysDept> list = sysDeptMapper.selectAll(null);
        for (SysDept sysDept:list){
            SysDept parent = sysDeptMapper.selectByPrimaryKey(sysDept.getPid());
            if(parent!=null){
                sysDept.setPidName(parent.getName());
            }
        }
        return list;
    }
}
