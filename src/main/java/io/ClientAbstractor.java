package io;

import io.cadence.abstractor.CadenceAbstractor;
import io.io.netflix.abstractor.ConductorAbstractor;
import io.zeebe.abstractor.ZeebeClientAbstractor;

public class ClientAbstractor {
    public static void initClient(String Host, int Port) throws Exception {

        //Bool connectionSucceed
        ZeebeClientAbstractor zeebe = new ZeebeClientAbstractor();
        zeebe.clientInit(Host, Port);
        /*zeebe.clientInit("127.0.0.1:26500");*/
        //connectionSucceed==false go to next

        //URL call incomplete
        ConductorAbstractor conductor = new ConductorAbstractor();
        conductor.clientInit(Host, Port);
        //conductor.clientInit("127.0.0.1:26500");

        //URL call incomplete
        CadenceAbstractor cadence = new CadenceAbstractor();
        cadence.clientInit(Host, Port);
        //cadence.clientInit("127.0.0.1",26500,"test");
    }
}
