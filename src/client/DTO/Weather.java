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
public class Weather {
    private String description;
    private String iconUrl;
    private Double temp;
    private Double humidity;
    private Double windSpeed;

    public Weather(String description, String iconUrl, Double temp, Double humidity, Double windSpeed) {
        this.description = description;
        this.iconUrl =client.ClientController.URL_ICON_WEATHER_HEADER+iconUrl+client.ClientController.URL_ICON_WEATHER_TAIL;
        this.temp = temp;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
        
    }

    public String getDescription() {
        return description;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public Double getTemp() {
        return temp;
    }

    public Double getHumidity() {
        return humidity;
    }

    public Double getWindSpeed() {
        return windSpeed;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public void setTemp(Double temp) {
        this.temp = temp;
    }

    public void setHumidity(Double humidity) {
        this.humidity = humidity;
    }

    public void setWindSpeed(Double windSpeed) {
        this.windSpeed = windSpeed;
    }
    

    @Override
    public String toString() {
        return "Weather{" + "description=" + description + ", iconUrl=" + iconUrl + ", temp=" + temp + ", humidity=" + humidity + ", windSpeed=" + windSpeed + '}';
    }
    
    
    
    
    
}
