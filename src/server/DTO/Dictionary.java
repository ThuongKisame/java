/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.DTO;

/**
 *
 * @author TTC
 */
public class Dictionary {
    private String Vietnamese;
    private String English;

    public Dictionary( String English, String Vietnamese) {
        this.Vietnamese = Vietnamese;
        this.English = English;
    }

    public String getVietnamese() {
        return Vietnamese;
    }

    public String getEnglish() {
        return English;
    }

    public void setVietnamese(String Vietnamese) {
        this.Vietnamese = Vietnamese;
    }

    public void setEnglish(String English) {
        this.English = English;
    }

    @Override
    public String toString() {
        return "Dictionary{" + "Vietnamese=" + Vietnamese + ", English=" + English + '}';
    }
    
}
