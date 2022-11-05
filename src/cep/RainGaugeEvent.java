/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cep;

import java.util.Date;

public class RainGaugeEvent {
    
    String sensorID;
    Date timestamp;
    int flag;
    static int state = 0;
    int trans;
    
    public RainGaugeEvent(String s, int f, long t) {
        sensorID = s;
        flag = f;
        if(state!=f) {
            state = f;
            trans = 1;
        }
        else {
            trans = 0;
        }
        timestamp = new Date(t);
        //System.out.println(timestamp.getTime());
    }
    
    public int getFlag() {
        return flag;
    }
    
    public int getTrans() {
        return trans;
    }
    
    public int getState() {
        return state;
    }
    
    public String getSensorID() {
        return sensorID;
    }
    
    public int getTimestamp() {
        return timestamp.getSeconds();
    }

    public String toString() {
        return "Sensor ID: "+sensorID + " Flag: "+flag+" time: "+timestamp.toString();
    }
    
}
