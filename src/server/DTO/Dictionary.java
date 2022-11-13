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
    private String VietnameseUpperCase;
    private String English;
    private  String EnglishUpperCase;

    public Dictionary(String Vietnamese, String VietnameseUpperCase, String English, String EnglishUpperCase) {
        this.Vietnamese = Vietnamese;
        this.VietnameseUpperCase = VietnameseUpperCase;
        this.English = English;
        this.EnglishUpperCase = EnglishUpperCase;
    }

    public String getVietnamese() {
        return Vietnamese;
    }

    public String getVietnameseUpperCase() {
        return VietnameseUpperCase;
    }

    public String getEnglish() {
        return English;
    }

    public String getEnglishUpperCase() {
        return EnglishUpperCase;
    }

    public void setVietnamese(String Vietnamese) {
        this.Vietnamese = Vietnamese;
    }

    public void setVietnameseUpperCase(String VietnameseUpperCase) {
        this.VietnameseUpperCase = VietnameseUpperCase;
    }

    public void setEnglish(String English) {
        this.English = English;
    }

    public void setEnglishUpperCase(String EnglishUpperCase) {
        this.EnglishUpperCase = EnglishUpperCase;
    }

    
}
