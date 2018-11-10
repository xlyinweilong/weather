package com.yin.weather.monitor.controller;

import com.yin.weather.monitor.common.BaseMessage;
import com.yin.weather.monitor.dao.TaskDao;
import com.yin.weather.monitor.dao.TaskLogDao;
import com.yin.weather.monitor.dao.TaskLogDetailDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 任务控制器
 *
 * @author yin.weilong
 * @date 2018.11.09
 */
@RestController()
@RequestMapping(value = "/task", produces = "application/json")
public class TaskController {

    @Autowired
    private TaskDao taskDao;
    @Autowired
    private TaskLogDao taskLogDao;
    @Autowired
    private TaskLogDetailDao taskLogDetailDao;


    /**
     * 获取用户权限等信息
     *
     * @return
     */
    @GetMapping(value = "/task_list")
    public BaseMessage taskList(int pageIndex, int pageSize) {
        return BaseMessage.success(taskDao.findAll(PageRequest.of(pageIndex - 1, pageSize, Sort.Direction.ASC, "id")));
    }


    /**
     * 任务日志
     *
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @GetMapping(value = "/task_log_list")
    public BaseMessage taskLogList(int pageIndex, int pageSize) {
        return BaseMessage.success(taskLogDao.findAll(PageRequest.of(pageIndex - 1, pageSize, Sort.Direction.DESC, "id")));
    }

    /**
     * 任务日志详情
     *
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @GetMapping(value = "/task_log_detail_list")
    public BaseMessage taskLogDetailList(Long taskLogId,int pageIndex, int pageSize) {
        return BaseMessage.success(taskLogDetailDao.findByTaskLogId(taskLogId,PageRequest.of(pageIndex - 1, pageSize, Sort.Direction.DESC, "id")));
    }



}
