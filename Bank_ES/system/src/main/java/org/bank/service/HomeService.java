package org.bank.service;


import org.bank.vo.resp.HomeRespVO;
import org.bank.vo.resp.HomeVueRespVO;


public interface HomeService {

    HomeRespVO getHomeInfo(String userId);


    HomeVueRespVO homeForVue(String userId);
}
