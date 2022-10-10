/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.sql.Array;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
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

    public void run(String sms) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        if (user.getSecretKey() == null) {
            System.out.println(sms);
            //giải mã khóa bí mật
            deCodeSecretKey(sms);
//            System.out.println("-------" + user.getSecretKey());

        } else {
            String response=AES.decrypt(sms, user.getSecretKey());
            System.out.println("vao day");
            System.out.println(response);

            String[] res = response.split("//");
            int option = Integer.parseInt(res[0]);

            switch (option) {
                case LOGIN:
                    // Làm gì đó tại đây ...
                    break;
                case SEARCH:
                    // Làm gì đó tại đây ...
                    break;
                default:
                // Làm gì đó tại đây ...
            }
        }

    }
}
