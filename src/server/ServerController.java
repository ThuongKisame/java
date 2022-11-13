/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.lang.model.element.Element;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import server.DTO.Dictionary;
import server.DTO.User;

/**
 *
 * @author TTC
 */
public class ServerController {

    public static final int LOGIN = 1;
    public static final int SEARCH = 2;

    public static final String SEND_SEARCH = "3";
    
    public static final String SPLIT_TWO = "/==/";
    public static final String SPLIT_THREE = "/===/";
    public static final String SPLIT_FOUR = "/====/";


    //
    public static String API_SEARCH_COUNTRY = "https://restcountries.com/v3.1/name/";
    public static String API_SEARCH_COUNTRY_RECOMEND="https://www.agoda.com/api/cronos/search/GetUnifiedSuggestResult/3/24/24/0/vi-vn/?searchText=";
    public static String API_SEARCH_CITY = "https://api.openweathermap.org/geo/1.0/direct?q=";
    public static String API_CITY_KEY ="&limit=5&appid=7dfe2d591ff5d64ff7e4a4546f942d81";
    
    
    public static int MAXIMUM_SEARCH_REQUEST = 5;
    private User user;

    public ServerController(User user) {
        this.user = user;
    }

    public void deCodeSecretKey(String sms) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        user.setSecretKey(RSA.deCode(sms, Server.privateKey));
    }

    public void run(String sms) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, IOException {
        if (user.getSecretKey() == null) {
            System.out.println(sms);
            //giải mã khóa bí mật
            deCodeSecretKey(sms);
//            System.out.println("-------" + user.getSecretKey());

        } else {
            String response = AES.decrypt(sms, user.getSecretKey());
            System.out.println(response);

            String[] res = response.split(SPLIT_TWO);
            int option = Integer.parseInt(res[0]);

            switch (option) {
                case LOGIN:
                    // Làm gì đó tại đây ...
                    break;
                case SEARCH:
                    System.out.println("search: " + res[1]);
                    handleSearch(res[1].trim().toUpperCase());
                    break;
                default:
                // Làm gì đó tại đây ...
            }
        }

    }

    public static void sendMessageAES(String sms, User user) throws IOException {
        String res = AES.encrypt(sms, user.getSecretKey());
        System.out.println(sms);
        user.getOut().write(res);
        user.getOut().newLine();
        user.getOut().flush();
    }

    private void handleSearch(String searchValue) throws IOException {
        String response;
        response = searchCountry(searchValue);
        System.out.println("searchCountry" + response);
        
        response += SPLIT_FOUR + getInforCity(searchValue);
        System.out.println("searchCity" + response);

        if (response.length() > 0) {
            response = SEND_SEARCH+SPLIT_TWO + response;
            sendMessageAES(response, user);
        }
    }

    private static String searchCountry(String searchValue) {
        List<Dictionary> countrys = new ArrayList<>();
        for (int i = 0; i < Server.listDictionary.size(); i++) {
            if (Server.listDictionary.get(i).getVietnameseUpperCase().contains(searchValue)||Server.listDictionary.get(i).getEnglishUpperCase().contains(searchValue)) {
                countrys.add(Server.listDictionary.get(i));
                if (countrys.size() >= MAXIMUM_SEARCH_REQUEST) {
                    break;
                }
            }
        }
        System.out.println("arr" + countrys.toString());
        String response = "";
        if (countrys.size() > 0) {
            for (int i = 0; i < countrys.size(); i++) {
                if (response.length() == 0) {
                    response += getInforCountry(countrys.get(i));
                } else {
                    response += SPLIT_THREE + getInforCountry(countrys.get(i));
                }
            }
        }
        if(response.equals("")){
            return "[]";
        }
        return response;
    }

    private static String getInforCountry(Dictionary d) {
        String url = API_SEARCH_COUNTRY + d.getEnglish();
        System.out.println(url);
        String result;
        try {
            result = Jsoup.connect(url).ignoreContentType(true).execute().body();
            //get recomend
//            String recomend=getRecomend(d.getEnglish());
            return result.toString();
        } catch (Exception e) {
            System.out.println("Không thể lấy dữ liệu từ:" + url);
        }
        return "";
    }
     private static String getInforCity(String searchValue) {
        String url = API_SEARCH_CITY + searchValue + API_CITY_KEY;
        System.out.println(url);
        String result;
        try {
            result = Jsoup.connect(url).ignoreContentType(true).execute().body();
            JSONArray arrResult = new JSONArray(result);
            String response = "";
            int arrSize = arrResult.length();
            if (arrSize >0) {
                //lấy nhiều nhất 5 kết quả 
                if (arrSize > MAXIMUM_SEARCH_REQUEST) {
                    arrSize = MAXIMUM_SEARCH_REQUEST;
                }
                //lấy từ phần tử thứ 2 đến cuối mảng 
                for (int i = 0; i < arrSize; i++) {
                    if (response.length() <= 0) {
                        response = arrResult.get(i).toString();
                    } else {
                        response += SPLIT_THREE + arrResult.get(i).toString();
                    }
                }
            }else{
                 return "[]";
            }
            
            return response;
        } catch (Exception e) {
            System.out.println("Không thể lấy dữ liệu từ:" + url);
        }
        return "[]";
    }

    private static String getRecomend(String countryName) {
        String url = API_SEARCH_COUNTRY_RECOMEND + countryName;
        System.out.println(url);
        String result;
        try {
            result = Jsoup.connect(url).ignoreContentType(true).execute().body();
            JSONObject json = new JSONObject(result);
            JSONArray arrResult = json.getJSONArray("ViewModelList");
            String response = "";
            int arrSize = arrResult.length();
            if (arrSize > 1) {
                //lấy nhiều nhất 5 kết quả 
                if (arrSize > MAXIMUM_SEARCH_REQUEST+1) {
                    arrSize = MAXIMUM_SEARCH_REQUEST+1;
                }
                //lấy từ phần tử thứ 2 đến cuối mảng 
                for (int i = 1; i < arrSize; i++) {
                    if (response.length() <= 0) {
                        response = arrResult.get(i).toString();
                    } else {
                        response += SPLIT_THREE + arrResult.get(i).toString();
                    }
                }
            }
            
            
            System.out.println("city///"+response);
            return response;
        } catch (Exception e) {
            System.out.println("Không thể lấy dữ liệu từ:" + url);
        }
        return "[]";
    }
}
