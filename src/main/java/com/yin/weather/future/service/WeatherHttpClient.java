package com.yin.weather.future.service;

import com.google.gson.Gson;
import com.yin.weather.future.dao.DictStationDao;
import com.yin.weather.future.entity.DictStationEntity;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * 请求天气数据
 *
 * @author yin.weilong
 * @date 2018.11.08
 */
@Service
public class WeatherHttpClient {

    @Autowired
    private DictStationDao dictStationDao;


    public String loadHtml(String url) {
        //查询任务
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet(url);
            CloseableHttpResponse response = client.execute(httpGet);
            HttpEntity httpEntity = response.getEntity();
            if (httpEntity != null) {
                return EntityUtils.toString(httpEntity, "GBK");
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Map loadJson2Map(String url) {
        //查询任务
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet(url);
            CloseableHttpResponse response = client.execute(httpGet);
            HttpEntity httpEntity = response.getEntity();
            if (httpEntity != null) {
                String html = EntityUtils.toString(httpEntity, "UTF-8");
                Gson gson = new Gson();
                Map<String, Object> map = gson.fromJson(html, Map.class);
                return map;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * https://blog.csdn.net/gaojinshan/article/details/27665741
     *
     * @return
     */
    public void loadHtmlStions() {
        String url = "https://blog.csdn.net/gaojinshan/article/details/27665741";
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet(url);
            CloseableHttpResponse response = client.execute(httpGet);
            HttpEntity httpEntity = response.getEntity();
            if (httpEntity != null) {
                String html = EntityUtils.toString(httpEntity, "UTF-8");
                Document doc = Jsoup.parse(html);
                Element articleContent = doc.getElementById("article_content");
                Elements htmledit_views = articleContent.getElementsByClass("htmledit_views");
                Element element = htmledit_views.first().child(1);
                for (Element d1 : element.children().stream().filter(a -> a.tagName().equals("li")).collect(Collectors.toList())) {
                    String p = d1.children().stream().filter(a -> a.tagName().equals("a")).collect(Collectors.toList()).get(0).text();
                    for (Element d2 : d1.children().stream().filter(a -> a.tagName().equals("ol")).collect(Collectors.toList()).get(0).children().stream().filter(a -> a.tagName().equals("li")).collect(Collectors.toList())) {
                        String c = d2.children().stream().filter(a -> a.tagName().equals("a")).collect(Collectors.toList()).get(0).text();
                        for (Element d3 : d2.children().stream().filter(a -> a.tagName().equals("ol")).collect(Collectors.toList()).get(0).children()) {
                            if (d3.tagName().equals("li")) {
                                Element e = d3.getElementsByTag("a").first();
                                String s = e.text();
                                if (!s.contains(":")) {
                                    continue;
                                }
                                //http://weather.com.cn/data/cityinfo/101030100.html
//                                String href = e.attr("href");
//                                String code = href.replace("http://weather.com.cn/data/cityinfo/", "").replace(".html", "");

                                DictStationEntity station = new DictStationEntity();
                                station.setCityName(c.split(":")[1]);
                                station.setCityCode(c.split(":")[0]);
                                station.setStationCode(s.split(":")[0]);
                                station.setStationName(s.split(":")[1]);
                                station.setProvinceCode(p.split(":")[0]);
                                station.setProvinceName(p.split(":")[1]);
                                dictStationDao.save(station);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
