package org.bank.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.bank.dao.AccountDetailESDao;
import org.bank.entity.Account;
import org.bank.entity.AccountDetail;
import org.bank.entity.Order;
import org.bank.esEntity.AccountDetailES;
import org.bank.exception.BusinessException;
import org.bank.exception.code.BaseResponseCode;
import org.bank.mapper.AccountDetailMapper;
import org.bank.service.AccountDetailService;
import org.bank.service.AccountService;
import org.bank.vo.req.AccountDetailPageReqVO;
import org.bank.vo.resp.PageVO;
import org.checkerframework.checker.units.qual.A;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
public class AccountDetailServiceImpl implements AccountDetailService {
    private static final SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Autowired
    AccountDetailMapper accountDetailMapper;
    @Lazy
    @Autowired
    AccountService accountService;

    @Autowired
    AccountDetailESDao accountDetailESDao;


    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    @Override
    public AccountDetail addAccountDetail(AccountDetail vo, String userId) {
        Account accountInfo=accountService.detailInfoForAccountNo(vo.getAccountNo());
        if(accountInfo==null||StringUtils.isBlank(accountInfo.getUserId())){
            throw new BusinessException(BaseResponseCode.ORDER_ACCOUNT_CHECK_ERROR);
        }
        if(StringUtils.isBlank(userId)||!userId.equals(accountInfo.getUserId())){
            throw new BusinessException(BaseResponseCode.ORDER_ACCOUNT_CHECK_ERROR);
        }
        Date createtime=new Date();
        vo.setCreatetime(createtime);
        int insertSize=accountDetailMapper.insert(vo);
        if(insertSize==0){
            throw new BusinessException(BaseResponseCode.ACCOUNT_DETAIL_OPERATION_ERROR);
        }
        //插入es
        AccountDetailES accountDetailES=new AccountDetailES();
        BeanUtils.copyProperties(vo,accountDetailES);
        accountDetailES.setTimestamp(createtime.getTime());
        accountDetailES.setUserId(userId);
        accountDetailESDao.save(accountDetailES);
        return vo;
    }



    @Override
    public PageVO<AccountDetail> pageInfo(AccountDetailPageReqVO vo, String userId) {
        List<AccountDetail> accountDetailList = new ArrayList<>();
        PageVO<AccountDetail> result = new PageVO<>();
        Page<AccountDetail> page = PageHelper.startPage(vo.getPageNum(), vo.getPageSize());
        try {
            SearchRequest searchRequest = new SearchRequest("account_detail_bean");
            BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
            //模糊查询
            if (StringUtils.isNotBlank(vo.getAccountNo())) {
                boolQueryBuilder.filter(QueryBuilders.queryStringQuery("*" + vo.getAccountNo() + "*").field("accountNo"));
            }
            //精确查询
            if (StringUtils.isNotBlank(userId)) {
                boolQueryBuilder.filter(QueryBuilders.termQuery("userId", userId.trim()));
            }
            //精确查询
            if (StringUtils.isNotBlank(vo.getType())) {
                boolQueryBuilder.filter(QueryBuilders.termQuery("type", vo.getType()));
            }
            //全文搜索
            if (StringUtils.isNotBlank(vo.getKey())) {
                boolQueryBuilder.filter(QueryBuilders.multiMatchQuery(vo.getKey(), "title"));
            }
            //金额区间查询
            if(vo.getStartAmount()!=null){
                boolQueryBuilder.filter(QueryBuilders.rangeQuery("amount").gte(vo.getStartAmount()));
            }
            if(vo.getEndAmount()!=null){
                boolQueryBuilder.filter(QueryBuilders.rangeQuery("amount").lte(vo.getEndAmount()));
            }
            //日期区间查询
            if(vo.getCreatetimes()!=null&&vo.getCreatetimes().size()>0){
                boolQueryBuilder.filter(QueryBuilders.rangeQuery("timestamp").gte(simpleDateFormat.parse(vo.getCreatetimes().get(0)).getTime()));
                boolQueryBuilder.filter(QueryBuilders.rangeQuery("timestamp").lte(simpleDateFormat.parse(vo.getCreatetimes().get(1)).getTime()));
            }
            //排序
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.
                    sort(SortBuilders.fieldSort("timestamp").unmappedType("keyword").order(SortOrder.DESC)).query(boolQueryBuilder);
            searchRequest.source(searchSourceBuilder);
            SearchResponse response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            SearchHit[] hits = response.getHits().getHits();

            for (SearchHit hit : hits) {
                Map<String, Object> map = hit.getSourceAsMap();
                //日期类型需要重新设置 防止json格式化失败
                AccountDetailES accountDetailES = JSONObject.parseObject(JSONObject.toJSONString(map), AccountDetailES.class);
                //格式化日期
                AccountDetail accountDetail=new AccountDetail();
                BeanUtils.copyProperties(accountDetailES,accountDetail);
                accountDetail.setTransactionTime(simpleDateFormat.format(accountDetailES.getCreatetime()));
                accountDetailList.add(accountDetail);
            }
            result.setTotalRows((long) response.getHits().getTotalHits().value);
            result.setTotalPages(accountDetailList.size() % page.getPageSize() > 0 ? accountDetailList.size() % page.getPageSize() + 1 : accountDetailList.size() % page.getPageSize());
            result.setPageNum(page.getPageNum());
            result.setPageSize(page.getPageSize());
            result.setCurPageSize(page.getPageSize());
            result.setList(accountDetailList);
        } catch (IOException | ParseException e) {
            log.info("ES查询异常，{}", e);
            throw new BusinessException(BaseResponseCode.ES_OPTION_ERROR);
        }
        return result;
    }
}
