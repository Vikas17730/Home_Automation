/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cep;

import java.util.Date;

public class IntruderEvent {
    
    
    private String sensorID;
    private static int motionFlag;
    private static int rfidTag;
    private static int rfidFlag;
    Date timestamp;
    
    public IntruderEvent(String sid, int motionflag, int rfidflag, int rfidtag, long t) {
       sensorID = sid;
       motionFlag = motionflag;
       rfidFlag = rfidflag;
       rfidTag = rfidtag;
       timestamp = new Date(t);
    }
    
    public int getMotionFlag() {
        return motionFlag;
    }
    
    public int getRfidFlag() {
        return rfidFlag;
    }
    
    public int getRfidTag() {
        return rfidTag;
    }
    
    public String getSensorID() {
        return sensorID;
    }
    
    public int getTimestamp() {
        return timestamp.getSeconds();
    }
    
    public String toString() {
        return "Sensor ID: "+sensorID + " Motion Flag: "+motionFlag+" RFID Flag: "+rfidFlag+" RFID Tag: "+rfidTag+" Time: "+timestamp.toString();
    }
    
}
