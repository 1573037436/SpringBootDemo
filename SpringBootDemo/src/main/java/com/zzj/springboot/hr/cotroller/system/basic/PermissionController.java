package com.zzj.springboot.hr.cotroller.system.basic;

import com.zzj.springboot.hr.model.RespBean;
import com.zzj.springboot.model.SysResources;
import com.zzj.springboot.model.SysRole;
import com.zzj.springboot.service.ResourcesService;
import com.zzj.springboot.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/system/basic/permission")
public class PermissionController {
    @Autowired
    RoleService roleService;
    @Autowired
    ResourcesService menuService;
    @GetMapping("/menus")
    public List<SysResources> getAllMenus() {
        return menuService.listAll();
    }

    @PutMapping("/")
    public RespBean updateMenuRole(Integer rid, Integer[] mids) {
        return RespBean.error("更新失败!");
    }

    @PostMapping("/role")
    public RespBean addRole(@RequestBody SysRole role) {
        if (roleService.add(role) == 1) {
            return RespBean.ok("添加成功!");
        }
        return RespBean.error("添加失败!");
    }

    @DeleteMapping("/role/{rid}")
    public RespBean deleteRoleById(@PathVariable Integer rid) {
        if (roleService.deleteRoleById(rid) == 1) {
            return RespBean.ok("删除成功!");
        }
        return RespBean.error("删除失败!");
    }
}
