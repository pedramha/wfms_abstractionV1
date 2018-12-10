import io.ClientAbstractor;

import java.util.Scanner;

public class Application {
    public static void main(final String[] args) throws Exception {

        //for zeebe 127.0.0.1:26500
        //for cadence 127.0.0.1:7933

        String input = getServerName();
        String[] parts = input.split(":");
        String ip = parts[0];
        String port = parts[1];
        ClientAbstractor.initClient(ip, Integer.parseInt(port));

    }

    private static String getServerName() {
        System.out.println("Enter the IP and the port of the server(e.g. 127.0.0.1:7933) you want to connect to:");
        Scanner scanner = new Scanner(System.in);
        String serverAdd = scanner.nextLine();
        return serverAdd;
    }
}



