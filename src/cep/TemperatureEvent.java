/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cep;

import java.util.ArrayList;
import java.util.Date;

public class TemperatureEvent {
    
    String sensorID;
    Date timestamp;
    Double temperature;
    private static ArrayList<Double> temperatureLevels = new ArrayList<>();
    Double temperatureExpected;
    
    public TemperatureEvent(String s, double p, long t) {
        temperatureLevels.add(15.0); temperatureLevels.add(18.0);
        temperatureLevels.add(24.0); temperatureLevels.add(30.0);
        temperatureLevels.add(34.0); temperatureLevels.add(33.0);
        temperatureLevels.add(29.0); temperatureLevels.add(29.0);
        temperatureLevels.add(28.0); temperatureLevels.add(26.0);
        temperatureLevels.add(21.0); temperatureLevels.add(17.0);
        sensorID = s;
        temperature = p;
        timestamp = new Date(t);
        temperatureExpected = temperatureLevels.get(timestamp.getMonth());
    }
    
    public double getTemperature() {
        return temperature;
    }
    
    public double getTemperatureExpected() {
        return temperatureExpected;
    }
    
    public String getSensorID() {
        return sensorID;
    }
    
    public int getTimestamp() {
        return timestamp.getSeconds();
    }

    public String toString() {
        return "Sensor ID: "+sensorID + " Temperature: "+temperature.toString()+" time: "+timestamp.toString();
    }
    
}
