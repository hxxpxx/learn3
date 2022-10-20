package org.bank.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.bank.constants.Constant;
import org.bank.entity.Account;
import org.bank.entity.AccountDetail;
import org.bank.exception.BusinessException;
import org.bank.exception.code.BaseResponseCode;
import org.bank.lock.redisson.RedissonLock;
import org.bank.mapper.AccountMapper;
import org.bank.service.AccountDetailService;
import org.bank.service.AccountService;
import org.bank.service.RedisService;
import org.bank.utils.PageUtils;
import org.bank.vo.req.AccountAddReqVo;
import org.bank.vo.req.AccountPageReqVO;
import org.bank.vo.req.AccountTransactionReqVO;
import org.bank.vo.req.AccountUpdateReqVO;
import org.bank.vo.resp.PageVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @BelongsProject: Bank_ES
 * @BelongsPackage: org.bank.service.impl
 * @Author: lizongle
 * @CreateTime: 2022-07-26  15:44
 * @Description:
 * @Version: 1.0
 */
@Service
@Slf4j
public class AccountServiceImpl implements AccountService {

    @Autowired
    RedisService redisService;
    @Autowired
    AccountMapper accountMapper;

    @Autowired
    RedissonLock redissonLock;

    @Lazy
    @Autowired
    AccountDetailService accountDetailService;

    @Override
    public Account addAccount(AccountAddReqVo vo, String userId) {
        //查询是否有此账号
        Account accountByNo=accountMapper.selectByAccountNo(vo.getAccountNo());
        if(accountByNo!=null&& StringUtils.isNotBlank(accountByNo.getAccountNo())){
            throw new BusinessException(BaseResponseCode.ACCOUNT_ADD_ACCOUNTNO_ERROR);
        }
        if(StringUtils.isBlank(userId)||!userId.equals(vo.getUserId())){
            throw new BusinessException(BaseResponseCode.ACCOUNT_ADD_USER_ERROR);
        }
        Account insert =new Account();
        BeanUtils.copyProperties(vo,insert);
        insert.setCreatetime(new Date());
        int insertSize=accountMapper.insertSelective(insert);
        if(insertSize==0){
            throw new BusinessException(BaseResponseCode.OPERATION_ERRO);
        }
        //将此信息放入redis
        accountByNo=accountMapper.selectByAccountNo(vo.getAccountNo());
        redisService.set(Constant.ACCOUNTNO_KEY+accountByNo.getAccountNo(),accountByNo);
        return accountByNo;
    }

    @Override
    public void updateAccount(AccountUpdateReqVO vo,String userId) {
        //查询是否由此账户信息
        Account accountInfo=accountMapper.selectByPrimaryKey(vo.getId());
        if(accountInfo==null||StringUtils.isBlank(accountInfo.getAccountNo())){
            throw new BusinessException(BaseResponseCode.ACCOUNT_ISEXIT_ERROR);
        }
        if(StringUtils.isBlank(userId)||!userId.equals(vo.getUserId())){
            throw new BusinessException(BaseResponseCode.ACCOUNT_UPDATE_USER_ERROR);
        }
        Account update = new Account();
        BeanUtils.copyProperties(vo,update);
        update.setUpdatetime(new Date());
        int updateSize=accountMapper.updateByPrimaryKeySelective(update);
        if(updateSize==0){
            throw new BusinessException(BaseResponseCode.OPERATION_ERRO);
        }

        Account accountByNo=accountMapper.selectByAccountNo(vo.getAccountNo());
        redisService.set(Constant.ACCOUNTNO_KEY+accountByNo.getAccountNo(),accountByNo);
    }

    @Override
    public Account detailInfoForAccountNo(String accountNo) {
        if(redisService.hasKey(Constant.ACCOUNTNO_KEY+accountNo)
                &&redisService.get(Constant.ACCOUNTNO_KEY+accountNo)!=null){
            return JSONObject.parseObject(redisService.get(Constant.ACCOUNTNO_KEY+accountNo).toString(),Account.class);
        }else{
            Account accountInfo=accountMapper.selectByAccountNo(accountNo);
            if(accountInfo==null||StringUtils.isBlank(accountInfo.getAccountNo())){
                throw new BusinessException(BaseResponseCode.ACCOUNT_ISEXIT_ERROR);
            }
            return accountInfo;
        }
    }

    @Override
    public void deletedAccount(int id,String userId) {
        Account accountInfo=accountMapper.selectByPrimaryKey(id);
        if(accountInfo==null||StringUtils.isBlank(accountInfo.getAccountNo())){
            throw new BusinessException(BaseResponseCode.ACCOUNT_ISEXIT_ERROR);
        }
        if(StringUtils.isBlank(userId)||!userId.equals(accountInfo.getUserId())){
            throw new BusinessException(BaseResponseCode.ACCOUNT_UPDATE_USER_ERROR);
        }
        Account update=new Account();
        update.setId(accountInfo.getId());
        update.setDeleted(0);
        update.setUpdatetime(new Date());
        int updateSize=accountMapper.updateByPrimaryKeySelective(update);
        if(updateSize==0){
            throw new BusinessException(BaseResponseCode.OPERATION_ERRO);
        }
        //删除缓存
        redisService.delete(Constant.ACCOUNTNO_KEY+accountInfo.getAccountNo());
    }

