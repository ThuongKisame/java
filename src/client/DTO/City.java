/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.DTO;

/**
 *
 * @author TTC
 */
public class City {

    private String name;
    private String data;
    private String urlFlag;
    private Double longtitude;
    private Double latitude;

    public City(String name, String data, String urlFlag, Double longtitude, Double latitude) {
        this.name = name;
        this.data = data;
        this.urlFlag = client.ClientController.URL_IMG_FLAG+urlFlag+".png";
        this.longtitude = longtitude;
        this.latitude = latitude;
    }

    public String getData() {
        return data;
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
