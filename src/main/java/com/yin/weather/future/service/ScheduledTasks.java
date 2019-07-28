package com.yin.weather.future.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 调度器
 *
 * @author yin.weilong
 * @date 2018.11.07
 */
@Component
public class ScheduledTasks {

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    @Autowired
    private WeatherService weatherService;
    @Autowired
    private DictStationService dictStationService;
    @Autowired
    private WeatherHttpClient weatherHttpClient;

    @Scheduled(cron = "0 0 11  * * * ")
    public void startTask() throws Exception {
        log.info("天气调度器开始触发");
        Date d1 = new Date();
        weatherService.loadWeatherList();
        log.info("天气调度器运行结束:" + (System.currentTimeMillis() - d1.getTime()));
    }



}
