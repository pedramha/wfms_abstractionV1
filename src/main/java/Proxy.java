import io.ClientAbstractor;
import io.undertow.Undertow;

public class Proxy {
    public static void start(String host, int port) {
        Undertow server = Undertow.builder()
                .addHttpListener(port, host)
                .setHandler(exchange ->
                        ClientAbstractor.initClient(exchange.getHostName(), exchange.getHostPort()))
                .build();
        server.start();
    }
}
