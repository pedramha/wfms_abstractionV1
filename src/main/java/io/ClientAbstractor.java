package io;

import io.cadence.abstractor.CadenceAbstractor;
import io.zeebe.abstractor.ZeebeClientAbstractor;

public class ClientAbstractor {
    public static void initClient(String host, int Port) {
        boolean connectionSuccessful = false;

        //first try the client code of Cadence, if it doesn't work, go for Zeebe...

        try {
            CadenceAbstractor cadence = new CadenceAbstractor();
            cadence.clientInit(host, Port);
            connectionSuccessful = true;
        } catch (Exception e) {
        }

        if (connectionSuccessful == false) {
            try {
                ZeebeClientAbstractor zeebe = new ZeebeClientAbstractor();
                zeebe.clientInit(host, Port);
            } catch (Exception e) {
                System.out.println("Cannot connect to any server...");
            }

        }

/*
        //URL call incomplete
        ConductorAbstractor conductor = new ConductorAbstractor();
        conductor.clientInit(Host, Port);
        //conductor.clientInit("127.0.0.1:26500");*/
    }
}
