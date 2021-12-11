package com.vdx.jobscheduler.controller;

import com.vdx.jobscheduler.model.Job;
import com.vdx.jobscheduler.service.impl.JobProcessingSystemImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author vinod kumar
 */
@Slf4j
@RestController("/job")
public class JobController {

    @Autowired
    private JobProcessingSystemImpl jobProcessingSystem;

    @PostMapping("/submit")
    public ResponseEntity<String> submit(@RequestBody Job job, @RequestParam long futureExecutionTimeInMillis ){
        try {
            jobProcessingSystem.submit(job, futureExecutionTimeInMillis);
        }catch (Exception ex){
            log.error("Error occurred while submitting job with error : {}",ex.getMessage());
            return new ResponseEntity<>("Job submitted failed.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("Job submitted successfully.", HttpStatus.OK);
    }

    @GetMapping("/recon")
    public ResponseEntity<String> recon(){
        try {
            jobProcessingSystem.runEligibleJobs();
        }catch (Exception ex){
            return new ResponseEntity<>("Job recon successfully.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("Job recon successfully.", HttpStatus.OK);
    }
}
