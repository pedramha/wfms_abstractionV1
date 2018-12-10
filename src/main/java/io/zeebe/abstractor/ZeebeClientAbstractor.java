package io.zeebe.abstractor;

import io.CallMaker;
import io.zeebe.client.ZeebeClient;
import io.zeebe.client.api.events.DeploymentEvent;
import io.zeebe.client.api.events.WorkflowInstanceEvent;
import io.zeebe.client.api.subscription.JobWorker;
import org.json.JSONObject;

import java.util.Map;

public class ZeebeClientAbstractor {

    public void clientInit(String BrokerAddress, int Port)
    {
        final ZeebeClient client = ZeebeClient.newClientBuilder()
                .brokerContactPoint(BrokerAddress + ":" + Port)
                //.brokerContactPoint("127.0.0.1:26500")
                .build();

        do_WorkflowLogic(client);

        final JobWorker jobWorker = client.jobClient()
                .newWorker()
                .jobType("http")
                .handler((jobClient, job) ->
                {
                    final Map<String, Object> headers = job.getCustomHeaders();
                    final String method = (String) headers.get("method");
                    String url=headers.get("url").toString();
                    String wfpayload = job.getPayload();//get the payload of the worklfow
                    String response=null;
                    try {
                        response=CallMaker.callURL(url);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    JSONObject serializedResp = new JSONObject(response);

                    jobClient.newCompleteCommand(job.getKey())
                            //.payload(serializedResp)
                            .send()
                            .join();
                })
                .open();
    }

    private static void do_WorkflowLogic(ZeebeClient client) {
        final DeploymentEvent deployment = client.workflowClient()
                .newDeployCommand()
                .addResourceFromClasspath("http-get.bpmn")
                .send()
                .join();
        final long wfKey = deployment.getWorkflows().get(0).getWorkflowKey();
        final int version = deployment.getWorkflows().get(0).getVersion();

        final WorkflowInstanceEvent wfInstance = client.workflowClient()
                .newCreateInstanceCommand()
                .workflowKey(wfKey)
                .send()
                .join();
        final long workflowInstanceKey = wfInstance.getWorkflowInstanceKey();
    }

}
