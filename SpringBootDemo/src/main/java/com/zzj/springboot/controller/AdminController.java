package com.zzj.springboot.controller;

import com.zzj.springboot.model.SysResources;
import com.zzj.springboot.service.ResourcesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 * Created by zzj on 2020/6/9.
 */
@RestController
public class AdminController {
    @Autowired
    ResourcesService resourcesService;
    @RequestMapping("/api/menu")
    @ResponseBody
    public List<SysResources> getResources(){
        return resourcesService.handleMenus(resourcesService.listAll());
    }
}
