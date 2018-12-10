package io.cadence.abstractor;

import com.uber.cadence.client.WorkflowClient;
import com.uber.cadence.workflow.Workflow;
import com.uber.cadence.workflow.WorkflowMethod;

import static io.cadence.abstractor.CadenceAbstractor.TASK_LIST;

public class WorkflowInst {


    public String wfInit(String host, int Port, String Domain) {


        WorkflowClient workflowClient = WorkflowClient.newInstance(host, Port, Domain);
        // Get a workflow stub using the same task list the worker uses.
        RESTWorkflow workflow = workflowClient.newWorkflowStub(RESTWorkflow.class);
        // Execute a workflow waiting for it to complete.
        //the URL of the service is indicated
        String serviceURL = "https://jsonplaceholder.typicode.com/posts/1";
        String response = workflow.getREST(serviceURL);
        return response;
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
        private final RestActivity.RESTActivities activities =
                Workflow.newActivityStub(RestActivity.RESTActivities.class);

        @Override
        public String getREST(String url) {
            // This is a blocking call that returns only after the activity has completed.
            return activities.composeREST(url);
        }
    }

}
