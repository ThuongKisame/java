/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import client.DTO.City;
import server.AES;

import client.DTO.Country;
import client.DTO.Hotel;
import client.DTO.Weather;
import client.GUI.SearchCityItemPanel;
import client.GUI.SearchCountryItemPanel;
import client.GUI.TitleFarme;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import java.io.IOException;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author TTC
 */
public class ClientController {

    private static final String alpha = "abcdefghijklmnopqrstuvwxyz"; // a-z
    private static final String alphaUpperCase = alpha.toUpperCase(); // A-Z
    private static final String digits = "0123456789"; // 0-9
    private static final String ALPHA_NUMERIC = alpha + alphaUpperCase + digits;
    private static Random generator = new Random();

    public static final int LOGIN = 1;
    public static final int SEND_SEARCH = 2;
    public static final int GET_SEARCH = 3;

    public static final int SEND_RECOMMEND = 4;
    public static final int GET_RECOMMEND = 5;

    public static final int SEND_CITY_ACRONYM = 6;
    public static final int GET_CITY_ACRONYM = 7;

    public static final int SEND_WEATHER = 8;
    public static final int GET_WEATHER = 9;

    public static final int SEND_HOTELS = 10;
    public static final int GET_HOTELS = 11;

    public static final int SEND_HOTELS_IN_CT = 12;
    public static final int GET_HOTELS_IN_CT = 13;

    public static final String URL_IMG_FLAG = "http://www.geognos.com/api/en/countries/flag/";
    public static final String URL_IMG_MAP = "http://img.geonames.org/img/country/250/";
    public static final String URL_ICON_WEATHER_HEADER = "https://openweathermap.org/img/wn/";
    public static final String URL_ICON_WEATHER_TAIL = "@2x.png";

    public static final String SPLIT_TWO = "/==/";//dùng để gửi thông điệp
    public static final String SPLIT_THREE = "/===/";
    public static final String SPLIT_FOUR = "/====/";

    private Socket server;

    public ClientController(Socket server) {
        this.server = server;
    }

    public static void getHotelInCountry(String name) throws IOException {
        String response = SEND_HOTELS_IN_CT + SPLIT_TWO + name;
        sendMessage(response);
    }

    public void run(String sms) throws InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, IOException {
        if (Client.secretKey == null) {
            createKey(sms);
        } else {
            String response = AES.decrypt(sms, Client.secretKey);
            System.out.println(response);

            String[] res = response.split(SPLIT_TWO);
            int option = Integer.parseInt(res[0]);

//            sendMessage(AES.encrypt("hi",Client.secretKey));
            switch (option) {
                case GET_SEARCH: {
                    handleGetSearch(res[1].trim());
                    break;
                }
                case GET_RECOMMEND: {
                    handleGetRecommend(res[1].trim());
                    break;
                }
                case GET_CITY_ACRONYM: {
                    handleGetAcronym(res[1].trim());
                    break;
                }
                case GET_WEATHER: {
                    handleGetWeather(res[1]);
                    break;
                }
                case GET_HOTELS: {
                    handleGetHotels(res[1].trim());
                    break;
                }
                case GET_HOTELS_IN_CT: {
                    handleGetHotelsInCT(res[1].trim());
                    break;
                }
                default: {

                    break;
                }

            }
        }

    }

    public static int randomNumber(int min, int max) {
        return generator.nextInt((max - min) + 1) + min;
    }

