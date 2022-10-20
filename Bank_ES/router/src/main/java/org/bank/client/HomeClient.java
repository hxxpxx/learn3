package org.bank.client;

import org.bank.utils.DataResult;
import org.bank.vo.resp.HomeRespVO;
import org.bank.vo.resp.HomeVueRespVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

@FeignClient(name = "orderSystemService",contextId = "homeServer")
public interface HomeClient {
    @GetMapping("/home")
    public DataResult<HomeRespVO> getHomeInfo();
    @RequestMapping(value = "/home/getSujbectInfo", method = RequestMethod.GET)
    public DataResult<Map<String, Object>> getSujbectInfo() ;
    @RequestMapping(value = "/homeForVue", method = RequestMethod.GET)
    public DataResult<HomeVueRespVO> getHomeForVueInfo();
}
