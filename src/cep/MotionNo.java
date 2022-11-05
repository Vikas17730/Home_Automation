/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cep;

import static cep.CEP.cepConfig;
import static cep.MotionYes.lastMotion;
import static cep.MotionYes.lightsOn;
import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MotionNo {
    
    public EPRuntime run() throws InterruptedException {
        
        cepConfig.getEngineDefaults().getThreading().setInternalTimerEnabled(false);
        cepConfig.addEventType("MotionEvent",MotionEvent.class.getName());
        EPServiceProvider cep;
        cep = EPServiceProviderManager.getProvider("MotionNoEngine", cepConfig);
        EPRuntime cepRT = cep.getEPRuntime();
        EPAdministrator cepAdm = cep.getEPAdministrator();
        EPStatement cepStatement = cepAdm.createEPL("select * from MotionEvent.win:length(1)\n" +
                                                    "match_recognize (\n" +
                                                    "partition by sensorID\n"+
                                                    "measures A.sensorID as a_sensorID, A.timestamp as a_timestamp, A.flag as a_flag\n"+
                                                    "pattern (A)\n" +
                                                    "define\n" +
                                                    "A as (A.flag=0) " +
                                                    ")");
        cepStatement.addListener(new CEPListener());
        
        return cepRT;
    }
    
    public static class CEPListener implements UpdateListener {
        public void update(EventBean[] newData, EventBean[] oldData) {
            
            System.out.println("No motion detected");
            long curTime = (long) newData[0].get("a_timestamp");
            if(lightsOn.get((String)(newData[0].get("a_sensorID")))==1 && curTime - lastMotion.get((String)(newData[0].get("a_sensorID"))) >= 300000) {
                System.out.println("CLOSE LIGHTS at " +newData[0].get("a_sensorID"));
                try {
                    new MessageScreen("Closing the Lights at " +newData[0].get("a_sensorID"));
                } catch (InterruptedException ex) {
                    Logger.getLogger(MotionNo.class.getName()).log(Level.SEVERE, null, ex);
                }
                lightsOn.put((String)(newData[0].get("a_sensorID")),0);
            }
        }
    }
    
}
