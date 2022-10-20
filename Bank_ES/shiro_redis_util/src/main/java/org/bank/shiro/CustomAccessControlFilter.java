package org.bank.shiro;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.bank.constants.Constant;
import org.bank.exception.BusinessException;
import org.bank.exception.code.BaseResponseCode;
import org.bank.utils.DataResult;
import org.bank.utils.HttpContextUtils;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @BelongsProject: BankCloud
 * @BelongsPackage: org.bank.config
 * @Author: lizongle
 * @Description:
 * @Version: 1.0
 */
@Slf4j
public class CustomAccessControlFilter extends AccessControlFilter {
    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) {
        return false;
    }
    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws IOException, ServletException {
        HttpServletRequest request= (HttpServletRequest) servletRequest;
        try {
            Subject subject=getSubject(servletRequest,servletResponse);
            log.info(subject.isAuthenticated()+"");
            log.info(HttpContextUtils.isAjaxRequest(request)+"");
            log.info(request.getMethod());
            log.info(request.getRequestURL().toString());
            String token=request.getHeader(Constant.ACCESS_TOKEN);
            if(StringUtils.isEmpty(token)){
                throw new BusinessException(BaseResponseCode.TOKEN_ERROR);
            }
            CustomPasswordToken customPasswordToken=new CustomPasswordToken(token);
            getSubject(servletRequest, servletResponse).login(customPasswordToken);
        }catch (BusinessException exception){
            if(HttpContextUtils.isAjaxRequest(request)){
                customRsponse(exception.getMessageCode(),exception.getDetailMessage(),servletResponse);
            }else if(exception.getMessageCode()==BaseResponseCode.TOKEN_ERROR.getCode()){
                servletRequest.getRequestDispatcher("/index/login").forward(servletRequest,servletResponse);
            }else if(exception.getMessageCode()==BaseResponseCode.UNAUTHORIZED_ERROR.getCode()){
                servletRequest.getRequestDispatcher("/index/403").forward(servletRequest,servletResponse);
            }else {
                servletRequest.getRequestDispatcher("/index/500").forward(servletRequest,servletResponse);
            }
            return false;
        } catch (AuthenticationException e){
            if(HttpContextUtils.isAjaxRequest(request)){
                if(e.getCause() instanceof BusinessException){
                    BusinessException exception= (BusinessException) e.getCause();
                    customRsponse(exception.getMessageCode(),exception.getDetailMessage(),servletResponse);
                }else {
                    customRsponse(BaseResponseCode.SYSTEM_BUSY.getCode(),BaseResponseCode.SYSTEM_BUSY.getMsg(),servletResponse);
                }
            }else {
                servletRequest.getRequestDispatcher("/index/403").forward(servletRequest,servletResponse);
            }
            return false;
        }catch (Exception e) {
            if(HttpContextUtils.isAjaxRequest(request)){
                if(e.getCause() instanceof BusinessException){
                    BusinessException exception= (BusinessException) e.getCause();
                    customRsponse(exception.getMessageCode(),exception.getDetailMessage(),servletResponse);
                }else {
                    customRsponse(BaseResponseCode.SYSTEM_BUSY.getCode(),BaseResponseCode.SYSTEM_BUSY.getMsg(),servletResponse);
                }
            }else {
                servletRequest.getRequestDispatcher("/index/500").forward(servletRequest,servletResponse);
            }
            return false;
        }
        return true;
    }


    /**
     * 对跨域提供支持
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
        httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true"); //带上cookie信息
//        // 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }
        return super.preHandle(request, response);
    }

    private void customRsponse(int code,String msg,ServletResponse response){
        try {
            DataResult result = DataResult.getResult(code,msg);

            response.setContentType("application/json; charset=utf-8");
            response.setCharacterEncoding("UTF-8");

            String userJson = JSON.toJSONString(result);
            OutputStream out = response.getOutputStream();
            out.write(userJson.getBytes("UTF-8"));
            out.flush();
        } catch (IOException e) {
            log.error("eror={}",e);
        }
    }

}
