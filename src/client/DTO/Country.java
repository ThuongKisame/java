/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.DTO;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author TTC
 */
public class Country {

    //đánh index
    private String name;
    private String url;
    private String urlMap;
    private String data;
    private String currencies;
    private String languages;
    private String population;
    private String latitude;
    private String longtitude;

    public String recommend ;
    
    public List<Recommend> rcmd=new ArrayList<>();
    public List<Hotel> hotels=new ArrayList<>();

    public Country(String name, String url, String urlMap, String data, String currencies, String languages, String population, String latlng, String recommend) {
        this.name = name;
        this.url = url;
        this.urlMap = urlMap;
        this.data = data;
        this.currencies = currencies;
        this.languages = languages;
        this.population = population;
        System.out.println(latlng);
        float lati = Float.parseFloat(latlng.split(",")[0].substring(1, latlng.split(",")[0].length()));
        String l=latlng.split(",")[1].substring(0, latlng.split(",")[1].length()-1);
        float longti = Float.parseFloat(l);
        if (lati > 0) {
            this.latitude = Math.abs(lati) + "' N";
        } else {
            this.latitude = Math.abs(lati) + "' S";
        }
        if (longti > 0) {
            this.longtitude = Math.abs(longti) + "' E";
        } else {
            this.longtitude = Math.abs(longti) + "' U";
        }
        this.recommend=recommend;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getLongtitude() {
        return longtitude;
    }

    public String getLatitude() {
        return latitude;
    }


    public String getPopulation() {
        return population;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setLongtitude(String longtitude) {
        this.longtitude = longtitude;
    }

   

    public void setPopulation(String population) {
        this.population = population;
    }

    public String getUrlMap() {
        return urlMap;
    }

    public String getData() {
        return data;
    }

    public String getCurrencies() {
        return currencies;
    }

    public String getLanguages() {
        return languages;
    }

    public String getRecommend() {
        return recommend;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUrlMap(String urlMap) {
        this.urlMap = urlMap;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setCurrencies(String currencies) {
        this.currencies = currencies;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }

    public void setRecommend(String recommend) {
        this.recommend = recommend;
    }

    @Override
    public String toString() {
        return "Country{" + "name=" + name + ", url=" + url + ", urlMap=" + urlMap + ", currencies=" + currencies + ", languages=" + languages + ", population=" + population + ", latitude=" + latitude + ", longtitude=" + longtitude + ", recommend=" + recommend + '}';
    }



}
