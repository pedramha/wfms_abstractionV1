package io.io.netflix.abstractor;

import com.netflix.conductor.client.worker.Worker;
import com.netflix.conductor.common.metadata.tasks.Task;
import com.netflix.conductor.common.metadata.tasks.TaskResult;
import com.netflix.conductor.common.metadata.tasks.TaskResult.Status;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author Viren
 *
 */
public class SampleWorker implements Worker {

    private String taskDefName;

    public SampleWorker(String taskDefName) {
        this.taskDefName = taskDefName;
    }

    @Override
    public String getTaskDefName() {
        return taskDefName;
    }

    @Override
    public TaskResult execute(Task task) {
        String req=null;

        try {
            req=makeRequest("task.url");
        } catch (IOException e) {
            e.printStackTrace();
        }


        System.out.printf("Executing %s%n", taskDefName);
        TaskResult result = new TaskResult(task);
        result.setStatus(Status.COMPLETED);

        //Register the output of the task
        result.getOutputData().put("outputKey1", "value");
        result.getOutputData().put("oddEven", 1);
        result.getOutputData().put("mod", 4);
        //result.setOutputData(req);

        return result;
    }


    public String makeRequest(String url) throws IOException {
        java.net.URL url1 = new URL(url);
        URLConnection yc = url1.openConnection();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        yc.getInputStream()));
        String inputLine;
        StringBuffer results=new StringBuffer();
        while ((inputLine = in.readLine()) != null)
        {
            results.append(inputLine);
        }
        in.close();
        return  results.toString();
    }


}