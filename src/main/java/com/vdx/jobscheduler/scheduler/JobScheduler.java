package com.vdx.jobscheduler.scheduler;

import com.vdx.jobscheduler.model.Job;
import com.vdx.jobscheduler.service.MetricService;
import com.vdx.jobscheduler.service.impl.JobProcessingSystemImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.util.*;
/**
 * @author vinod kumar
 */
@Service
@Slf4j
public class JobScheduler {

    @Autowired
    private JobProcessingSystemImpl jobProcessingSystem;

    @Autowired
    private MetricService metricService;

    @Async(value = "jobSchedulerTaskExecutor")
    @Scheduled(fixedRateString = "${scheduler.interval.milli}")
    public void scheduleJob(){
        try {
            jobProcessingSystem.runEligibleJobs();
            System.out.println("scheduler fired started");
        }catch (Exception ex){
            log.error("Error occurred at time : {} with error : {}", System.currentTimeMillis(),ex.getMessage());
        }
    }

    @Async(value = "jobSchedulerTaskExecutor")
    @Scheduled(fixedRateString = "${scheduler.metric.flush.interval.milli}")
    public void scheduleMetricFlush(){
        try {
            metricService.flushMetric();
        }catch (Exception ex){
            log.error("Error occurred at time : {} with error : {}", System.currentTimeMillis(),ex.getMessage());
        }
    }

}
