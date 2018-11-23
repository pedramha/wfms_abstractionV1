package io.cadence.abstractor;

import com.uber.cadence.activity.ActivityMethod;
import com.uber.cadence.worker.Worker;
import io.CallMaker;

public class RestActivity {
    /*public static final String DOMAIN = "sample";
    static final String TASK_LIST = "RestActivity";*/

    public void workerInit(String Host, int Port, String Domain, String Task_list) {
        // Start a worker that hosts both workflow and activity implementations.
        Worker.Factory factory = new Worker.Factory(Host, Port, Domain);
        Worker worker = factory.newWorker(Task_list);
        // Workflows are stateful. So you need a type to create instances.
        worker.registerWorkflowImplementationTypes(WorkflowInst.RESTWorkflowImpl.class);
        // Activities are stateless and thread safe. So a shared instance is used.
        worker.registerActivitiesImplementations(new RESTActivitiesImpl());
        // Start listening to the workflow and activity task lists.
        factory.start();
        /*// Start a workflow execution. Usually this is done from another program.
        WorkflowClient workflowClient = WorkflowClient.newInstance(DOMAIN);
        // Get a workflow stub using the same task list the worker uses.
        RESTWorkflow workflow = workflowClient.newWorkflowStub(RESTWorkflow.class);
        // Execute a workflow waiting for it to complete.
        String REST = workflow.getREST("Blahblah");*/

/*        WorkflowInst wfTest=new WorkflowInst();

        System.out.println(wfTest.wfInit());
        System.exit(0);*/
    }

    public interface RESTActivities {
        @ActivityMethod(scheduleToCloseTimeoutSeconds = 2)
        String composeREST(String url);
    }

    static class RESTActivitiesImpl implements RESTActivities {
        @Override
        public String composeREST(String url) {
            String response = "Error";
            try {
                response = CallMaker.callURL(url);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return response;
        }
    }
}
