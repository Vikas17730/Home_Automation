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

public class HumidityDown {
    
    public EPRuntime run() throws InterruptedException {
        
        cepConfig.addEventType("HumidityEvent",HumidityEvent.class.getName());
        EPServiceProvider cep;
        cep = EPServiceProviderManager.getProvider("HumidityDownEngine", cepConfig);
        EPRuntime cepRT = cep.getEPRuntime();
        EPAdministrator cepAdm = cep.getEPAdministrator();
        EPStatement cepStatement = cepAdm.createEPL("select * from HumidityEvent\n" +
                                                    "match_recognize (\n" + 
                                                    "partition by sensorID\n"+
                                                    "measures A.sensorID as a_sensorID, A.humidity as a_humidity, A.humidityExpected as a_humidityExpected\n"+
                                                    "pattern (A)\n" +
                                                    "define\n" +
                                                    "A as (A.humidity-A.humidityExpected)>=10\n" +
                                                    ")");
        cepStatement.addListener(new CEPListener());
        
        return cepRT;
    }
    
    public static class CEPListener implements UpdateListener {
        public void update(EventBean[] newData, EventBean[] oldData) {
            
            Object o = newData[0].getUnderlying();
            System.out.println("Humidity high. Adjusting it down.");
            try {
                new MessageScreen("Humidity high "+newData[0].get("a_sensorID")+". Adjusting it down.");
            } catch (InterruptedException ex) {
                Logger.getLogger(HumidityDown.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
    
}
