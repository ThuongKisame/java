/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.DTO;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author TTC
 */
public class User {

    private Socket client;
    private String secretKey=null;
    private BufferedReader in;
    private BufferedWriter out;

    public User(Socket client) {
        try {
            this.in =new BufferedReader(new InputStreamReader(client.getInputStream()));
            this.out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
        } catch (IOException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Socket getClient() {
        return client;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public BufferedReader getIn() {
        return in;
    }

    public BufferedWriter getOut() {
        return out;
    }

    public void setClient(Socket client) {
        this.client = client;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public void setIn(BufferedReader in) {
        this.in = in;
    }

    public void setOut(BufferedWriter out) {
        this.out = out;
    }

    @Override
    public String toString() {
        return "User{" + "client=" + client + ", secretKey=" + secretKey + ", in=" + in + ", out=" + out + '}';
    }
    
    
    
}
