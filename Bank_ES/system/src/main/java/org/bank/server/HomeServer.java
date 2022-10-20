package org.bank.server;

import io.jsonwebtoken.Claims;
import org.bank.constants.Constant;
import org.bank.service.HomeService;
import org.bank.service.PermissionService;
import org.bank.service.RoleService;
import org.bank.utils.DataResult;
import org.bank.utils.JwtTokenUtil;
import org.bank.vo.resp.HomeRespVO;
import org.bank.vo.resp.HomeVueRespVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @BelongsProject: BankCloud
 * @BelongsPackage: org.bank.controller
 * @Author: lizongle
 * @Description:
 * @Version: 1.0
 */
@RestController("homeServer")
public class HomeServer {
    @Autowired
    private HomeService homeService;

    @Autowired
    private PermissionService permissionService;
    @Autowired
    private RoleService roleService;

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public DataResult<HomeRespVO> getHomeInfo(HttpServletRequest request) {
        String accessToken = request.getHeader("authorization");
        /**
         * 通过access_token拿userId
         */
        String userId = JwtTokenUtil.getUserId(accessToken);
        DataResult<HomeRespVO> result = DataResult.success();
        result.setData(homeService.getHomeInfo(userId));
        return result;
    }

    @RequestMapping(value = "/home/getSujbectInfo", method = RequestMethod.GET)
    public DataResult<Map<String, Object>> getSujbectInfo(HttpServletRequest request) {
        String accessToken = request.getHeader("authorization");
        /**
         * 通过access_token拿userId
         */
        String userId = JwtTokenUtil.getUserId(accessToken);
        List<String> roleNames = roleService.getRoleNames(userId);
        Map<String, Object> resultData = new HashMap<String, Object>();
        if (roleNames != null && !roleNames.isEmpty()) {
            resultData.put("roleNames", roleService.getRoleNames(userId));
        }
        resultData.put("permissions", permissionService.getPermissionsByUserId(userId));
        DataResult<Map<String, Object>> result = DataResult.success();
        result.setData(resultData);
        return result;
    }

    @RequestMapping(value = "/homeForVue", method = RequestMethod.GET)
    public DataResult<HomeVueRespVO> getHomeForVueInfo(HttpServletRequest request){
        String accessToken=request.getHeader("authorization");
        /**
         * 通过access_token拿userId
         */
        String userId= JwtTokenUtil.getUserId(accessToken);
        DataResult<HomeVueRespVO> result=DataResult.success();
        result.setData(homeService.homeForVue(userId));
        return result;
    }
}
