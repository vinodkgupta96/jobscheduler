package com.vdx.jobscheduler.service;

import com.vdx.jobscheduler.model.Job;
import java.util.*;
/**
 * @author vinod kumar
 */
public interface JobProcessingSystem {
    public void submit(Job job, long futureExecutionTimeInMillis);
    public void runEligibleJobs();
}

