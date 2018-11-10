package com.yin.weather.future.service;

import com.yin.weather.future.dao.DictStationDao;
import com.yin.weather.future.dao.WeatherDao;
import com.yin.weather.future.dao.WeatherLogDao;
import com.yin.weather.future.dao.WeatherLogErrorDao;
import com.yin.weather.future.entity.DictStationEntity;
import com.yin.weather.future.entity.WeatherEntity;
import com.yin.weather.future.entity.WeatherLogEntity;
import com.yin.weather.future.entity.WeatherLogErrorEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * 城市服务
 *
 * @author yin.weilong
 * @date 2018.11.07
 */

@Service
public class WeatherService {

    @Autowired
    private DictStationDao dictStationDao;
    @Autowired
    private WeatherDao weatherDao;
    @Autowired
    private WeatherHttpClient weatherHttpClient;
    @Autowired
    private WeatherLogDao weatherLogDao;
    @Autowired
    private WeatherLogErrorDao weatherLogErrorDao;

    public void loadWeatherList() {
        WeatherLogEntity log = new WeatherLogEntity();
        log.setStationCount(dictStationDao.count());
        weatherLogDao.save(log);
        int successCount = 0;
        int errorCount = 0;
        for (DictStationEntity station : dictStationDao.findAll()) {
            String url = "http://www.weather.com.cn/data/cityinfo/" + station.getStationCode() + ".html";
            try {
                Map<String, Object> map = weatherHttpClient.loadJson2Map(url);
                Map subMap = (Map) map.get("weatherinfo");
                WeatherEntity weather = new WeatherEntity();
                weather.setStationCode(station.getStationCode());
                weather.setTempHighest(new BigDecimal(subMap.get("temp1").toString().replace("℃", "")));
                weather.setTempLowest(new BigDecimal(subMap.get("temp2").toString().replace("℃", "")));
                weather.setWeather(subMap.get("weather").toString());
                weatherDao.save(weather);
                successCount++;
            } catch (Exception e) {
                errorCount++;
                WeatherLogErrorEntity logError = new WeatherLogErrorEntity();
                logError.setLogId(log.getId());
                logError.setWeatherDate(log.getWeatherDate());
                logError.setStationCode(station.getStationCode());
                weatherLogErrorDao.save(logError);
            }
        }
        log.setSuccessCount(successCount);
        log.setErrorCount(errorCount);
        log.setEndTime(new Date());
        weatherLogDao.save(log);
    }
}
