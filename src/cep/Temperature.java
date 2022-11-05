/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cep;

import java.util.Date;


public class Temperature {
    
    String symbol;
    Double temp;
    Date timestamp;
    
    public Temperature(String s, double p, long t) {
        symbol = s;
        temp = p;
        timestamp = new Date(t);
    }
    
    public double getTemp() {
        return temp;
    }
    public String getSymbol() {
        return symbol;
    }
    
    public int getTimestamp() {
        return timestamp.getSeconds();
    }

    public String toString() {
        return "Temperature: "+temp.toString()+" time: "+timestamp.toString();
    }

}
