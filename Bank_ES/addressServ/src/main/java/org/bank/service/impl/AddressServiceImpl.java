package org.bank.service.impl;

import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.bank.entity.Address;
import org.bank.exception.BusinessException;
import org.bank.exception.code.BaseResponseCode;
import org.bank.mapper.AddressMapper;
import org.bank.service.AddressService;
import org.bank.utils.PageUtils;
import org.bank.vo.req.AddressAddReqVo;
import org.bank.vo.req.AddressPageReqVO;
import org.bank.vo.req.AddressUpdateReqVO;
import org.bank.vo.resp.PageVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class AddressServiceImpl implements AddressService {
    @Autowired
    AddressMapper addressMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Address addAddress(AddressAddReqVo vo, String userId) {
        //查询当前人下是否有此地址
        Address address = new Address();
        address.setIsDefault("1");
        address.setUserId(userId);
        List<Address> list = addressMapper.selectAll(address);
        Address addAddress = new Address();
        BeanUtils.copyProperties(vo,addAddress);
        addAddress.setUserId(userId);
        addAddress.setCreatetime(new Date());
        if (list != null && list.size() > 0&&"1".equals(vo.getIsDefault())) {
            //如果需要变更默认地址  则需要设置之前的不是默认
            Address updateAddress=new Address();
            updateAddress.setId(list.get(0).getId());
            updateAddress.setIsDefault("0");
            updateAddress.setUpdatetime(new Date());
            int updateSize=addressMapper.updateByPrimaryKeySelective(updateAddress);
            if(updateSize<=0){
                throw new BusinessException(BaseResponseCode.OPERATION_ERRO);
            }
        }
        int insertSize=addressMapper.insertSelective(addAddress);
        if(insertSize<=0){
            throw new BusinessException(BaseResponseCode.OPERATION_ERRO);
        }
        return addAddress;
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateAddress(AddressUpdateReqVO vo) {
        Address address=addressMapper.selectByPrimaryKey(vo.getId());
        if(address==null){
            log.error("传入 的 id:{}不合法",vo.getId());
            throw new BusinessException(BaseResponseCode.DATA_ERROR);
        }

        //查询当前人下是否有此地址
        Address isHasDefaultAddress = new Address();
        isHasDefaultAddress.setIsDefault("1");
        isHasDefaultAddress.setUserId(address.getUserId());
        List<Address> list = addressMapper.selectAll(isHasDefaultAddress);

        if (list != null && list.size() > 0&&"1".equals(vo.getIsDefault())) {
            //如果需要变更默认地址  则需要设置之前的不是默认
            Address updateAddress=new Address();
            updateAddress.setId(list.get(0).getId());
            updateAddress.setIsDefault("0");
            updateAddress.setUpdatetime(new Date());
            int updateSize=addressMapper.updateByPrimaryKeySelective(updateAddress);
            if(updateSize<=0){
                throw new BusinessException(BaseResponseCode.OPERATION_ERRO);
            }
        }

        Address update=new Address();
        BeanUtils.copyProperties(vo,update);
        update.setUpdatetime(new Date());
        int count=addressMapper.updateByPrimaryKeySelective(update);
        if(count!=1){
            throw new BusinessException(BaseResponseCode.OPERATION_ERRO);
        }
    }

    @Override
    public Address detailInfo(int id) {
        Address address=addressMapper.selectByPrimaryKey(id);
        if(address==null){
            log.error("传入 的 id:{}不合法",id);
            throw new BusinessException(BaseResponseCode.DATA_ERROR);
        }
        return address;
    }

    @Override
    public void deletedAddress(int id) {
        Address address=addressMapper.selectByPrimaryKey(id);
        if(address==null){
            log.error("传入 的 id:{}不合法",id);
            throw new BusinessException(BaseResponseCode.DATA_ERROR);
        }
        Address update=new Address();
        update.setId(id);
        update.setDeleted(0);
        update.setUpdatetime(new Date());
        int updateSize=addressMapper.updateByPrimaryKeySelective(update);
        if(updateSize<=0){
            throw new BusinessException(BaseResponseCode.OPERATION_ERRO);
        }
    }

    @Override
    public PageVO<Address> pageInfo(AddressPageReqVO vo, String userId) {
        PageHelper.startPage(vo.getPageNum(),vo.getPageSize());
        Address query=new Address();
        BeanUtils.copyProperties(vo,query);
        query.setUserId(userId);
        List<Address> address =addressMapper.selectAll(query);
        return PageUtils.getPageVO(address);
    }

    @Override
    public List<Address> getAddressInfoByUserId(String userId) {
        Address query=new Address();
        query.setUserId(userId);
        List<Address> list=addressMapper.selectAll(query);
        return list;
    }

    @Override
    public List<Address> selectAllAddresss() {
        return addressMapper.selectAll(null);
    }
}
