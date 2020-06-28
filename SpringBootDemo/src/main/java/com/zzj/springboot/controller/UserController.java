package com.zzj.springboot.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zzj.springboot.model.SysUser;
import com.zzj.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by zzj on 2020/6/17.
 */
@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    UserService userService;
    @ResponseBody
    @GetMapping("/list")
    public IPage<SysUser> list(@RequestParam Map<String, String> params){
        //设置当前页和每页的数据量
        Page page=new Page(Long.valueOf(params.get("current")),Long.valueOf(params.get("size")));
        return userService.selectPage(page);
    }
    @PostMapping(value = "/addOrUpdateUser")
    public String createModel(@RequestBody @Valid SysUser user) {
        if(user.getId()==null){
            userService.addUser(user);
        }else{
            userService.updateUser(user);
        }
        return "200";
    }
    @DeleteMapping(value = "/deleteById/{id}")
    public String deleteUser(@PathVariable("id") long id) {
        userService.deleteById(id);
        return "200";
    }

    @PostMapping(value = "/deleteBatchIds") //{"ids":"1,2,4,5,6,7,11,12,13,14,15"}
    public String deleteBatchUser(@RequestBody Map<String,String> ids) {
        //userService.deleteBatchMapIds(ids);
        String temp=ids.get("ids");
        String[] temps=temp.split(",");
        List idList= Arrays.asList(temps);
        userService.deleteBatchIds(idList);
        return "200";
    }
}
