package io.cadence.abstractor;

import com.uber.cadence.client.WorkflowClient;
import com.uber.cadence.workflow.Workflow;
import com.uber.cadence.workflow.WorkflowMethod;

public class WorkflowInst {

    static final String TASK_LIST = "RestActivity";

    public String wfInit(String ServiceAddres, String Domain, String Host, int Port) {
        // Start a workflow execution. Usually this is done from another program.
        WorkflowClient workflowClient = WorkflowClient.newInstance(Host, Port, Domain);
        // Get a workflow stub using the same task list the worker uses.
        RESTWorkflow workflow = workflowClient.newWorkflowStub(RESTWorkflow.class);
        // Execute a workflow waiting for it to complete.
        String REST = workflow.getREST(ServiceAddres);
        return REST;
    }


    public interface RESTWorkflow {
        /**
         * @return REST string
         */
        @WorkflowMethod(executionStartToCloseTimeoutSeconds = 10, taskList = TASK_LIST)
        String getREST(String url);
    }

    /**
     * RESTWorkflow implementation that calls RESTsActivities#composeREST.
     */
    public static class RESTWorkflowImpl implements RESTWorkflow {
        /**
         * Activity stub implements activity interface and proxies calls to it to Cadence activity
         * invocations. Because activities are reentrant, only a single stub can be used for multiple
         * activity invocations.
         */
        private final RestActivity.RESTActivities activities =
                Workflow.newActivityStub(RestActivity.RESTActivities.class);

        @Override
        public String getREST(String url) {
            // This is a blocking call that returns only after the activity has completed.
            return activities.composeREST(url);
        }
    }

}
