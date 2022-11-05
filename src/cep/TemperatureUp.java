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

public class TemperatureUp {
    
    public EPRuntime run() throws InterruptedException {

        cepConfig.addEventType("TemperatureEvent",TemperatureEvent.class.getName());
        EPServiceProvider cep;
        cep = EPServiceProviderManager.getProvider("TemperatureUpEngine", cepConfig);
        EPRuntime cepRT = cep.getEPRuntime();
        EPAdministrator cepAdm = cep.getEPAdministrator();
        EPStatement cepStatement = cepAdm.createEPL("select * from TemperatureEvent\n" +
                                                    "match_recognize (\n" + 
                                                    "partition by sensorID\n"+
                                                    "measures A.sensorID as a_sensorID, A.temperature as a_temperature, "+
                                                    "A.temperatureExpected as a_temperatureExpected\n"+
                                                    "pattern (A)\n" +
                                                    "define\n" +
                                                    "A as (A.temperature-A.temperatureExpected<-10)\n"+
                                                    ")");
        cepStatement.addListener(new CEPListener());
        
        return cepRT;
    }
    
    public static class CEPListener implements UpdateListener {
        public void update(EventBean[] newData, EventBean[] oldData) {
            
            Object o = newData[0].getUnderlying();
            System.out.println("Temperature Low at "+newData[0].get("a_sensorID")+". Switching off AC.");
            try {
                new MessageScreen("Temperature Low at "+newData[0].get("a_sensorID")+". Switching off AC.");
            } catch (InterruptedException ex) {
                Logger.getLogger(TemperatureUp.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
    
}
