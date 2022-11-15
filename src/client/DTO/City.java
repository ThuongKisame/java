/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.DTO;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author TTC
 */
public class City {

    private String name;
    private String data;
    private String flag;
    private String urlFlag;
    private Double longtitude;
    private Double latitude;
    
    public Country country=null;
    public Weather weather=null;
    public List<Hotel> hotels=new ArrayList<>();

    public City(String name, String data, String flag, Double longtitude, Double latitude) {
        this.name = name;
        this.data = data;
        this.flag=flag;
        this.urlFlag = client.ClientController.URL_IMG_FLAG+flag+".png";
        this.longtitude = longtitude;
        this.latitude = latitude;
    }

    public String getData() {
        return data;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
    

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongtitude() {
        return longtitude;
    }

    public String getName() {
        return name;
    }

    public String getUrlFlag() {
        return urlFlag;
    }

    public void setUrlFlag(String urlFlag) {
        this.urlFlag = urlFlag;
    }

    @Override
    public String toString() {
        return "City{" + "name=" + name + ", data=" + data + ", urlFlag=" + urlFlag + ", longtitude=" + longtitude + ", latitude=" + latitude + '}';
    }

    
    
    
}
