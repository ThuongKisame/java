/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import client.DTO.Country;
import client.GUI.CityPanel;
import client.GUI.CountryPanel;
import client.GUI.MainClientFarme;
import client.GUI.RoundedPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.PublicKey;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;

/**
 * @author TTC
 */
public class Client {

    public static int port;
    public static String serverIP;
    public static BufferedReader in;
    public static BufferedWriter out;
    public static PublicKey publicKey;
    public static String secretKey = null;
    
    public static MainClientFarme farme;
    public static Dimension dms=new Dimension(1166, 600);
    public static CityPanel cityPanel;
    public static CountryPanel countryPanel;
    
    
    public static ClientController ctrl;
    public static boolean STATE_SEARCH_PANEL=false;
    public static JPanel containerResultSearch  = new RoundedPanel(10, Color.WHITE);


    private void LoadData() throws FileNotFoundException, IOException {
        String currentPath = new java.io.File(".").getCanonicalPath();
        System.out.println(currentPath);
        String url = currentPath + "\\src\\client\\data.txt";
        // Đọc dữ liệu từ File với Scanner
        FileInputStream fileInputStream = new FileInputStream(url);
        Scanner scanner = new Scanner(fileInputStream);

        try {
            while (scanner.hasNextLine()) {
                String data = scanner.nextLine();
                String res[] = data.split("-");
                String serverIP = res[0].split(":")[1];
                Client.serverIP = res[0].split(":")[1];
                Client.port = Integer.parseInt(res[1].split(":")[1]);
            }
        } finally {
            try {
                scanner.close();
                fileInputStream.close();
            } catch (IOException ex) {

            }
        }
    }

    public static void main(String[] args) throws UnknownHostException, FileNotFoundException, IOException {

        Client cln = new Client();
        cln.LoadData();
        try {
            System.out.println(Client.serverIP);
            Socket server = new Socket(Client.serverIP, Client.port);
            System.out.println("Connected!");
            
            Scanner sc = new Scanner(System.in);
            //listener server message
            Client.in = new BufferedReader(new InputStreamReader(server.getInputStream()));
            Client.out = new BufferedWriter(new OutputStreamWriter(server.getOutputStream()));
            
            //Open gui
            Client.farme=new MainClientFarme();
            Client.farme.setVisible(true);
            
            ClientListener clientlistener = new ClientListener(server);
            clientlistener.start();
            
           
        } catch (IOException ex) {
            System.out.println("Can't connect this server!");
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
