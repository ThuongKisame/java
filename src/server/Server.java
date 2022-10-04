/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author TTC
 */
public class Server {
    
    public static ArrayList<Socket> clients=new ArrayList<Socket>();

    public static void main(String[] args) {
        Socket client=null;
        try {
            ServerSocket server =new ServerSocket(12345);
            System.out.println("waiting connect !");
            System.out.println(server.getLocalSocketAddress());
            while (true) {
                client = new Socket();
                client = server.accept();
                System.out.println(client.toString());
                clients.add(client);
                ServerListener listener = new ServerListener(client);
                listener.start();
                ClientThread clientThread = new ClientThread(client);
                clientThread.start();
            }
        } catch (IOException ex) {
            System.out.println("Can't connect this port!");
        }
    }

}
