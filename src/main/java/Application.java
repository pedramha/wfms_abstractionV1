public class Application {
    public static void main(final String[] args) throws Exception {
        String host = "localhost";
        int port = 8181;
        Proxy.start(host, port);

/*         Undertow server = Undertow.builder()
                 .addHttpListener(port,host)
                 .setHandler(exchange ->
                         ClientAbstractor.initClient(exchange.getHostName(),exchange.getHostPort()))
                 .build();
        server.start();*/
    }
}



