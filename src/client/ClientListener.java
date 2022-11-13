/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.io.IOException;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.spec.InvalidKeySpecException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.JOptionPane;

/**
 *
 * @author TTC
 */
public class ClientListener extends Thread {

    private Socket server;

    public ClientListener(Socket server) {
        this.server = server;
    }

    @Override
    public void run() {
        try {
            Client.ctrl = new ClientController(server);
            while (true) {
                String sms = Client.in.readLine();
                try {
                    Client.ctrl.run(sms);
                } catch (InvalidKeySpecException ex) {
                    Logger.getLogger(ClientListener.class.getName()).log(Level.SEVERE, null, ex);
                } catch (NoSuchPaddingException ex) {
                    Logger.getLogger(ClientListener.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InvalidKeyException ex) {
                    Logger.getLogger(ClientListener.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalBlockSizeException ex) {
                    Logger.getLogger(ClientListener.class.getName()).log(Level.SEVERE, null, ex);
                } catch (BadPaddingException ex) {
                    Logger.getLogger(ClientListener.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (IOException ex) {
            System.out.println("Server đã đóng !");
               JOptionPane.showMessageDialog(Client.farme, "Server đã đóng !");
        }
    }

}
