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
import java.util.logging.Level;
import java.util.logging.Logger;

public class RainDetected {
    
    public EPRuntime run() throws InterruptedException {

        cepConfig.addEventType("RainGaugeEvent",RainGaugeEvent.class.getName());
        EPServiceProvider cep;
        cep = EPServiceProviderManager.getProvider("RainDetectedEngine", cepConfig);
        EPRuntime cepRT = cep.getEPRuntime();
        EPAdministrator cepAdm = cep.getEPAdministrator();
        EPStatement cepStatement = cepAdm.createEPL("select * from RainGaugeEvent\n" +
                                                    "match_recognize (\n" +
                                                    "partition by sensorID\n" + 
                                                    "measures A.sensorID as a_sensorID, A.flag as a_flag, A.trans as a_trans\n"+
                                                    "pattern (A)\n" +
                                                    "define\n" +
                                                    "A as A.flag=1 and A.trans=1\n" +
                                                    ")");
        cepStatement.addListener(new CEPListener());
        
        return cepRT;
    }
    
    public static class CEPListener implements UpdateListener {
        public void update(EventBean[] newData, EventBean[] oldData) {
            
            Object o = newData[0].getUnderlying();
            System.out.println("Rainfall Detected");
            try {
                new MessageScreen("Rainfall Detected");
            } catch (InterruptedException ex) {
                Logger.getLogger(RainDetected.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
    
}
