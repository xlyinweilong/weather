package com.yin.weather.future.service;

import com.yin.weather.future.dao.DictCityDao;
import com.yin.weather.future.dao.DictStationDao;
import com.yin.weather.future.entity.DictStationEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 地区服务
 *
 * @author yin.weilong
 * @date 2018.11.07
 */

@Service
public class DictStationService {

    @Autowired
    private DictCityDao dictCityDao;
    @Autowired
    private DictStationDao dictStationDao;
    @Autowired
    private WeatherHttpClient weatherHttpClient;

    public void loadStationList() {
        dictCityDao.findAll().forEach(city -> {
            String url = "http://www.weather.com.cn/data/city3jdata/station/" + city.getProvinceCode() + city.getCode() + ".html";
            Map<String, String> map = weatherHttpClient.loadJson2Map(url);
            for (String code : map.keySet()) {
                DictStationEntity station = new DictStationEntity();
                String stationCode = null;
                if (city.getCode().equals("00")) {
                    stationCode = city.getProvinceCode() + code + city.getCode();
                }else{
                    stationCode = city.getProvinceCode() + city.getCode()+ code;
                }
                station.setStationCode(stationCode);
                station.setStationName(map.get(code));
                station.setCityCode(city.getCode());
                station.setCityName(city.getName());
                station.setProvinceCode(city.getProvinceCode());
                station.setProvinceName(city.getProvinceName());
                dictStationDao.save(station);
            }
        });
    }
}
