/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import server.DTO.Dictionary;
import static server.ServerController.API_SEARCH_CITY;
import static server.ServerController.API_SEARCH_COUNTRY;
import static server.ServerController.MAXIMUM_SEARCH_REQUEST;

/**
 *
 * @author TTC
 */
public class test {

    public static void main(String[] args) throws IOException {

        String result = Jsoup.connect("https://api.openweathermap.org/geo/1.0/direct?q=VI&limit=5&appid=7dfe2d591ff5d64ff7e4a4546f942d81").ignoreContentType(true).execute().body();
        JSONArray arrResult = new JSONArray(result);
        System.out.println("arrr" + arrResult.toString());
    }
}
