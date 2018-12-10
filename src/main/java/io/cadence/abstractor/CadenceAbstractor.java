package io.cadence.abstractor;

public class CadenceAbstractor {
    public static final String DOMAIN = "sample";
    static final String TASK_LIST = "RestActivity";

    public void clientInit(String host, int Port) {
        RestActivity restActivity = new RestActivity();
        restActivity.workerInit(host, Port, DOMAIN, TASK_LIST);
        WorkflowInst workflowInst = new WorkflowInst();

        //The URL of a random service to be called.


        workflowInst.wfInit(host, Port, DOMAIN);
    }

/*    public void setDomain(String domain) {
        Domain = domain;
    }

    public void setTask_List(String task_List) {
        Task_List = task_List;
    }*/
}