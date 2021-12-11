package com.vdx.jobscheduler.model;

import lombok.Data;
import lombok.Synchronized;

/**
 * @author vinod kumar
 */
@Data
public class JobMetric {
    private long jobReceivedCount;
    private long totalProcessingTime;
    private long failedCount;
    private long successCount;

    @Synchronized
    public void updateJobReceivedCount() {
        this.jobReceivedCount += 1;
    }
    @Synchronized
    public void updateTotalProcessingTime(double processingTime) {
        this.totalProcessingTime+= processingTime;
    }

    @Synchronized
    public void updateFailedCount() {
        this.failedCount += 1;
    }

    @Synchronized
    public void updateSuccessCount() {
        this.successCount += 1;
    }

    public void clear(){
        this.jobReceivedCount = 0;
        this.totalProcessingTime =0;
        this.failedCount = 0;
        this.successCount = 0;

    }
}
