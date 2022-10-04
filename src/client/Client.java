/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author TTC
 */
public class Client {

    public static int port;
    public static String serverIP;
    public static BufferedReader in;
    public static BufferedWriter out;

    private void LoadData() throws FileNotFoundException, IOException {
        String currentPath = new java.io.File(".").getCanonicalPath();
        System.out.println(currentPath);
        String url = currentPath + "\\src\\client\\data.txt";
//          String url=
        //C:\Users\TTC\Documents\NetBeansProjects\LTM\src\client\data.txt
        // Đọc dữ liệu từ File với Scanner
        FileInputStream fileInputStream = new FileInputStream(url);
        Scanner scanner = new Scanner(fileInputStream);

        try {
            while (scanner.hasNextLine()) {
                String data = scanner.nextLine();
                String res[] = data.split("-");
                String serverIP=res[0].split(":")[1];
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
            System.out.println("success!");
            Scanner sc = new Scanner(System.in);
            //listener server message
            ClientListener clientlistener = new ClientListener(server);
            clientlistener.start();
            Client.out = new BufferedWriter(new OutputStreamWriter(server.getOutputStream()));
            while (true) {
                String sms = sc.nextLine();
                Client.out.write(sms);
                Client.out.newLine();
                Client.out.flush();
            }
        } catch (IOException ex) {
            System.out.println("Can't connect this server!");
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
