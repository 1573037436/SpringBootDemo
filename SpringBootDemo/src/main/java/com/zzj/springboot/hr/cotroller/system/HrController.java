package com.zzj.springboot.hr.cotroller.system;

import com.zzj.springboot.hr.model.RespBean;
import com.zzj.springboot.model.SysUser;
import com.zzj.springboot.service.RoleService;
import com.zzj.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/system/hr")
public class HrController {
    @Autowired
    RoleService roleService;
    @Autowired
    UserService userService;

    @PutMapping("/")
    public RespBean updateHr(@RequestBody SysUser hr) {
        if (userService.updateUser(hr) == 1) {
            return RespBean.ok("更新成功!");
        }
        return RespBean.error("更新失败!");
    }

    @PutMapping("/role")
    public RespBean updateHrRole(Integer hrid, Integer[] rids) {
        if (userService.updateUserRole(hrid, rids)) {
            return RespBean.ok("更新成功!");
        }
        return RespBean.error("更新失败!");
    }

    @DeleteMapping("/{id}")
    public RespBean deleteHrById(@PathVariable Integer id) {
        if (userService.deleteById(id) == 1) {
            return RespBean.ok("删除成功!");
        }
        return RespBean.error("删除失败!");
    }
}
