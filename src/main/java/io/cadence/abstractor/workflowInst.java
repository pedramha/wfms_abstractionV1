package io.cadence.abstractor;
import com.uber.cadence.client.WorkflowClient;
import com.uber.cadence.client.WorkflowOptions;

public class workflowInst {
    private static final String DOMAIN = "sample";

    public static void main (String[] args) throws Exception{
        WorkflowClient workflowClient=WorkflowClient.newInstance(DOMAIN);
        WorkflowOptions options=new WorkflowOptions.Builder().setWorkflowId("test").build();
        workflowClient.newWorkflowStub(RestService.class,options);
    }
}

