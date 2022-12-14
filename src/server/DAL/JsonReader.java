/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.DAL;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonReader {

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }

    public static void main(String[] args) throws IOException, JSONException {
        JSONObject json = readJsonFromUrl("https://www.agoda.com/api/cronos/search/GetUnifiedSuggestResult/3/24/24/0/vi-vn/?searchText=quang%20nam");
        System.out.println(json.toString());

//        System.out.println(json.get("SuggestionList"));

//        JSONObject ar = new JSONObject(json.get("SuggestionList"));

        System.out.println();
        System.out.println(json.getJSONArray("SuggestionList").getJSONObject(0));
//        Gson gson = new Gson(); // khởi tạo Gson
//        Employee employee = gson.fromJson(json, Employee.class);

//        DemoJson j = gson.fromJson(json.toString(), DemoJson.class);

//        System.out.println(j.suggestions.get(0).objectId);


    }
//
//    public class DemoJson {
//        @SerializedName("SuggestionList")
//        public List<Suggestion> suggestions;
//    }
//
//    public class Suggestion {
//        @SerializedName("ObjectID")
//        public int objectId;
//    }
}