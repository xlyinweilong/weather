package com.yin.weather.old.service;

import com.yin.weather.future.dao.DictStationDao;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 补充没有录入的信息
 *
 * @author yin.weilong
 * @date 2018.11.07
 */
@Component
public class SupplementTasks {

    private static final Logger log = LoggerFactory.getLogger(SupplementTasks.class);

    @Autowired
    private DictOldStationDao dictOldStationDao;
    @Autowired
    private OldWeatherDao oldWeatherDao;
    @Autowired
    private DictStationDao dictStationDao;
    @Autowired
    private WeatherHttpClient weatherHttpClient;

    public Date getStartDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    public Date getEndDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        return calendar.getTime();
    }


    /**
     * 加载station
     *
     * @throws Exception
     */
    public void findAll() throws Exception {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatterMonth = new SimpleDateFormat("yyyyMM");
        Calendar calendar = Calendar.getInstance();
        Date endDate = formatter.parse("2018-11-27");
        for (DictOldStationEntity dictOldStation : dictOldStationDao.findAll()) {
            if (dictOldStation.getStationCode() == null) {
                continue;
            }
            calendar.set(2011, 0, 1, 0, 0, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            while (calendar.getTime().before(endDate)) {
                if (oldWeatherDao.findByStationCodeAndWeatherDate(dictOldStation.getStationCode(), calendar.getTime()) == null) {
                    System.out.println(dictOldStation.getStation() + ",缺少数据:" + formatter.format(calendar.getTime()));
//                    //如果缺少数据
//                    int i = oldWeatherDao.delete4StationCodeAndWeatherDate(dictOldStation.getStationCode(), this.getStartDate(calendar.getTime()), this.getEndDate(calendar.getTime()));
//                    System.out.println("删除了" + i + "条数据");
                    //获取数据
                    String url = "http://www.tianqihoubao.com" + dictOldStation.getUrl().split("\\.")[0] + "/month/" + formatterMonth.format(calendar.getTime()) + ".html";
                    this.loadWeather(url, dictOldStation.getStationCode());
                    //日期调整到下个月1号
                    calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);
                    calendar.set(Calendar.DAY_OF_MONTH, 1);
                } else {
                    calendar.add(Calendar.DAY_OF_YEAR, 1);
                }
            }
        }
    }

    //加载天气
    public void loadWeather(String url, String stationCode) {
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
                BigDecimal h = null;
                try {
                    h = new BigDecimal(temp.split("/")[0].trim().replace("℃", "").trim());
                } catch (Exception e) {
                    h = null;
                }
                BigDecimal l = null;
                try {
                    l = new BigDecimal(temp.split("/")[1].trim().replace("℃", "").trim());
                } catch (Exception e) {
                    l = null;
                }
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

    private String getUrl(String url) {
        if (url.startsWith("/")) {
            return url.substring(1, url.length());
        }
        return url;
    }


}
