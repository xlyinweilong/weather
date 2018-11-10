package com.yin.weather.future.service;

import com.yin.weather.future.dao.DictProvinceDao;
import com.yin.weather.future.entity.DictProvinceEntity;
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
public class DictProvinceService {

    @Autowired
    private DictProvinceDao dictProvinceDao;
    @Autowired
    private WeatherHttpClient weatherHttpClient;

    public void loadProvinceList() throws Exception {
        String url = "http://www.weather.com.cn/data/city3jdata/china.html";
        Map<String, String> map = weatherHttpClient.loadJson2Map(url);
        for (String code : map.keySet()) {
            DictProvinceEntity province = new DictProvinceEntity();
            province.setCode(code);
            province.setName(map.get(code));
            dictProvinceDao.save(province);
        }
    }
}
