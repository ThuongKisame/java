/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import server.DTO.User;

/**
 *
 * @author TTC
 */
public class ServerListener extends Thread {

    private User user;


    public ServerListener(User user) {
        this.user = user;
    }
    
    @Override
    public void run() {
        try {
            ServerController ctrl=new ServerController(this.user);
            while(true){
                String sms=user.getIn().readLine();
                ctrl.run(sms);
            }
        } catch (IOException ex) {
            System.out.println("Người dùng :"+user.toString() +" đã đóng kết nối");
            try {
                //handle close connect
                User.closeUser(user);
            } catch (IOException ex1) {
                System.out.println("Đóng kết nối lỗi !");
            }
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(ServerListener.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchPaddingException ex) {
            Logger.getLogger(ServerListener.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(ServerListener.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalBlockSizeException ex) {
            Logger.getLogger(ServerListener.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex) {
            Logger.getLogger(ServerListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
