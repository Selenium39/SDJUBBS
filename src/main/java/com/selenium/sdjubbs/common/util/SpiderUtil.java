package com.selenium.sdjubbs.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.util.UriBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class SpiderUtil {
    public static final String SDJU_INDEX_URL = "https://www.sdju.edu.cn/";

    public static String getIndexNews() {
        CloseableHttpResponse response = null;
        String html = "";
        //创建HttpClient对象
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(SDJU_INDEX_URL);
        try {
            response = client.execute(httpGet);
            html = EntityUtils.toString(response.getEntity(), "utf-8");
            //log.info("html: " + html);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Document document = Jsoup.parse(html);
        Element element = document.getElementById("wp_news_w6");
        //log.info("element: " + element.toString());
        Element scriptElement = element.selectFirst("script");
        //log.info(scriptElement.data());
        String scriptElementString = scriptElement.data();
        String jsonString = scriptElementString.substring(scriptElementString.indexOf('['), scriptElementString.indexOf(']') + 1);
        //log.info(jsonArray.get(0).toString());
        return jsonString;
    }
}
