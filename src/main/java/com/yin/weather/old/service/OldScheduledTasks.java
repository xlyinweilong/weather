package com.yin.weather.old.service;

import com.yin.weather.future.dao.DictStationDao;
import com.yin.weather.future.entity.DictStationEntity;
import com.yin.weather.future.service.WeatherHttpClient;
import com.yin.weather.old.dao.DictOldStationDao;
import com.yin.weather.old.dao.OldWeatherDao;
import com.yin.weather.old.entity.DictOldStationEntity;
import com.yin.weather.old.entity.OldWeatherEntity;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 调度器
 *
 * @author yin.weilong
 * @date 2018.11.07
 */
@Component
public class OldScheduledTasks {

    private static final Logger log = LoggerFactory.getLogger(OldScheduledTasks.class);

    @Autowired
    private DictOldStationDao dictOldStationDao;
    @Autowired
    private OldWeatherDao oldWeatherDao;
    @Autowired
    private DictStationDao dictStationDao;
    @Autowired
    private WeatherHttpClient weatherHttpClient;


//    @Scheduled(cron = "0 5 23  * * * ")
    public void startOldTask() throws Exception {
        log.info("天气调度器开始触发");
        Date d1 = new Date();
        this.loadMonthPage();
        log.debug("天气调度器运行结束:" + (System.currentTimeMillis() - d1.getTime()));
    }

    /**
     * 加载station
     * @throws Exception
     */
    public void loadStation() throws Exception {
        String baseUrl = "http://www.tianqihoubao.com/lishi/index.htm";
        Document doc = Jsoup.parse(new URL(baseUrl).openStream(), "GBK", baseUrl);
        try {
            Element body = doc.body();
            Element citychk = body.getElementsByClass("citychk").first();
            for (Element dl : citychk.getElementsByTag("dl")) {
                String province = dl.getElementsByTag("dt").first().getElementsByTag("b").first().text();
                for (Element a : dl.getElementsByTag("dd").first().getElementsByTag("a")) {
                    String href = a.attr("href");
                    String city = a.text();
                    String station = a.text();
                    DictOldStationEntity dict = new DictOldStationEntity();
                    dict.setProvince(province);
                    dict.setCity(city);
                    dict.setStation(station);
                    dict.setUrl(href);
                    dictOldStationDao.save(dict);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 加载stationCode
     */
    public void loadStationCode() {
        Iterable<DictOldStationEntity> oldList = dictOldStationDao.findAll();
        Iterable<DictStationEntity> newList = dictStationDao.findAll();
        for (DictOldStationEntity old : oldList) {
            DictStationEntity station = null;
            DictStationEntity city = null;
            for (DictStationEntity s : newList) {
                if (s.getCityName().equals(old.getCity()) && s.getProvinceName().equals(old.getProvince()) && s.getStationName().equals(old.getStation())) {
                    station = s;
                }
                if (s.getCityName().equals(old.getCity()) && s.getProvinceName().equals(old.getProvince())) {
                    if (city == null) {
                        city = s;
                    }
                }
            }
            try {
                if (station == null) {
                    old.setStationCode(city.getStationCode());
                    dictOldStationDao.save(old);
                } else {
                    old.setStationCode(station.getStationCode());
                    dictOldStationDao.save(old);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 加载数据
     *
     * @throws Exception
     */
    public void loadMonthPage() throws Exception {
        dictOldStationDao.findAll().forEach(station -> {
            if (station.getStationCode() != null) {
                String baseUrl = "http://www.tianqihoubao.com/";
                try {
                    String url = baseUrl + getUrl(station.getUrl());
                    Document doc = Jsoup.parse(weatherHttpClient.loadHtml(url));
                    Element body = doc.body();
                    Element content = body.getElementById("content");
                    for (Element pcity : content.getElementsByClass("pcity")) {
                        for (Element a : pcity.getElementsByTag("a")) {
                            String href = a.attr("href");
                            String text = a.text();
                            System.out.println(text);
                            String yearAndMonth = text.split("月")[0];
                            String year = yearAndMonth.split("年")[0];
                            String month = yearAndMonth.split("年")[1];
                            this.loadWeather(year, month, baseUrl + getUrl(href), station.getStationCode());
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    //加载天气
    public void loadWeather(String year, String month, String url, String stationCode) {
        SimpleDateFormat aDate = new SimpleDateFormat("yyyy年MM月dd日");
        List<OldWeatherEntity> list = new ArrayList<>();
        try {
            Document doc = Jsoup.parse(weatherHttpClient.loadHtml(url));
            Element body = doc.body();
//            Element content = body.getElementById("content");
            int i = 0;
            for (Element tr : body.getElementsByTag("tr")) {
                if (i == 0) {
                    i++;
                    continue;
                }
                Elements tds = tr.getElementsByTag("td");
                String date = tds.get(0).getElementsByTag("a").first().text();
                String weather = tds.get(1).text();
                String temp = tds.get(2).text();
                String wind = tds.get(3).text();
                Date d = aDate.parse(date);
                BigDecimal h = new BigDecimal(temp.split("/")[0].trim().replace("℃", "").trim());
                BigDecimal l = new BigDecimal(temp.split("/")[1].trim().replace("℃", "").trim());
                OldWeatherEntity oldWeatherEntity = new OldWeatherEntity();
                oldWeatherEntity.setStationCode(stationCode);
                oldWeatherEntity.setTempHighest(h);
                oldWeatherEntity.setTempLowest(l);
                oldWeatherEntity.setWeather(weather);
                oldWeatherEntity.setWeatherDate(d);
                oldWeatherEntity.setWind(wind);
                list.add(oldWeatherEntity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        oldWeatherDao.saveAll(list);
    }

    private String getUrl(String url){
        if(url.startsWith("/")){
            return url.substring(1,url.length());
        }
        return url;
    }


}
