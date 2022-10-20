package org.bank.service;

import org.bank.entity.AccountDetail;
import org.bank.vo.req.AccountDetailPageReqVO;
import org.bank.vo.resp.PageVO;


/**
 * @BelongsProject: Bank_ES
 * @BelongsPackage: org.bank.service
 * @Author: lizongle
 * @Description:
 * @Version: 1.0
 */
public interface AccountDetailService {
    AccountDetail addAccountDetail(AccountDetail vo, String userId);

    PageVO<AccountDetail> pageInfo(AccountDetailPageReqVO vo, String userId);


}
