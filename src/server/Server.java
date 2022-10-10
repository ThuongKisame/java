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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import server.DTO.User;

/**
 *
 * @author TTC
 */
public class Server {

    public static ArrayList<User> users = new ArrayList<User>();
    public static ServerSocket server;
    public static PublicKey publicKey;
    public static PrivateKey privateKey;

 

    

    public static void sendMessage(String sms, User user) throws IOException {
        System.out.println(sms);
        user.getOut().write(sms);
        user.getOut().newLine();
        user.getOut().flush();
//        out.close();
    }

    public void WritingConnect() {
        Socket client = null;

        try {
            while (true) {
                client = new Socket();
                client = Server.server.accept();
                System.out.println("Client :" + client.toString() + " is connected");
                User user=new User(client);
                users.add(user);
                ServerListener listener = new ServerListener(user);
                listener.start();
                //Gửi khóa public cho client
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
            
            RSA.CreateServerKey();
            
//          writing client conect
            server.WritingConnect();
        } catch (IOException ex) {
            System.out.println("Can't connect this port!");
        }
    }

}