    public String randomAlphaNumeric(int numberOfCharactor) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < numberOfCharactor; i++) {
            int number = randomNumber(0, ALPHA_NUMERIC.length() - 1);
            char ch = ALPHA_NUMERIC.charAt(number);
            sb.append(ch);
        }
        return sb.toString();
    }

    public void createSecretKey() {
        Client.secretKey = randomAlphaNumeric(8);
    }

    public static void sendMessage(String sms) throws IOException {
        System.out.println("Send to server:" + sms);
        String value = AES.encrypt(sms, Client.secretKey);
        Client.out.write(value);
        Client.out.newLine();
        Client.out.flush();
//        out.close();
    }

    public void createKey(String sms) throws InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, IOException {
        //nhận public key dạng string và convert sang byte
        byte[] bytes = Base64.getDecoder().decode(sms);
        //tạo public key
        X509EncodedKeySpec spec = new X509EncodedKeySpec(bytes);
        KeyFactory factory;
        try {
            factory = KeyFactory.getInstance("RSA");
            PublicKey pubKey = factory.generatePublic(spec);
            Client.publicKey = pubKey;
            createSecretKey();
            //Mã hóa khóa bí mật bằng publickey của server
            String res = RSA.enCode(Client.secretKey, Client.publicKey);
            //gửi secretKey cho lại server
            Client.out.write(res);
            Client.out.newLine();
            Client.out.flush();

        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(ClientController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void handleGetSearch(String trim) {
        String res[] = trim.split(SPLIT_FOUR);

        String countrys[] = res[0].split(SPLIT_THREE);
        String citys[] = res[1].split(SPLIT_THREE);
        System.out.println("city::::" + citys[0]);

        //render search 
//        renderCountry("Không tìm thấy kết quả");
        if (countrys[0].contains("[]") && citys[0].contains("[]")) {
            System.out.println("////");
            renderCountry("Không tìm thấy kết quả");
        } else {
            render(countrys, citys);

        }
    }

    private void renderCountry(String sms) {
        if (Client.STATE_SEARCH_PANEL) {
            Client.containerResultSearch.removeAll();

        }
        Client.containerResultSearch.setBounds(100, 55, Client.farme.getWidth() - 200, 80);

        Client.containerResultSearch.setLayout(new BoxLayout(Client.containerResultSearch, BoxLayout.Y_AXIS));

        JLabel lable = new JLabel(sms);
        lable.setHorizontalAlignment(SwingConstants.CENTER);

        lable.setSize(Client.containerResultSearch.getWidth(), 40);

        Client.containerResultSearch.add(lable);
        Client.farme.container.add(Client.containerResultSearch);

        Client.farme.repaint();

        Client.STATE_SEARCH_PANEL = true;
    }

    private void render(String[] countrys, String[] citys) {
        if (Client.STATE_SEARCH_PANEL) {
            Client.containerResultSearch.removeAll();

        }
        //reset data
        Client.countrys.removeAll(Client.countrys);
        Client.citys.removeAll(Client.citys);

        Client.containerResultSearch.setBounds(100, 55, Client.farme.getWidth() - 200, 600);

        BoxLayout boxLayout = new BoxLayout(Client.containerResultSearch, BoxLayout.Y_AXIS);
        Client.containerResultSearch.setLayout(boxLayout);
        System.out.println(countrys[0]);
        if (!countrys[0].contains("[]")) {

            for (String country : countrys) {
                Country ct = convertStringToCountry(country);
                Client.countrys.add(ct);
            }

            Dimension d = new Dimension(1150, 30);
            TitleFarme title = new TitleFarme("Quốc gia", d);
            Client.containerResultSearch.add(Box.createRigidArea(new Dimension(1000, 10)));
            Client.containerResultSearch.add(title);
            Client.containerResultSearch.add(Box.createRigidArea(new Dimension(1000, 10)));

            Client.countrys.forEach(e -> {
                SearchCountryItemPanel item;
                try {
                    item = new SearchCountryItemPanel(e);
                    item.countryName.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 1, Color.BLACK));
                    item.countryName.setBackground(Color.GRAY);
                    item.repaint();
                    Client.containerResultSearch.add(item);

                } catch (IOException ex) {
                    Logger.getLogger(ClientController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        }
        if (!citys[0].contains("[]")) {

            Dimension d = new Dimension(1150, 30);

            Client.containerResultSearch.add(Box.createRigidArea(new Dimension(1000, 10)));
            TitleFarme title = new TitleFarme("Thành phố", d);
            Client.containerResultSearch.add(title);

            for (String city : citys) {
                JSONObject jsonObj = new JSONObject(city);
                String name = jsonObj.getString("name");
                String flag = jsonObj.getString("country");
                Double lat = jsonObj.getDouble("lat");
                Double lon = jsonObj.getDouble("lon");

                City ct = new City(name, city, flag, lon, lat);
                Client.citys.add(ct);

            }

            Client.citys.forEach(e -> {
                System.out.println(e.toString());
                Dimension dm = new Dimension(1150, 60);

                SearchCityItemPanel item;
                try {
                    item = new SearchCityItemPanel(e, dm);
                    item.setMaximumSize(dm);
                    item.setMinimumSize(dm);
                    item.repaint();
                    Client.containerResultSearch.add(item);
                } catch (IOException ex) {
                    Logger.getLogger(ClientController.class.getName()).log(Level.SEVERE, null, ex);
                }

            });
        }

        Client.farme.container.add(Client.containerResultSearch);

        Client.farme.revalidate();
        Client.farme.repaint();

        Client.STATE_SEARCH_PANEL = true;
    }

    public static Image getScaledImage(Image Img, int wt, int ht) {
        BufferedImage resizedImg = new BufferedImage(wt, ht, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = resizedImg.createGraphics();

        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(Img, 0, 0, wt, ht, null);
        g2.dispose();

        return resizedImg;
    }

    public static void getRecommend(String countryName) throws IOException {
        String sms = SEND_RECOMMEND + SPLIT_TWO + countryName;
        sendMessage(sms);
    }

    private void handleGetRecommend(String result) throws IOException {
        Client.countryPanel.country.setRecommend(result);

        client.Client.countryPanel.renderRecommed(client.Client.countryPanel);
        System.out.println("----------------------");
        if (Client.countryPanel.country.rcmd.size() > 0) {
            System.out.println("++++++++++");
            getHotelInCountry(Client.countryPanel.country.rcmd.get(0).getName().split(",")[0]);
        }
        client.Client.farme.repaint();
    }

    private Country convertStringToCountry(String country) {
        JSONArray jsonArr = new JSONArray(country);

        String name = jsonArr.getJSONObject(0).getJSONObject("name").getString("common");

        String cca2 = jsonArr.getJSONObject(0).getString("cca2");
        String url = URL_IMG_FLAG + cca2 + ".png";
        String urlMap = URL_IMG_MAP + cca2 + ".png";
        String currencies = jsonArr.getJSONObject(0).getJSONObject("currencies").toString().split("\":")[0].split("\"")[1];
        String languages = jsonArr.getJSONObject(0).getJSONObject("languages").toString().split("\":\"")[1].split("\"")[0];
        int population = jsonArr.getJSONObject(0).getInt("population");
        String latlng = jsonArr.getJSONObject(0).getJSONArray("latlng").toString();

        return new Country(name, url, urlMap, country, currencies, languages, population + "", latlng, "");
    }

    private void handleGetAcronym(String countryString) throws IOException {
        Client.cityPanel.city.country = convertStringToCountry(countryString);
        Client.cityPanel.renderCountry(Client.cityPanel);

    }

    private void handleGetWeather(String re) throws IOException {
        Client.cityPanel.city.weather = converStringToWeather(re);
        Client.cityPanel.renderWeather(Client.cityPanel);
    }

    private Weather converStringToWeather(String weatherString) {
        JSONObject json = new JSONObject(weatherString);
        String description = json.getJSONArray("weather").getJSONObject(0).getString("description");
        String iconUrl = json.getJSONArray("weather").getJSONObject(0).getString("icon");
        Double temp = json.getJSONObject("main").getDouble("temp");
        Double humidity = json.getJSONObject("main").getDouble("humidity");
        Double windSpeed = json.getJSONObject("wind").getDouble("speed");

        return new Weather(description, iconUrl, temp, humidity, windSpeed);
    }

    private void handleGetHotels(String arrString) {
        convertStringToHotel(arrString, Client.cityPanel.city.hotels);
        Client.cityPanel.renderHotel(Client.cityPanel);
    }

    private void convertStringToHotel(String arrString, List<Hotel> hotels) {
        JSONArray arr = new JSONArray(arrString);
        for (int i = 0; i < arr.length(); i++) {
            JSONObject json = arr.getJSONObject(i);

            String hotelName = json.getString("hotelName");
            String price = json.getString("price");
            String rated = json.getString("rated");
            String location = json.getString("location");
            String imgage = json.getString("imgage");

            Hotel hotel = new Hotel(rated, imgage, price, location, hotelName);

            hotels.add(hotel);
        }
    }

    private void handleGetHotelsInCT(String arrString) {
        convertStringToHotel(arrString, Client.countryPanel.country.hotels);
        Client.countryPanel.renderHotels(Client.countryPanel);
    }

}
