/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cep;

import java.util.ArrayList;
import java.util.Date;

public class HumidityEvent {
    
    String sensorID;
    Date timestamp;
    Double humidity;
    Double humidityExpected;
    private static ArrayList<Double> humidityLevels = new ArrayList<>();
    
    public HumidityEvent(String s, double p, long t) {
        humidityLevels.add(63.0); humidityLevels.add(55.0);
        humidityLevels.add(47.0); humidityLevels.add(34.0);
        humidityLevels.add(33.0); humidityLevels.add(46.0);
        humidityLevels.add(70.0); humidityLevels.add(73.0);
        humidityLevels.add(62.0); humidityLevels.add(52.0);
        humidityLevels.add(55.0); humidityLevels.add(62.0);
        sensorID = s;
        humidity = p;
        timestamp = new Date(t);
        humidityExpected = humidityLevels.get(timestamp.getMonth());
    }
    
    public double getHumidity() {
        return humidity;
    }
    
    public double getHumidityExpected() {
        return humidityExpected;
    }
    
    public String getSensorID() {
        return sensorID;
    }
    
    public int getTimestamp() {
        return timestamp.getSeconds();
    }

    public String toString() {
        return "Sensor ID: "+sensorID + " Humidity: "+humidity.toString()+" time: "+timestamp.toString();
    }
    
}
