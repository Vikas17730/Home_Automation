/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cep;

import java.util.Date;

public class MotionEvent {
    
    String sensorID;
    long timestamp;
    int flag;
    
    public MotionEvent(String s, int p, long t) {
        sensorID = s;
        flag = p;
        timestamp = t;
    }
    
    public int getFlag() {
        return flag;
    }
    public String getSensorID() {
        return sensorID;
    }
    
    public long getTimestamp() {
        return timestamp;
    }

    public String toString() {
        return "Sensor ID: "+sensorID + " Flag: "+flag+" time: "+new Date(timestamp).toString();
    }
    
}
