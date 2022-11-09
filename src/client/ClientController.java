/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import server.AES;

import java.io.IOException;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * @author TTC
 */
public class ClientController {

    private static final String alpha = "abcdefghijklmnopqrstuvwxyz"; // a-z
    private static final String alphaUpperCase = alpha.toUpperCase(); // A-Z
    private static final String digits = "0123456789"; // 0-9
    private static final String ALPHA_NUMERIC = alpha + alphaUpperCase + digits;
    private static Random generator = new Random();

    public static final String SEND_SEARCH="2//";
    
    public static final int LOGIN = 1;
    public static final int UPDATE = 2;
    private Socket server;

    public ClientController(Socket server) {
        this.server = server;
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
//                System.out.println(pubKey);
            //Mã hóa khóa bí mật bằng publickey của server
            String res = RSA.enCode(Client.secretKey, Client.publicKey);
            //gửi secretKey cho lại server
            Client.out.write(res);
            Client.out.newLine();
            Client.out.flush();
//            System.out.println(mes)
//            System.out.println(Client.secretKey);

        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(ClientController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void run(String sms) throws InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, IOException {
        if (Client.secretKey == null) {
            createKey(sms);
        } else {
            String response = AES.decrypt(sms, Client.secretKey);
            System.out.println(response);

            String[] res = response.split("//");
            int option = Integer.parseInt(res[0]);

//            sendMessage(AES.encrypt("hi",Client.secretKey));
            switch (option) {

                default: {

                    break;
                }

                // Làm gì đó tại đây ...            // Làm gì đó tại đây ...
            }
        }

    }

}
