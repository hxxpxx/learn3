package org.bank.service;

import org.bank.entity.OrderSnapshot;
import org.bank.vo.req.OrderSnapshotAddReqVO;

public interface OrderSnapshotService {
    OrderSnapshot addOrderSnapshot(OrderSnapshot vo);

    OrderSnapshot detailInfo(String orderNo);

    void deleteOrderSnapshot(String orderNo);
}
