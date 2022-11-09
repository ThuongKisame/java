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
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.lang.model.element.Element;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import server.DTO.User;

/**
 *
 * @author TTC
 */
public class ServerController {

    public static final int LOGIN = 1;
    public static final int SEARCH = 2;
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

            String[] res = response.split("//");
            int option = Integer.parseInt(res[0]);

            switch (option) {
                case LOGIN:
                    // Làm gì đó tại đây ...
                    break;
                case SEARCH:
                    System.out.println("search" + res[1]);
                    System.out.println( handelSearch(res[1].trim()));
                    break;
                default:
                // Làm gì đó tại đây ...
            }
        }

    }

    public static void sendMessageAES(String sms, User user) throws IOException {
        System.out.println(sms);
        user.getOut().write(sms);
        user.getOut().newLine();
        user.getOut().flush();
//        out.close();
    }

    private String handelSearch(String key) throws IOException {
        Document doc = Jsoup.connect("https://dichthuatmientrung.com.vn/ten-cac-quoc-gia-va-quoc-tich-bang-tieng-anh/").get();
        Element link= (Element) doc.select("table").first(); 
        System.out.println(link);
        
        String url = "https://restcountries.com/v3.1/name/"+key;
        String result;
        try {
            result = Jsoup.connect(url).ignoreContentType(true).execute().body();

//            JSONObject json = new JSONObject(result);
//            String res = json.getJSONArray("data").toString();

//            return res.substring(1, res.length() - 1);
            return result.toString();
        } catch (Exception e) {
            return "Không tìm thấy vùng!";
        }
    }
}
