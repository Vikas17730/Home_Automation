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
import java.util.logging.Level;
import java.util.logging.Logger;

public class FireDetectorSuspected {
    
    public EPRuntime run() throws InterruptedException {
        
        cepConfig.addEventType("TemperatureEvent",TemperatureEvent.class.getName());
        EPServiceProvider cep;
        cep = EPServiceProviderManager.getProvider("FireSuspectedEngine", cepConfig);
        EPRuntime cepRT = cep.getEPRuntime();
        EPAdministrator cepAdm = cep.getEPAdministrator();
        EPStatement cepStatement = cepAdm.createEPL("select * from TemperatureEvent.win:length(30)"+//.win:expr(oldest_timestamp > newest_timestamp - 2000)\n" +
                                                    "match_recognize (\n" +
                                                    "partition by sensorID\n" + 
                                                    "measures A.sensorID as a_sensorID, A.temperature as a_temperature, "+
                                                    "B.sensorID as b_sensorID, B.temperature as b_temperature\n"+
                                                    "pattern (A B)\n" +
                                                    "define\n" +
                                                    "A as A.temperature > 65,\n" +
                                                    "B as B.temperature > 65\n"+
                                                    ")");
        cepStatement.addListener(new CEPListener());
        
        return cepRT;
    }
    
    public static class CEPListener implements UpdateListener {
        public void update(EventBean[] newData, EventBean[] oldData) {
            
            Object o = newData[0].getUnderlying();
            System.out.println("SUSPECTED FIRE situation at "+newData[0].get("a_sensorID"));
            try {
                new MessageScreen("SUSPECTED FIRE situation at "+newData[0].get("a_sensorID"));
                sleep(5000);
                //new SendSMS("+918765829573","A fire situation is suspected! Please look into it!");
                //new SendEmSendail("iit2014504@iiita.ac.in","Suspected fire situation!","A situation of fire is suspected. Please look into the matter.");
            } catch (InterruptedException ex) {
                Logger.getLogger(FireDetectorSuspected.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
    
}
