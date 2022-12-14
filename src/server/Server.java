/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import server.DTO.Dictionary;
import server.DTO.User;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import server.DTO.Dictionary;
import org.jsoup.Jsoup;
import server.DTO.Acronym;

/**
 *
 * @author TTC
 */
public class Server {

    public static ArrayList<User> users = new ArrayList<User>();
    public static ServerSocket server;
    public static PublicKey publicKey;
    public static PrivateKey privateKey;

    public static List<Dictionary> listDictionary = new ArrayList<>();
    public static List<Acronym> listAcronyms = new ArrayList<>();
    
    public static String URL_DICTIONARY = "https://dichthuatmientrung.com.vn/ten-cac-quoc-gia-va-quoc-tich-bang-tieng-anh/";
    public static String DICTIONARY_SELECTOR = "table tr:not(:first-child) td:first-child";
    public static String DICTIONARY_SELECTOR_ENGLISH = "span:first-child";
    public static String DICTIONARY_SELECTOR_VIETNAMESE = "span:last-child";

    public static String URL_ACRONYM = "https://bankervn.com/ten-quoc-gia-tieng-anh/";
    public static String ACRONYM_SELECTOR = "table tr:not(:first-child)";
    //agoda
//    public static String HOTELS_SELECTOR = ".Gridstyled__GridStyled-sc-dfh2k0-0.kEJDDi.PropertyCard__Container";
//    public static String HOTELS_IMG_SELECTOR = ".HeroImage.HeroImage--s\"";
//    public static String HOTELS_NAME_SELECTOR=".PropertyCard__HotelName";
//    public static String HOTELS_ADDRESS_SELECTOR=".Address__Text";
//    public static String HOTELS_RATED_SELECTOR = ".Typographystyled__TypographyStyled-sc-j18mtu-0.Hkrzy.kite-js-Typography ";
//    public static String HOTELS_PRICE_SELECTOR = ".PropertyCardPrice__Value";
    //booking.com
    public static final String URL_HOTELS="https://www.booking.com/searchresults.vi.html?ss=";
    public static final String HOTEL_SELECTOR_ITEM=".a826ba81c4.fe821aea6c.fa2f36ad22.afd256fc79.d08f526e0d.ed11e24d01.ef9845d4b3.da89aeb942";
    
    
    
            
    public static int ACRONYM_SELECTOR_ENGLISH = 1;
    public static int ACRONYM_SELECTOR_ACRONYM_NAME = 2;

    public static void sendMessage(String sms, User user) throws IOException {
        System.out.println(sms);
        user.getOut().write(sms);
        user.getOut().newLine();
        user.getOut().flush();
//        out.close();
    }

    private static void getDictionary() throws IOException {
        Document doc = Jsoup.connect(Server.URL_DICTIONARY).get();
        List<Element> result = doc.select(Server.DICTIONARY_SELECTOR);
        result.forEach(e -> {
            String english = e.select(Server.DICTIONARY_SELECTOR_ENGLISH).text();
            String vietNamese = e.select(Server.DICTIONARY_SELECTOR_VIETNAMESE).text();
            if (vietNamese.contains("n?????c")) {
                vietNamese = vietNamese.replaceAll("n?????c", "").trim();
            }
            Dictionary d = new Dictionary(vietNamese, vietNamese.toUpperCase(), english, english.toUpperCase());
            Server.listDictionary.add(d);
        });

    }

    private static void getAcronym() throws IOException {
        Document doc = Jsoup.connect(Server.URL_ACRONYM).get();
        List<Element> result = doc.select(Server.ACRONYM_SELECTOR);
        result.forEach(e -> {
            List<Element> item = e.select("td");
            String english = item.get(ACRONYM_SELECTOR_ENGLISH).text();
            String acronym = item.get(ACRONYM_SELECTOR_ACRONYM_NAME).text();

            Acronym ac = new Acronym(english, acronym);
            Server.listAcronyms.add(ac);
        });

    }

    public void WritingConnect() {
        Socket client = null;

        try {
            while (true) {
                client = new Socket();
                client = Server.server.accept();
                System.out.println("Client :" + client.toString() + " is connected");
                User user = new User(client);
                users.add(user);
                ServerListener listener = new ServerListener(user);
                listener.start();
                //G???i kh??a public cho client
                Server.sendMessage(Base64.getEncoder().encodeToString(Server.publicKey.getEncoded()), user);
//                ClientThread clientThread = new ClientThread(client);
//                clientThread.start();
            }
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidKeySpecException {
        try {
            Server server = new Server();
            Server.server = new ServerSocket(12345);
            //t???o public key v?? private key
            RSA.CreateServerKey();
            //l???y d??? li???u chuy???n t??n n?????c t??? ti???ng vi???t sang ti???ng anh-{Vietnamese=n?????c ??an M???ch, English=Denmark}
            getDictionary();
            //l???y d??? li???u chuy???n t??n vi???t t???t sang t??n ti???ng anh
            getAcronym() ;
            
            System.out.println(Server.listDictionary);
            System.out.println(Server.listAcronyms);
            //writing client conect
            server.WritingConnect();
        } catch (IOException ex) {
            System.out.println("Can't connect this port!");
        }
    }

}
