package org.bank.controller;

import org.bank.client.HomeClient;
import org.bank.utils.DataResult;
import org.bank.vo.resp.HomeRespVO;
import org.bank.vo.resp.HomeVueRespVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @BelongsProject: BankCloud
 * @BelongsPackage: org.bank.controller
 * @Author: lizongle
 * @Description:
 * @Version: 1.0
 */
@RestController
@RequestMapping("/sys")
public class HomeController {

    @Autowired
    private HomeClient homeClient;
    @GetMapping("/home")
    public DataResult<HomeRespVO> getHomeInfo(HttpServletRequest request){
        return homeClient.getHomeInfo();
    }

    @GetMapping("/homeForVue")
    public DataResult<HomeVueRespVO> getHomeForVueInfo(){
        return homeClient.getHomeForVueInfo();
    }
}
