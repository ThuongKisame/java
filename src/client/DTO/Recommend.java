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
public class Recommend {
    private String name;
    private String url;
    private String data;
    
    public String recommendItem="";

    public Recommend(String name, String url, String data) {
        this.name = name;
        this.url = url;
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getData() {
        return data;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Recommend{" + "name=" + name + ", url=" + url + ", data=" + data + '}';
    }
    
}
