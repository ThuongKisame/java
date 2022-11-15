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
public class Hotel {
    private String rated;
    private String urlImage;
    private String price;
    private String localtion;
    private String name;

    public Hotel(String rated, String urlImage, String price, String localtion, String name) {
        this.rated = rated;
        this.urlImage = urlImage;
        this.price = price;
        this.localtion = localtion;
        this.name = name;
    }

    public String getRated() {
        return rated;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public String getPrice() {
        return price;
    }

    public String getLocaltion() {
        return localtion;
    }

    public String getName() {
        return name;
    }

    public void setRated(String rated) {
        this.rated = rated;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setLocaltion(String localtion) {
        this.localtion = localtion;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Hotel{" + "rated=" + rated + ", urlImage=" + urlImage + ", price=" + price + ", localtion=" + localtion + ", name=" + name + '}';
    }
    
}
