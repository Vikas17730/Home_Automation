/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cep;

import com.espertech.esper.client.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Random;
import static java.lang.Thread.sleep;
import static java.lang.Thread.sleep;

public class CEP {

    /**
     * @param args the command line arguments
     */
    
    
    public static Configuration cepConfig = new Configuration();    
    
    public static void main(String[] args) throws InterruptedException, FileNotFoundException, IOException {
        
        EPRuntime critical = new FireDetectorCritical().run();
        
        EPRuntime suspected = new FireDetectorSuspected().run();
        
        EPRuntime rain = new RainDetected().run();
        
        EPRuntime humidityup = new HumidityUp().run();
        
        EPRuntime humiditydown = new HumidityDown().run();
        
        EPRuntime temperatureup = new TemperatureUp().run();
        
        EPRuntime temperaturedown = new TemperatureDown().run();
        
        EPRuntime motionyes = new MotionYes().run();
        
        EPRuntime motionno = new MotionNo().run();
        
        EPRuntime intruderengine = new IntruderEngine().run();
        
        TemperatureEvent[] temperatureReadings = new TemperatureEvent[5];
        HumidityEvent[] humidityReadings = new HumidityEvent[5];
        MotionEvent[] motionReadings = new MotionEvent[5];
        RFIDEvent[] rfidReadings = new RFIDEvent[8];
        IntruderEvent[] intruderReadings = new IntruderEvent[8];
        
        BufferedReader brt = new BufferedReader(new FileReader("datatest.txt")); // temperature
        BufferedReader brh = new BufferedReader(new FileReader("H1.txt")); // Humidity
        BufferedReader rg  = new BufferedReader(new FileReader("R1.txt")); // RFID
        BufferedReader ms  = new BufferedReader(new FileReader("M1.txt")); // Rain
        BufferedReader rfid  = new BufferedReader(new FileReader("RF1.txt")); // RFID
        String st = "";
        String sh = "";
        String me = "";
        String timestamp="147972";
        int cnt = 0;
        int more = 1;
        while(more==1) {
            for(int i=0;i<5;i++) {
                me = ms.readLine();
                st = brt.readLine();
                sh = brh.readLine();
                
                if(st==null || sh==null) {
                    more = 0;
                    break;
                }
                String[] temp = st.split(",");
                String S; double p; long t;
                S = temp[0];
                p = Double.parseDouble(temp[2]);
                temp[1]=temp[1].substring(1,20);
                System.out.println(temp[1]);
                String k=temp[1];
                String str="2015-02-02 14:25:00";

                System.out.println(str);
                LocalDateTime ldtStart = LocalDateTime.parse(k .replace( " " , "T" ) ) ;
                long secs= ldtStart.getSecond();
                String x=String.valueOf(secs);
                t = Long.parseLong(x);
                temperatureReadings[i] = new TemperatureEvent(S,p,t);
                System.out.println(temperatureReadings[i]);
                temp = sh.split(" ");
                S = temp[0];
                p = Double.parseDouble(temp[1]);
                t = Long.parseLong(temp[2]);
                humidityReadings[i] = new HumidityEvent(S, p, t);
                System.out.println(humidityReadings[i]);
                temp = me.split(" ");
                int flg;
                S = temp[0];
                flg = Integer.parseInt(temp[1]);
                t = Long.parseLong(temp[2]);
                motionReadings[i] = new MotionEvent(S, flg, t);
                System.out.println(motionReadings[i]);
                suspected.sendEvent(temperatureReadings[i]);    // Suspected fire engine
                critical.sendEvent(temperatureReadings[i]);     // Critical fire engine
                temperatureup.sendEvent(temperatureReadings[i]);  // Increase temperature
                temperaturedown.sendEvent(temperatureReadings[i]);// Decrease temperature
                humidityup.sendEvent(humidityReadings[i]);   // Humidity engine
                humiditydown.sendEvent(humidityReadings[i]); // Humidity engine
                motionyes.sendEvent(motionReadings[i]);   // Motion detector
                motionno.sendEvent(motionReadings[i]);    // Motion detector
            }
            for(int i=0;i<8;i++) {
                String s = rfid.readLine();
                String[] temp = s.split(" ");
                String id = temp[0];
                Long t = Long.parseLong(temp[1]);
                int tag = Integer.parseInt(temp[2]);
                int flag = Integer.parseInt(temp[3]);
                rfidReadings[i] = new RFIDEvent(id,tag,flag,t);
                switch(i) {
                    case 0: intruderReadings[i] = new IntruderEvent(id, motionReadings[i].getFlag(), flag, tag, t);
                            break;
                    case 1: intruderReadings[i] = new IntruderEvent(id, motionReadings[i].getFlag(), flag, tag, t);
                            break;
                    case 2: intruderReadings[i] = new IntruderEvent(id, motionReadings[i].getFlag(), flag, tag, t);
                            break;
                    case 3:
                    case 4: intruderReadings[i] = new IntruderEvent(id, motionReadings[3].getFlag(), flag, tag, t);
                            break;
                    case 5:
                    case 6: intruderReadings[i] = new IntruderEvent(id, motionReadings[4].getFlag(), flag, tag, t);
                            break;
                }
                if(i<=6) {
                    System.out.println(intruderReadings[i]);
                    intruderengine.sendEvent(intruderReadings[i]);
                }
            }
            String[] temp = rg.readLine().split(" ");
            String S; int flag; long t;
            S = temp[0];
            flag = Integer.parseInt(temp[1]);
            t = Long.parseLong(temp[2]);
            RainGaugeEvent rge = new RainGaugeEvent(S, flag, t);
            System.out.println(rge);
            rain.sendEvent(rge);
            cnt++;
            sleep(2000);
        }
    }
    
}
