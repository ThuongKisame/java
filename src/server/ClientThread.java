/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author TTC
 */
public class ClientThread extends Thread{
    private Socket client;
    private BufferedWriter out;
    public ClientThread (Socket client){
        this.client=client;
    }

    @Override
    public void run() {
        try {
            this.out=new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
            Scanner sc=new Scanner(System.in);
            while(true){
                String sms=sc.nextLine();
                this.out.write(sms);
                this.out.newLine();
                this.out.flush();
            }
        } catch (IOException ex) {
            Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
