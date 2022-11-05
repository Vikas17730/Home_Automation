/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cep;

import java.util.Date;

public class RFIDEvent {
    
    String sensorID;
    Date timestamp;
    int tag;
    int flag;
    
    public RFIDEvent(String s, int tg, int flg, long t) {
        sensorID = s;
        tag = tg;
        flag = flg;
        timestamp = new Date(t);
    }
    
    public int getFlag() {
        return flag;
    }
    
    public int getTag() {
        return tag;
    }
    
    public String getSensorID() {
        return sensorID;
    }
    
    public int getTimestamp() {
        return timestamp.getSeconds();
    }

    public String toString() {
        return "Sensor ID: "+sensorID + " Tag: "+tag+" Flag: "+flag+" time: "+timestamp.toString();
    }
    
}
