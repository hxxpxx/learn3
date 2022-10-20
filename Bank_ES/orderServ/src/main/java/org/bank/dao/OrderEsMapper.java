package org.bank.dao;

import org.bank.esEntity.OrderES;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface OrderEsMapper  extends ElasticsearchRepository<OrderES,String> {

}
