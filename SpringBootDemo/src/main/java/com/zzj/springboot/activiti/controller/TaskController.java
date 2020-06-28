package com.zzj.springboot.activiti.controller;

import cn.hutool.json.JSONObject;
import com.zzj.springboot.activiti.service.ActTaskService;
import com.zzj.springboot.activiti.util.R;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api/task")
public class TaskController {

    private final ActTaskService actTaskService;

    @GetMapping("/todoPage")
    public R todoPage(@RequestParam Map<String, Object> params) {
        return new R<>(actTaskService.getTaskByName(params,
                null //SecurityUtils.getUsername() 先不考虑代办任务的处理人
        ));
    }

    @PostMapping("/handleTask")
    public R handleTask(@RequestBody JSONObject paramJson) {
        return new R(actTaskService.handleTask(paramJson));
    }

}
