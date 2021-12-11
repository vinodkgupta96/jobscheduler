package com.vdx.jobscheduler.model;

import com.vdx.jobscheduler.constant.JobType;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author vinod kumar
 */
@Data
@Slf4j
public class Job {
    private String id;
    private JobType jobType;
    private Integer num;
    private Long scheduledTime;
    private Long actualStartTime;
    public void run(){
        if(JobType.SQUARE == jobType){
            log.info("Square of num : {} is = {}",num,num*num);
        }else if(JobType.CUBE == jobType){
            log.info("Cube of num : {} is = {}",num,num*num*num);
        }else{
            log.error("Invalid input");
        }
    }

}
