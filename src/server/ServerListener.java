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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author TTC
 */
public class ServerListener extends Thread {

    private Socket client;
    private BufferedReader in;
    private BufferedWriter out;


    public ServerListener(Socket client) {
        this.client = client;
    }
    
    @Override
    public void run() {
        try {
            this.in=new BufferedReader(new InputStreamReader(client.getInputStream()));
            while(true){
                String sms=this.in.readLine();
                System.out.println(sms);
            }
        } catch (IOException ex) {
            Logger.getLogger(ServerListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
