/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cep;

import static cep.CEP.cepConfig;
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

class IntruderEngine {

    public EPRuntime run() throws InterruptedException {
        
        cepConfig.addEventType("IntruderEvent",IntruderEvent.class.getName());
        EPServiceProvider cep;
        cep = EPServiceProviderManager.getProvider("IntruderEngine", cepConfig);
        EPRuntime cepRT = cep.getEPRuntime();
        EPAdministrator cepAdm = cep.getEPAdministrator();
        EPStatement cepStatement = cepAdm.createEPL("select * from IntruderEvent\n" +
                                                    "match_recognize (\n" + 
                                                    "partition by sensorID\n"+
                                                    "measures A.sensorID as a_sensorID, A.timestamp as a_timestamp, A.motionFlag as a_motionFlag, "+
                                                    "A.rfidTag as a_rfidTag, A.rfidFlag as a_rfidFlag\n"+
                                                    "pattern (A)\n" +
                                                    "define\n" +
                                                    "A as (A.rfidFlag=1 and (A.rfidTag<0 or A.rfidTag>100))" +
                                                    ")");
        cepStatement.addListener(new CEPListener());
        
        return cepRT;
    }
    
    public static class CEPListener implements UpdateListener {
        public void update(EventBean[] newData, EventBean[] oldData) {
            
            Object o = newData[0].getUnderlying();
            System.out.println("INTRUDER Detected");
            try {
                new MessageScreen("INTRUDER Detected");
            } catch (InterruptedException ex) {
                Logger.getLogger(IntruderEngine.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
    
}
