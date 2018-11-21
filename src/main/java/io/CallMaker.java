package io;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class CallMaker {
    public static String callURL(String url) throws Exception {
        URL _url = new URL(url);
        URLConnection yc = _url.openConnection();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        yc.getInputStream()));
        String inputLine;
        StringBuffer results=new StringBuffer();

        while ((inputLine = in.readLine()) != null)
        {
            results.append(inputLine);
        }
        in.close();
        return  results.toString();
    }
}
