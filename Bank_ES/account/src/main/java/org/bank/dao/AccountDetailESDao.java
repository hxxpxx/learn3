package org.bank.dao;

import org.bank.esEntity.AccountDetailES;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface AccountDetailESDao  extends ElasticsearchRepository<AccountDetailES,Integer> {

}
