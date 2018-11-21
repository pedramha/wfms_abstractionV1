package io.zeebe.abstractor;
import io.zeebe.client.ZeebeClient;
import io.zeebe.client.api.clients.JobClient;
import io.zeebe.client.api.events.DeploymentEvent;
import io.zeebe.client.api.events.WorkflowInstanceEvent;
import io.zeebe.client.api.response.ActivatedJob;
import io.zeebe.client.api.subscription.JobHandler;
import io.zeebe.client.api.subscription.JobWorker;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

public class Application {

    public static void main(String[] args)
    {
        final ZeebeClient client = ZeebeClient.newClientBuilder()
                .brokerContactPoint("127.0.0.1:26500")
                .build();

        final DeploymentEvent deployment = client.workflowClient()
                .newDeployCommand()
                .addResourceFromClasspath("http-get.bpmn")
                //.addResourceFromClasspath("demoProcess.bpmn")
                .send()
                .join();

        final long wfKey = deployment.getWorkflows().get(0).getWorkflowKey();
        final int version = deployment.getWorkflows().get(0).getVersion();
        System.out.println("Workflow deployed. Version: " + version);

        final WorkflowInstanceEvent wfInstance = client.workflowClient()
                .newCreateInstanceCommand()
                .workflowKey(wfKey)
                //.bpmnProcessId("http-example.get")
                //.bpmnProcessId("demoProcess")
                //.latestVersion()
                .send()
                .join();

        final long workflowInstanceKey = wfInstance.getWorkflowInstanceKey();

        System.out.println("Workflow instance created. Key: " + workflowInstanceKey);


        // after the workflow instance is created
        final JobWorker jobWorker = client.jobClient()
                .newWorker()
                .jobType("http")
                //.jobType("bar")
                .handler((jobClient, job) ->
                {

                    final Map<String, Object> headers = job.getCustomHeaders();

                    final String method = (String) headers.get("method");
                    String ur=headers.get("url").toString();
                    String response=null;
                    URLConnectionReader urlConnectionReader=new URLConnectionReader();
                    try {
                        response= urlConnectionReader.callURL(ur);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }



                    //JSONObject serializedResp=new JSONObject(response);
                    jobClient.newCompleteCommand(job.getKey())
                            .payload(headers)
                            .send()
                            .join();
/*                    jobClient.newCompleteCommand(job.getKey())
                            .send()
                            .join();*/
                })
                .open();
    }
    private static class ExampleJobHandler implements JobHandler {
        @Override
        public void handle(final JobClient client, final ActivatedJob job) {
            // here: business logic that is executed with every job

            System.out.println(
                    String.format(
                            "[type: %s, key: %s, lockExpirationTime: %s]\n[headers: %s]\n[payload: %s]\n===",
                            job.getType(),
                            job.getKey(),
                            job.getDeadline().toString(),
                            job.getHeaders(),
                            job.getPayload()));

            client.newCompleteCommand(job.getKey()).send().join();
        }
    }


    public static class URLConnectionReader {
        public String callURL(String url) throws Exception {
            URL url1 = new URL(url);
            URLConnection yc = url1.openConnection();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            yc.getInputStream()));
            String inputLine;
            StringBuffer results=new StringBuffer();

            while ((inputLine = in.readLine()) != null)

            {
                //System.out.println(inputLine);
                results.append(inputLine);
            }
            in.close();
            JSONObject jsonObject=new JSONObject(results);
            return  results.toString();
        }
    }

}