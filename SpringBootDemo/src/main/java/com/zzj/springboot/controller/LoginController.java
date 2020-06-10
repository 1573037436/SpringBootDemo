package com.zzj.springboot.controller;
import com.zzj.springboot.model.SysUser;
import com.zzj.springboot.service.UserService;
import com.zzj.springboot.model.Result;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    UserService userService;

    @CrossOrigin
    @PostMapping(value = "/api/login")
    @ResponseBody
    public Result login(@RequestBody SysUser requestUser, HttpSession session) {
        //登录认证
        UsernamePasswordToken token=new UsernamePasswordToken(requestUser.getUsername(),requestUser.getPassword());
        Subject subject= SecurityUtils.getSubject();
        try{
            // 开始认证，这一步会跳到我们自定义的 Realm 中
            //访问 doGetAuthenticationInfo 方法，开始身份认证
            subject.login(token);
            //为了保存登录状态，我们可以把用户信息存在 Session 对象中
            //当用户在应用程序的 Web 页之间跳转时，存储在 Session 对象中的变量不会丢失
            SysUser user = userService.findUser(requestUser);
            session.setAttribute("user", user);
            return new Result(200);
        }catch (Exception e){
            session.setAttribute("user", requestUser);
            session.setAttribute("error", "用户名或密码错误！");
            return new Result(400);
        }
    }

    /**
     * 测试权限认证
     */
    @CrossOrigin
    @RequestMapping(value = "/api/admin")
    public void admin(){
        System.out.println("=========/api/admin===========");
    }
    @CrossOrigin
    @RequestMapping(value = "/api/library")
    public void library(){
        System.out.println("===========/api/library=============");
    }
    @CrossOrigin
    @RequestMapping(value = "/api/user")
    public void user(){
        System.out.println("=============/api/user=============");
    }
}

