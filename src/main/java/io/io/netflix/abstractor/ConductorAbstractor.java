//package io.io.netflix.abstractor;
//
//
//import com.netflix.conductor.client.http.TaskClient;
//import com.netflix.conductor.client.task.WorkflowTaskCoordinator;
//import com.netflix.conductor.client.worker.Worker;
//import com.netflix.conductor.common.metadata.tasks.Task;
//import io.CallMaker;
//
//import java.util.Map;
//
//public class ConductorAbstractor {
//
//    public void clientInit(String BrokerAddress, int Port) throws Exception {
//
//        TaskClient taskClient = new TaskClient();
//        taskClient.setRootURI(BrokerAddress + ":" + Port);
//        //taskClient.setRootURI("http://localhost:8080/api/");		//Point this to the server API
//
//        int threadCount = 1;			//number of threads used to execute workers.  To avoid starvation, should be same or more than number of workers
//
//        Worker worker1 = new SampleWorker("task_1");
//        //Worker worker2 = new SampleWorker("task_5");
//        //Create WorkflowTaskCoordinator
//        WorkflowTaskCoordinator.Builder builder = new WorkflowTaskCoordinator.Builder();
//        WorkflowTaskCoordinator coordinator = builder.withWorkers(worker1).withThreadCount(threadCount).withTaskClient(taskClient).build();
//        //Start for polling and execution of the tasks
//        coordinator.init();
//        //test
//        Task task = taskClient.pollTask("http",worker1.getIdentity(),"");
//        Map<String, Object> inputData = task.getInputData();
//        CallMaker.callURL(inputData.get("URL").toString());
//        //result of prev step
//        //task.setOutputData();
//    }
//
//
//}
