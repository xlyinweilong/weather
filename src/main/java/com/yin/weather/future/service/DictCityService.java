package com.yin.weather.future.service;

import com.yin.weather.future.dao.DictCityDao;
import com.yin.weather.future.dao.DictProvinceDao;
import com.yin.weather.future.entity.DictCityEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 城市服务
 *
 * @author yin.weilong
 * @date 2018.11.07
 */

@Service
public class DictCityService {

    @Autowired
    private DictCityDao dictCityDao;
    @Autowired
    private DictProvinceDao dictProvinceDao;
    @Autowired
    private WeatherHttpClient weatherHttpClient;

    public void loadCityList() {
        dictProvinceDao.findAll().forEach(province -> {
            String url = "http://www.weather.com.cn/data/city3jdata/provshi/" + province.getCode() + ".html";
            Map<String, String> map = weatherHttpClient.loadJson2Map(url);
            for (String code : map.keySet()) {
                DictCityEntity city = new DictCityEntity();
                city.setCode(code);
                city.setName(map.get(code));
                city.setProvinceCode(province.getCode());
                city.setProvinceName(province.getName());
                dictCityDao.save(city);
            }
        });
    }
}