    @Override
    public PageVO<Account> pageInfo(AccountPageReqVO vo, String userId) {
        if(StringUtils.isBlank(userId)){
            throw new BusinessException(BaseResponseCode.ACCOUNT_QUERY_USER_ERROR);
        }
        PageHelper.startPage(vo.getPageNum(),vo.getPageSize());
        Account query=new Account();
        BeanUtils.copyProperties(vo,query);
        query.setUserId(userId);
        List<Account> accountList =accountMapper.selectAll(query);
        return PageUtils.getPageVO(accountList);
    }

    @Override
    public List<Account> getAccountInfoByUserId(String userId) {
        if(StringUtils.isBlank(userId)){
            throw new BusinessException(BaseResponseCode.ACCOUNT_QUERY_USER_ERROR);
        }
        Account query=new Account();
        query.setUserId(userId);
        List<Account> accountList=accountMapper.selectAll(query);
        return accountList;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    @Override
    public boolean deductionByAccountNo(AccountTransactionReqVO accountTransactionReqVO) {
        try{
            if(redissonLock.lock(Constant.ACCOUNT_BALANCE_LOCK_KEY+accountTransactionReqVO.getAccountNo(), 10)){
                //校验账户信息
                boolean checkAccount= checkAccount(accountTransactionReqVO.getAccountNo(),accountTransactionReqVO.getUserId());
                if(!checkAccount){
                    throw  new BusinessException(BaseResponseCode.ORDER_ACCOUNT_CHECK_ERROR);
                }
                Account accountInfo=detailInfoForAccountNo(accountTransactionReqVO.getAccountNo());
                Double amount=accountTransactionReqVO.getAmount();
                //判断是退款还是交易
                AccountDetail accountDetailInsert=new AccountDetail();
                accountDetailInsert.setAccountNo(accountTransactionReqVO.getAccountNo());
                accountDetailInsert.setAmount(accountTransactionReqVO.getAmount());
                accountDetailInsert.setOrderNo(accountTransactionReqVO.getOrderNo());
                accountDetailInsert.setTitle(accountTransactionReqVO.getTitle());
                String type=accountTransactionReqVO.isType()?"deduction":"refund";
                accountDetailInsert.setType(type);
                if(accountTransactionReqVO.isType()){//扣款
                    //校验余额
                    Double balance=accountInfo.getBalance();
                    int res =  balance.compareTo(amount);
                    if(res<0){
                        throw  new BusinessException(BaseResponseCode.ORDER_ACCOUNT_BALANCE_DEFICIENCY);
                    }
                    BigDecimal balanceDecimal = new BigDecimal(Double.toString(balance));
                    BigDecimal deductionDecimal = new BigDecimal(Double.toString(amount));
                    Double afterBalance=balanceDecimal.subtract(deductionDecimal).doubleValue();
                    Account updateAccount=new Account();
                    updateAccount.setId(accountInfo.getId());
                    updateAccount.setBalance(afterBalance);
                    updateAccount.setUpdatetime(new Date());
                    int updateSize=accountMapper.updateByPrimaryKeySelective(updateAccount);
                    if(updateSize==0){
                        throw  new BusinessException(BaseResponseCode.ORDER_ACCOUNT_BALANCE_DEDUCTION_ERROR);
                    }
                    //重置缓存
                    accountInfo.setBalance(afterBalance);
                    redisService.set(Constant.ACCOUNTNO_KEY+accountTransactionReqVO.getAccountNo(),accountInfo);
                    //组装明细信息
                    accountDetailInsert.setBalance(afterBalance);
                }else{
                    //校验余额
                    Double balance=accountInfo.getBalance();
                    BigDecimal balanceDecimal = new BigDecimal(Double.toString(balance));
                    BigDecimal deductionDecimal = new BigDecimal(Double.toString(amount));
                    Double afterBalance=balanceDecimal.add(deductionDecimal).doubleValue();
                    Account updateAccount=new Account();
                    updateAccount.setId(accountInfo.getId());
                    updateAccount.setBalance(afterBalance);
                    updateAccount.setUpdatetime(new Date());
                    int updateSize=accountMapper.updateByPrimaryKeySelective(updateAccount);
                    if(updateSize==0){
                        throw  new BusinessException(BaseResponseCode.ORDER_ACCOUNT_BALANCE_DEDUCTION_ERROR);
                    }
                    //重置缓存
                    accountInfo.setBalance(afterBalance);
                    redisService.set(Constant.ACCOUNTNO_KEY+accountTransactionReqVO.getAccountNo(),accountInfo);
                    //组装明细信息
                    accountDetailInsert.setBalance(afterBalance);
                }
                //添加明细信息
                accountDetailInsert.setCreatetime(new Date());
                accountDetailService.addAccountDetail(accountDetailInsert,accountTransactionReqVO.getUserId());
                return true;
            }else {
                throw  new BusinessException(BaseResponseCode.ORDER_ACCOUNT_GET_LOCK_ERROR);
            }
        }catch (Exception e){
            log.error("账户交易异常，异常原因为",e);
            if(e instanceof  BusinessException){
                throw e;
            }else {
                throw  new BusinessException(BaseResponseCode.ORDER_ACCOUNT_OPERATION_ERROR);
            }
        }finally {
            redissonLock.release(Constant.ACCOUNT_BALANCE_LOCK_KEY+accountTransactionReqVO.getAccountNo());
        }
    }

    private boolean checkAccount(String accountNo,String userId){
        if(StringUtils.isBlank(accountNo)||StringUtils.isBlank(userId)){
            return false;
        }
        Account accountInfo=detailInfoForAccountNo(accountNo);
        if(accountInfo!=null&&userId.equals(accountInfo.getUserId())){
            return true;
        }else{
            return false;
        }

    }
}
