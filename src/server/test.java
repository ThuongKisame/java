/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.IOException;
import static java.rmi.server.LogStream.log;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import server.DTO.Dictionary;
import static server.ServerController.API_SEARCH_CITY;
import static server.ServerController.API_SEARCH_COUNTRY;
import static server.ServerController.MAXIMUM_SEARCH_REQUEST;

/**
 *
 * @author TTC
 */
public class test {

    private static String getHotelsData(String cityName) throws IOException {
        String url = Server.URL_HOTELS + cityName + "&checkin=2022-12-13&checkout=2022-12-14&group_adults=2&no_rooms=1&group_children=0&sb_travel_purpose=leisure";
        System.out.println("352" + url);

        Document doc = Jsoup.connect(url).get();
        List<org.jsoup.nodes.Element> result = doc.select(Server.HOTEL_SELECTOR_ITEM);
        if (result.size() > 0) {
            for (int i = 0; i < MAXIMUM_SEARCH_REQUEST; i++) {
                String hotelName = result.get(i).select(".fcab3ed991.a23c043802").text();
                String price = result.get(i).select(".fd1924b122.d4741ba240 span.fcab3ed991.fbd1d3018c.e729ed5ab6").text();
                String rated = result.get(i).select(".b5cd09854e.d10a6220b4").text();
                String location = result.get(i).select(".a1fbd102d9 .cb5ebe3ffb span").text();
                String imgage = result.get(i).select("img").attr("src");

                System.out.println(hotelName + " " + price + " " + rated + " " + location + " " + imgage);
            }
        }
        return "";
    }

    public static void main(String[] args) throws IOException, InterruptedException {

//        String url = "https://www.booking.com/searchresults.vi.html?ss=tam%20k%E1%BB%B3";
//        System.out.println(getHotelsData("tam ky"));
           float longti = Float.parseFloat("-80");
           System.out.println(longti);
    }
}
