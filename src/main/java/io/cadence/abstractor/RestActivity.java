package io.cadence.abstractor;

import com.uber.cadence.activity.ActivityMethod;
import com.uber.cadence.worker.Worker;
import io.CallMaker;

public class RestActivity {

    public void workerInit(String host, int Port, String DOMAIN, String TASK_LIST) {
        Worker.Factory factory =
                new Worker.Factory(host, Port, DOMAIN);
        Worker worker = factory.newWorker(TASK_LIST);
        worker.registerWorkflowImplementationTypes(WorkflowInst.RESTWorkflowImpl.class);
        worker.registerActivitiesImplementations(new RESTActivitiesImpl());
        factory.start();
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
