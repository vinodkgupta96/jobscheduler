package com.vdx.jobscheduler.service;

import com.vdx.jobscheduler.constant.JobType;
import com.vdx.jobscheduler.helper.JobOrderingHelper;
import com.vdx.jobscheduler.model.Job;
import com.vdx.jobscheduler.model.JobMetric;
import com.vdx.jobscheduler.service.impl.JobProcessingSystemImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

/**
 * @author vinod kumar
 */
@RunWith(MockitoJUnitRunner.class)
public class JobProcessingSystemImplTest {

    @InjectMocks
    private JobProcessingSystemImpl jobProcessingSystem;

    @Mock
    private JobMetric jobMetric;

    @Mock
    private JobOrderingHelper jobOrderingHelper;

    @Test
    public void test_JobSubmissionSuccessFull(){
        Job job = getJob();
        List<Job> jobs = Arrays.asList(job,job);
        Long futureTimeinMilli = System.currentTimeMillis()+ 10*1000;
        Mockito.doNothing().when(jobOrderingHelper).push(Mockito.any());
        Mockito.doNothing().when(jobMetric).updateJobReceivedCount();
        jobProcessingSystem.submit(job,futureTimeinMilli);
        Mockito.verify(jobOrderingHelper, Mockito.times(1)).push(Mockito.any());
        Mockito.verify(jobMetric, Mockito.times(1)).updateJobReceivedCount();
    }

    @Test(expected = Exception.class)
    public void test_JobSubmissionFailure(){
        Job job = getJob();
        List<Job> jobs = Arrays.asList(job,job);
        Long futureTimeinMilli = System.currentTimeMillis()+ 10*1000;
        Mockito.doThrow(new Exception("exception occurred")).when(jobOrderingHelper).push(Mockito.any());
        jobProcessingSystem.submit(job,futureTimeinMilli);
        Mockito.verify(jobOrderingHelper, Mockito.times(1)).push(Mockito.any());
        Mockito.verify(jobMetric, Mockito.times(0)).updateJobReceivedCount();
    }

    private Job getJob(){
        Job job = new Job();
        job.setId("1");
        job.setJobType(JobType.CUBE);
        job.setNum(2);
        return job;
    }


}
