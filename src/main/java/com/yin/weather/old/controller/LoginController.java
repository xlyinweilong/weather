package com.yin.weather.old.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户控制器
 *
 * @author yin
 */
@RestController
@RequestMapping(value = "old")
public class LoginController {

    @Autowired
    private com.yin.weather.old.service.OldScheduledTasks oldScheduledTasks;


    @GetMapping(value = "go")
    public String info() throws Exception {
        oldScheduledTasks.startOldTask();
        return "1";
    }


}
