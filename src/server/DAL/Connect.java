package server.DAL;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author TTC
 */
public class Connect {
    
    private static String hostName = "localhost";
    private static String dbName = "chess";
    private static String userName = "root";
    private static String password = "";
    public static Connection conn;

    private static String connectionURL = "jdbc:mysql://" + hostName + ":3306/" + dbName;

    public Connect() {
    }

    public static void getConnect() {
        conn = null;
        try {
            conn = (Connection) DriverManager.getConnection(connectionURL, userName, password);
        } catch (Exception er) {
            
            System.out.println("Xampp!!");
        }
    }
    public static void main(String[] args) throws SQLException {
        getConnect(); 
        if (conn!=null) 
        {
            System.out.println(conn);
            System.out.println("Ket noi thanh cong");
        } else {
            System.out.println("Ket noi that bai");
        }
    }
}
