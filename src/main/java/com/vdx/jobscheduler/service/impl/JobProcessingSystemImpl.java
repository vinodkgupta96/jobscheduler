package com.vdx.jobscheduler.service.impl;

import com.vdx.jobscheduler.helper.JobOrderingHelper;
import com.vdx.jobscheduler.model.Job;
import com.vdx.jobscheduler.model.JobMetric;
import com.vdx.jobscheduler.service.JobProcessingSystem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.concurrent.CompletableFuture;

/**
 * @author vinod kumar
 */
@Service
@Slf4j
public class JobProcessingSystemImpl implements JobProcessingSystem {

    @Autowired
    JobOrderingHelper jobOrderingHelper;

    @Autowired
    @Qualifier(value = "jobTaskExecutor")
    ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Autowired
    @Qualifier("jobMetric")
    private JobMetric jobMetric;

    @Override
    public void submit(Job job, long futureExecutionTimeInMillis) {
        job.setScheduledTime(futureExecutionTimeInMillis);
        jobOrderingHelper.push(job);
        jobMetric.updateJobReceivedCount();
    }

    @Override
    public void runEligibleJobs() {
        List<Job> jobs = jobOrderingHelper.getApplicableJobs();
        jobs.stream().forEach(job -> {
            threadPoolTaskExecutor.execute(()->{
                try{
                    job.setActualStartTime(System.currentTimeMillis());
                    job.run();
                    jobMetric.updateTotalProcessingTime(System.currentTimeMillis()-job.getActualStartTime());
                    jobMetric.updateSuccessCount();
                }catch (Exception ex){
                    log.error("Error occurred while running job with error :{} ",ex.getMessage());
                    jobMetric.updateFailedCount();
                }
            });
        });
    }


}
