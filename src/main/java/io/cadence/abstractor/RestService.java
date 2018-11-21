package io.cadence.abstractor;
import com.uber.cadence.activity.ActivityMethod;
import com.uber.cadence.worker.Worker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.ExecutionException;

public class RestService {


    static final String TASK_LIST = "RestService";
    private static final String DOMAIN = "sample";

    public interface CallRestEndpoint {
        @ActivityMethod
        String makeRequest(String URL) throws IOException;
    }

    public static class CallRestEndpointImpl implements CallRestEndpoint {

        public String makeRequest(String url) throws IOException {
            java.net.URL url1 = new URL(url);
            URLConnection yc = url1.openConnection();
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
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //avoid getting the domain and TASK_LIST here-but rather the IP of the server
        Worker.Factory factory = new Worker.Factory(DOMAIN);
        Worker worker = factory.newWorker(TASK_LIST);
        //avoid writing workflow related code here if possible
        worker.registerActivitiesImplementations(new CallRestEndpointImpl());
        factory.start();
    }



}

