/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cep;

import java.net.*;

public class SendSMS {

        public SendSMS(String to, String msg) {
                try {
                        String recipient = to;
                        String message = msg;
                        String username = "admin";
                        String password = "abc123";
                        String originator = "+918090002396";

                        String requestUrl  = "http://127.0.0.1:9501/api?action=sendmessage&" +
            "username=" + URLEncoder.encode(username, "UTF-8") +
            "&password=" + URLEncoder.encode(password, "UTF-8") +
            "&recipient=" + URLEncoder.encode(recipient, "UTF-8") +
            "&messagetype=SMS:TEXT" +
            "&messagedata=" + URLEncoder.encode(message, "UTF-8") +
            "&originator=" + URLEncoder.encode(originator, "UTF-8") +
            "&serviceprovider=SMPP0" +
            "&responseformat=html";



                        URL url = new URL(requestUrl);
                        HttpURLConnection uc = (HttpURLConnection)url.openConnection();

                        System.out.println(uc.getResponseMessage());

                        uc.disconnect();

                } catch(Exception ex) {
                        System.out.println(ex.getMessage());

                }
                System.out.println("Sent Successfully");
        }

}
