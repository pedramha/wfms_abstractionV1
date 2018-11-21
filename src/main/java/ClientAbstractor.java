import io.cadence.abstractor.CadenceAbstractor;
import io.io.netflix.abstractor.ConductorAbstractor;
import io.zeebe.abstractor.ZeebeClientAbstractor;

public class ClientAbstractor {
    public static void initClient() throws Exception {
        ZeebeClientAbstractor zeebe=new ZeebeClientAbstractor();
        zeebe.clientInit("127.0.0.1:26500");

        ConductorAbstractor conductor=new ConductorAbstractor();
        conductor.clientInit("127.0.0.1:26500");

        CadenceAbstractor cadence=new CadenceAbstractor();
        cadence.clientInit("127.0.0.1",26500,"test");
    }
}
