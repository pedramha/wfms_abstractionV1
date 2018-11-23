package io.cadence.abstractor;

public class CadenceAbstractor {
    private String Domain;
    private String Task_List;

    public void clientInit(String ServiceAddres, String Host, int Port) {
        RestActivity restActivity = new RestActivity();
        restActivity.workerInit(Host, Port, Domain, Task_List);
        WorkflowInst workflowInst = new WorkflowInst();
        workflowInst.wfInit(ServiceAddres, Domain, Host, Port);
    }

    public void setDomain(String domain) {
        Domain = domain;
    }

    public void setTask_List(String task_List) {
        Task_List = task_List;
    }
}