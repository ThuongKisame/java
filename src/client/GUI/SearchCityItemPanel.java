/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.GUI;

import client.Client;
import client.DTO.City;
import java.awt.Dimension;
import java.awt.Image;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 *
 * @author TTC
 */
public class SearchCityItemPanel extends javax.swing.JPanel {
    
    private City city;
    /**
     * Creates new form SearchCityItemPanel
     */
    public SearchCityItemPanel(City city, Dimension dm) throws MalformedURLException, IOException {
        this.city=city;
        initComponents();
        this.setMaximumSize(dm);
        this.setMinimumSize(dm);

//        this.setLayout(null);
//        this.icon.setBounds(0, 0, 60, 40);

        Image image = null;
        URL url = new URL(this.city.getUrlFlag());
        image = ImageIO.read(url);
        image = client.ClientController.getScaledImage(image, 60, 40);
        ImageIcon icon = new ImageIcon(image);
        this.img.setIcon(icon);

        this.cityName.setText(city.getName());
//        this.cityName.setBounds(65, 0, (int) dm.getWidth() - 65, 10);

       
//        this.categoryName.setBounds(65, 10, (int) dm.getWidth() - 65, 20);
        
        this.repaint();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        img = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        cityName = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(725, 60));

        jPanel1.setPreferredSize(new java.awt.Dimension(725, 50));
        jPanel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                handleClick(evt);
            }
        });

        img.setBackground(new java.awt.Color(255, 255, 255));
        img.setIcon(new javax.swing.ImageIcon(getClass().getResource("/client/img/icons8_google_maps_32.png"))); // NOI18N

        jPanel2.setPreferredSize(new java.awt.Dimension(673, 40));
        jPanel2.setLayout(new java.awt.CardLayout());

        cityName.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jPanel2.add(cityName, "card2");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(img, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 659, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(img, javax.swing.GroupLayout.DEFAULT_SIZE, 49, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 49, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void handleClick(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_handleClick
        try {
            System.out.println("click : "+city.toString());
            client.Client.cityPanel =new CityPanel(this.city,client.Client.dms);
            client.Client.cityPanel.setBounds(100, 55, Client.farme.getWidth() - 200, 600);
            
            Client.farme.container.removeAll();
            Client.farme.container.add(Client.cityPanel);
            
            Client.farme.container.repaint();
            Client.farme.validate();
            Client.farme.repaint();
            
            // TODO add your handling code here:
        } catch (IOException ex) {
            Logger.getLogger(SearchCityItemPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_handleClick


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JLabel cityName;
    private javax.swing.JLabel img;
    public javax.swing.JPanel jPanel1;
    public javax.swing.JPanel jPanel2;
    // End of variables declaration//GEN-END:variables
}
