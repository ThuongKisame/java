/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

/**
 *
 * @author TTC
 */
public class ClientController {

    public static final int LOGIN = 1;
    public static final int UPDATE = 2;

    public void run(String sms) {
        String[] res = sms.split("//");
        int option = Integer.parseInt(res[0]);

        switch (option) {
            case LOGIN:
                // Làm gì đó tại đây ...
                break;
            case UPDATE:
                // Làm gì đó tại đây ...
                break;
            default:
            // Làm gì đó tại đây ...
        }
    }

}
