/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author TTC
 */
public class ClientListener extends Thread  {
    private Socket server;
     public ClientListener(Socket server) {
        this.server = server;
    }
    @Override
    public void run() {
        try {
            Client.in=new BufferedReader(new InputStreamReader(this.server.getInputStream()));
            while(true){
                String sms= Client.in.readLine();
                System.out.println(sms);
            }
        } catch (IOException ex) {
            Logger.getLogger(ClientListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
