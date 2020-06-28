package com.zzj.springboot.hr.cotroller.config;

import com.zzj.springboot.model.SysResources;
import com.zzj.springboot.service.ResourcesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/system/config")
public class SystemConfigController {
    @Autowired
    ResourcesService menuService;
    @GetMapping("/menu")
    public List<SysResources> getMenus() {
        return menuService.listAll();
    }
}