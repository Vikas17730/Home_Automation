/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cep;

import static java.lang.Thread.sleep;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class MessageScreen {
    
    public MessageScreen(String msg) throws InterruptedException {
        
        JFrame frame = new JFrame("Smart Home Alert");
        
        frame.setSize(500,150);
        frame.setLayout(null);
    
        JLabel lb1 = new JLabel(msg);
        lb1.setSize(lb1.getPreferredSize());
        lb1.setLocation(42,50);
        frame.add(lb1);
        
        frame.setVisible(true);

    }
}
