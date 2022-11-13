/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.GUI;

import java.awt.Image;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.imageio.ImageIO;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class GetImgae {

    public static void main(String[] args) throws KeyManagementException, NoSuchAlgorithmException, MalformedURLException, IOException {
      
// Now you can access an https URL without having the certificate in the truststore
        Image image = null;

        try {
            URL url = new URL("http://www.geognos.com/api/en/countries/flag/TH.png");
            image = ImageIO.read(url);

        } catch (MalformedURLException e) {
        }

//
////            URL url = new URL("https://flagcdn.com/w320/vn.png");
        JFrame frame = new JFrame();
        frame.setSize(300, 300);
        JLabel label = new JLabel(new ImageIcon(image));
        frame.add(label);
        frame.setVisible(true);
    }


}