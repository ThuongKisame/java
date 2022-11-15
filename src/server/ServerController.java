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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
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
import server.DTO.Acronym;
import server.DTO.Dictionary;
import server.DTO.User;
import static server.Server.ACRONYM_SELECTOR_ACRONYM_NAME;
import static server.Server.ACRONYM_SELECTOR_ENGLISH;

/**
 *
 * @author TTC
 */
public class ServerController {

    public static final int LOGIN = 1;
    public static final int SEARCH = 2;
    public static final String SEND_SEARCH = "3";
    public static final int GET_RECOMMEND = 4;
    public static final int SEND_RECOMMEND = 5;

    public static final int GET_CITY_ACRONYM = 6;
    public static final int SEND_CITY_ACRONYM = 7;

    public static final int GET_WEATHER = 8;
    public static final int SEND_WEATHER = 9;

    public static final int GET_HOTELS = 10;
    public static final int SEND_HOTELS = 11;
    
     public static final int GET_HOTELS_IN_CT = 12;
    public static final int SEND_HOTELS_IN_CT = 13;

    public static final String SPLIT_TWO = "/==/";
    public static final String SPLIT_THREE = "/===/";
    public static final String SPLIT_FOUR = "/====/";

    //
    public static String API_SEARCH_COUNTRY = "https://restcountries.com/v3.1/name/";
    public static String API_SEARCH_COUNTRY_RECOMEND = "https://www.agoda.com/api/cronos/search/GetUnifiedSuggestResult/3/24/24/0/vi-vn/?searchText=";
    public static String API_SEARCH_CITY = "https://api.openweathermap.org/geo/1.0/direct?q=";
    public static String API_CITY_KEY = "&limit=5&appid=7dfe2d591ff5d64ff7e4a4546f942d81";
    public static String API_WEATHER_HEADER = "https://api.openweathermap.org/data/2.5/weather?lat=";
    public static String API_WEATHER_BODY = "&lon=";
    public static String API_WEATHER_TAIL = "&appid=7dfe2d591ff5d64ff7e4a4546f942d81";

    public static final long MILLIS_IN_A_DAY = 1000 * 60 * 60 * 24;

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
                case GET_RECOMMEND: {
                    handleGetRecommend(res[1].trim());
                    break;
                }
                case GET_CITY_ACRONYM: {
                    handleGetCityAcronym(res[1].trim());
                    break;
                }
                case GET_WEATHER: {
                    handleGetWeaher(res[1]);
                    break;
                }
                case GET_HOTELS: {
                    handleGetHotels(res[1].trim());
                    break;
                }
                case GET_HOTELS_IN_CT:{
                    handleGetHotelsInCT(res[1].trim());
                    break;
                }
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
            response = SEND_SEARCH + SPLIT_TWO + response;
            sendMessageAES(response, user);
        }
    }

    private static String searchCountry(String searchValue) {
        List<Dictionary> countrys = new ArrayList<>();
        for (int i = 0; i < Server.listDictionary.size(); i++) {
            if (Server.listDictionary.get(i).getVietnameseUpperCase().contains(searchValue) || Server.listDictionary.get(i).getEnglishUpperCase().contains(searchValue)) {
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
        if (response.equals("")) {
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

    private static String getInforCountry(Acronym d) {
        String url = API_SEARCH_COUNTRY + d.getEnglisgName();
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
            if (arrSize > 0) {
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
            } else {
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
                if (arrSize > MAXIMUM_SEARCH_REQUEST + 1) {
                    arrSize = MAXIMUM_SEARCH_REQUEST + 1;
                }
                //lấy từ phần tử thứ 2 đến cuối mảng 
                for (int i = 1; i < arrSize; i++) {
                    if (response.length() <= 0) {
                        response = arrResult.get(i).toString();
                    } else {
                        response += SPLIT_THREE + arrResult.get(i).toString();
                    }
                }
            } else {
                return "[]";
            }

            System.out.println("city///" + response);
            return response;
        } catch (Exception e) {
            System.out.println("Không thể lấy dữ liệu từ:" + url);
        }
        return "[]";
    }

    private void handleGetRecommend(String request) throws IOException {
        String response = SEND_RECOMMEND + SPLIT_TWO + getRecomend(request);
        sendMessageAES(response, this.user);

    }

    private void handleGetCityAcronym(String acronym) throws IOException {
        Acronym ac = findAcronym(acronym);
        if (ac != null) {
            String response = getInforCountry(ac);
            if (response.length() > 0) {
                String re = SEND_CITY_ACRONYM + SPLIT_TWO + response;
                sendMessageAES(re, user);
            }
        }
    }

    private Acronym findAcronym(String acronym) {
        for (int i = 0; i < Server.listAcronyms.size(); i++) {
            if (Server.listAcronyms.get(i).getAcronymName().equalsIgnoreCase(acronym)) {
                return Server.listAcronyms.get(i);
            }
        }
        return null;
    }

    private void handleGetWeaher(String re) throws IOException {
        String res[] = re.split(SPLIT_THREE);
        String response = getWeather(res[0], res[1]);
        if (response.length() > 0) {
            response = SEND_WEATHER + SPLIT_TWO + response;
            sendMessageAES(response, user);
        }

    }

    private String getWeather(String lati, String longti) {
        String url = API_WEATHER_HEADER + lati + API_WEATHER_BODY + longti + API_WEATHER_TAIL;
        System.out.println(url);
        String result;
        try {
            result = Jsoup.connect(url).ignoreContentType(true).execute().body();
//            String recomend=getRecomend(d.getEnglish());
            return result.toString();
        } catch (Exception e) {
            System.out.println("Không thể lấy dữ liệu từ:" + url);
        }
        return "";
    }

    private void handleGetHotels(String cityName) throws IOException {
        String response =SEND_HOTELS+SPLIT_TWO+getHotelsData(cityName);
        sendMessageAES(response, user);
    }


    private String getHotelsData(String cityName) throws IOException {
        LocalDate currentDay = LocalDate.now(); // Create a date object
        LocalDate nextDay = currentDay.plusDays(1);
        
        String url = Server.URL_HOTELS + cityName + "&checkin=" + currentDay + "&checkout=" + nextDay + "&group_adults=2&no_rooms=1&group_children=0&sb_travel_purpose=leisure";
        System.out.println("352" + url);

        Document doc = Jsoup.connect(url).get();
        List<org.jsoup.nodes.Element> result = doc.select(Server.HOTEL_SELECTOR_ITEM);
        if (result.size() > 0) {
            JSONArray res = new JSONArray();
            for (int i = 0; i < MAXIMUM_SEARCH_REQUEST; i++) {
                String hotelName = result.get(i).select(".fcab3ed991.a23c043802").text();
                String price = result.get(i).select(".fd1924b122.d4741ba240 span.fcab3ed991.fbd1d3018c.e729ed5ab6").text();
                String rated = result.get(i).select(".b5cd09854e.d10a6220b4").text();
                String location = result.get(i).select(".f4bd0794db .cb5ebe3ffb span:first-child").text();
                String imgage = result.get(i).select("img").attr("src");

                JSONObject json = new JSONObject();
                json.put("hotelName", hotelName);
                json.put("price", price);
                json.put("rated", rated);
                json.put("location", location);
                json.put("imgage", imgage);

                res.put(json);

            }
            return res.toString();
        }
        return "";
    }

    private void handleGetHotelsInCT(String cityName) throws IOException {
        String response =SEND_HOTELS_IN_CT+SPLIT_TWO+getHotelsData(cityName);
        sendMessageAES(response, user);
    }

}
