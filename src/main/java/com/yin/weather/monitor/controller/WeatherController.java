package com.yin.weather.monitor.controller;

import com.yin.weather.future.dao.WeatherLogDao;
import com.yin.weather.future.dao.WeatherLogErrorDao;
import com.yin.weather.monitor.common.BaseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 天气器
 *
 * @author yin.weilong
 * @date 2018.11.09
 */
@RestController()
@RequestMapping(value = "/weather", produces = "application/json")
public class WeatherController {

    @Autowired
    private WeatherLogDao weatherLogDao;
    @Autowired
    private WeatherLogErrorDao weatherLogErrorDao;


    /**
     * 天气日志
     *
     * @return
     */
    @GetMapping(value = "/log_list")
    public BaseMessage logList(int pageIndex, int pageSize) {
        return BaseMessage.success(weatherLogDao.findAll(PageRequest.of(pageIndex - 1, pageSize, Sort.Direction.DESC, "id")));
    }



    /**
     * 天气错误
     *
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @GetMapping(value = "/log_error_list")
    public BaseMessage logErrorList(Long taskLogId,int pageIndex, int pageSize) {
        return BaseMessage.success(weatherLogErrorDao.findByLogId(taskLogId,PageRequest.of(pageIndex - 1, pageSize, Sort.Direction.DESC, "id")));
    }



}
