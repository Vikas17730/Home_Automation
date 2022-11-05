/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cep;

import static cep.CEP.cepConfig;
import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MotionYes {
    
    static HashMap<String,Long> lastMotion = new HashMap<String,Long>();
    static HashMap<String,Integer> lightsOn = new HashMap<String,Integer>();
    
    public EPRuntime run() throws InterruptedException {
        
        cepConfig.addEventType("MotionEvent",MotionEvent.class.getName());
        EPServiceProvider cep;
        cep = EPServiceProviderManager.getProvider("MotionYesEngine", cepConfig);
        EPRuntime cepRT = cep.getEPRuntime();
        EPAdministrator cepAdm = cep.getEPAdministrator();
        EPStatement cepStatement = cepAdm.createEPL("select * from MotionEvent\n" +
                                                    "match_recognize (\n" + 
                                                    "partition by sensorID\n"+
                                                    "measures A.sensorID as a_sensorID, A.timestamp as a_timestamp, A.flag as a_flag\n"+
                                                    "pattern (A)\n" +
                                                    "define\n" +
                                                    "A as (A.flag=1) " +
                                                    ")");
        cepStatement.addListener(new CEPListener());
        
        return cepRT;
    }
    
    public static class CEPListener implements UpdateListener {
        public void update(EventBean[] newData, EventBean[] oldData) {
            
            lastMotion.put((String)(newData[0].get("a_sensorID")), (Long)(newData[0].get("a_timestamp")));
            System.out.println("Motion detected");
            if(lightsOn.containsKey((String)(newData[0].get("a_sensorID")))) {
                if(lightsOn.get((String)(newData[0].get("a_sensorID")))==0) {
                    System.out.println("Switching ON the Lights at " +newData[0].get("a_sensorID"));
                    try {
                        new MessageScreen("Switching ON the Lights at " +newData[0].get("a_sensorID"));
                    } catch (InterruptedException ex) {
                        Logger.getLogger(MotionYes.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    lightsOn.put((String)(newData[0].get("a_sensorID")),1);
                }
            }
            else {
                System.out.println("Switch on Lights at " +newData[0].get("a_sensorID"));
                try {
                    new MessageScreen("Switching ON the Lights at " +newData[0].get("a_sensorID"));
                } catch (InterruptedException ex) {
                    Logger.getLogger(MotionYes.class.getName()).log(Level.SEVERE, null, ex);
                }
                lightsOn.put((String)(newData[0].get("a_sensorID")),1);
            }
            
        }
    }
    
    
}
