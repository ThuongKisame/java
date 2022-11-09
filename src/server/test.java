/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import server.DTO.Dictionary;

/**
 *
 * @author TTC
 */
public class test {
    public static List<Dictionary> listDictionary=new ArrayList<>();
    public static void main(String[] args) throws IOException {
        Document doc = Jsoup.connect("https://dichthuatmientrung.com.vn/ten-cac-quoc-gia-va-quoc-tich-bang-tieng-anh/").get();
        List<Element> result=doc.select("table tr:not(:first-child) td:first-child"); 
        System.out.println(result.get(12).text());
        for (int i = 0; i < result.size(); i++) {
           String res[]=result.get(i).text().split("nước");
            System.out.println(res[0]+", "+res[1]);
            Dictionary d=new Dictionary(res[0].trim(), res[1].trim() );
            listDictionary.add(d);
        }
        System.out.println(listDictionary.toString());
    }
}
