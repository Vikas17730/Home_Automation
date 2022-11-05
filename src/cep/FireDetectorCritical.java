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
import static java.lang.Thread.sleep;
import static java.lang.Thread.sleep;
import static java.lang.Thread.sleep;
import static java.lang.Thread.sleep;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FireDetectorCritical {
    
    public EPRuntime run() throws InterruptedException {
        
        cepConfig.addEventType("TemperatureEvent",TemperatureEvent.class.getName());
        EPServiceProvider cep;
        cep = EPServiceProviderManager.getProvider("FireCriticalEngine", cepConfig);
        EPRuntime cepRT = cep.getEPRuntime();
        EPAdministrator cepAdm = cep.getEPAdministrator();
        EPStatement cepStatement = cepAdm.createEPL("select * from pattern @SuppressOverlappingMatches [every a=TemperatureEvent -> b=TemperatureEvent -> c=TemperatureEvent].win:length(15) "+
                                                    " where a.temperature>30 and b.temperature>3+a.temperature and c.temperature>3+b.temperature"+
                                                    " and a.sensorID=b.sensorID and c.sensorID=b.sensorID"
                                                    );
        cepStatement.addListener(new CEPListener());
        
        return cepRT;
    }
    
    public static class CEPListener implements UpdateListener {
        public void update(EventBean[] newData, EventBean[] oldData) {
            
            Object o = newData[0].getUnderlying();
            System.out.println("CRITICAL FIRE situation at "+newData[0].get("a_sensorID"));
            try {
                new MessageScreen("CRITICAL FIRE situation at "+newData[0].get("a_sensorID"));
                //new SendSMS("+918765829573","A critical fire situation is detected! Please look into it!");
                //new SendEmail("iit2014504@iiita.ac.in","Critical fire situation!","A critical situation of fire has been detected. Please look into the matter.");
            } catch (InterruptedException ex) {
                Logger.getLogger(FireDetectorCritical.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
    
}
