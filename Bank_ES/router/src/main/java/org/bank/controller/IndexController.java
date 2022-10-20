package org.bank.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;


/**
 * @BelongsProject: BankCloud
 * @BelongsPackage: org.bank.controller
 * @Author: lizongle
 * @Description:
 * @Version: 1.0
 */
@Controller
@RequestMapping("/")
public class IndexController {
    @GetMapping()
    public String index(){
        return "login";
    }

    @GetMapping("/index/login")
    public String login(){
        return "login";
    }

    /**
     * 进入首页
     * @return
     */
    @GetMapping("/index/home")
    public String home(Model model, HttpServletRequest request){
        return "home";
    }

    /**
     * 更改密码页面
     * @return
     */
    @GetMapping("/index/users/password")
    public String updatePassword(){
        return "users/update_password";
    }

    /**
     * 用户编辑个人信息 视图
     * @return       java.lang.String
     * @throws
     */
    @GetMapping("/index/users/info")
    public String userDetail(Model model){
        model.addAttribute("flagType","edit");
        return "users/user_edit";
    }

    /**
     * 菜单权限列表  视图
     * @return       java.lang.String
     * @throws
     */
    @GetMapping("/index/menus")
    public String menusList(){
        return "menus/menu_list";
    }

    /**
     * 角色列表 操作视图
     * @return       java.lang.String
     * @throws
     */
    @GetMapping("/index/roles")
    public String roleList(){
        return "roles/role_list";
    }

    /**
     * 用户列表操作 视图
     * @return       java.lang.String
     * @throws
     */
    @GetMapping("/index/users")
    public String userList(){
        return "users/user_list";
    }

    /**
     * 系统操作日志 视图
     * @return       java.lang.String
     * @throws
     */
    @GetMapping("/index/logs")
    public String logList(){
        return "logs/log_list";
    }

    /**
     * 组织机构列表 试图
     * @return       java.lang.String
     * @throws
     */
    @GetMapping("/index/depts")
    public String deptList(){
        return "depts/dept_list";
    }

    @GetMapping("/index/403")
    public String error403(){
        return "error/403";
    }
    @GetMapping("/index/404")
    public String error404(){
        return "error/404";
    }

    @GetMapping("/index/500")
    public String error405(){
        return "error/500";
    }

    @GetMapping("/index/main")
    public String indexHome(){
        return "main";
    }

    @GetMapping("/index/about")
    public String about(){
        return "about";
    }
}
