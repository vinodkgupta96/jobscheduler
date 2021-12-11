package com.vdx.jobscheduler.helper;

import com.vdx.jobscheduler.model.Job;
import com.vdx.jobscheduler.model.JobMetric;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author vinod kumar
 */
@Component
public class JobOrderingHelper {
    private PriorityQueue<Job> queue= new PriorityQueue<>(
            (job1, job2) -> (int) (job1.getScheduledTime()-job2.getScheduledTime()));

    public List<Job> getApplicableJobs(){
        long currentTimeInMilliSec = System.currentTimeMillis();
        List<Job> applicableJobs =  new ArrayList<>();
        while (!queue.isEmpty()){
            if(queue.peek().getScheduledTime() <= currentTimeInMilliSec){
                applicableJobs.add(queue.poll());
            }
        }
        return applicableJobs;
    }

    public void push(Job job){
        queue.add(job);
    }
}
