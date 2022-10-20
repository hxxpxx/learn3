package org.bank.service;

import org.bank.entity.Account;
import org.bank.entity.Account;
import org.bank.vo.req.AccountAddReqVo;
import org.bank.vo.req.AccountPageReqVO;
import org.bank.vo.req.AccountTransactionReqVO;
import org.bank.vo.req.AccountUpdateReqVO;
import org.bank.vo.resp.PageVO;

import java.util.List;

/**
 * @BelongsProject: Bank_ES
 * @BelongsPackage: org.bank.service
 * @Author: lizongle
 * @Description:
 * @Version: 1.0
 */
public interface AccountService {
    Account addAccount(AccountAddReqVo vo, String userId);

    void updateAccount(AccountUpdateReqVO vo,String userId);

    Account detailInfoForAccountNo(String accountNo);

    void deletedAccount(int id,String userId);

    PageVO<Account> pageInfo(AccountPageReqVO vo, String userId);

    List<Account> getAccountInfoByUserId(String userId);


    boolean deductionByAccountNo(AccountTransactionReqVO accountTransactionReqVO);
}
