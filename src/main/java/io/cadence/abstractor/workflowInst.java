package io.cadence.abstractor;
import com.uber.cadence.client.WorkflowClient;
import com.uber.cadence.client.WorkflowOptions;

public class workflowInst {
    private static final String DOMAIN = "sample";

    public void cadenceWorkflowInst (String Domain) throws Exception{
        WorkflowClient workflowClient=WorkflowClient.newInstance(Domain);
        WorkflowOptions options=new WorkflowOptions.Builder().build();
        //WorkflowOptions options=new WorkflowOptions.Builder().setWorkflowId("test").build();
        workflowClient.newWorkflowStub(CadenceAbstractor.class,options);
    }
}

