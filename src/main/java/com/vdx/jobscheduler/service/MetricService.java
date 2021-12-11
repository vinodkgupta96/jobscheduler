package com.vdx.jobscheduler.service;

import com.vdx.jobscheduler.model.JobMetric;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;

/**
 * @author vinod kumar
 */
@Service
@Slf4j
public class MetricService {

    @Autowired
    @Qualifier("jobMetric")
    private JobMetric jobMetric;

    @Value("${dum.file.path}")
    private String filePath;

    @Synchronized
    public void flushMetric(){
        Path path = Paths.get(filePath);
        StringBuilder sb = new StringBuilder(LocalDateTime.now().toString()).append(",");
        sb.append(jobMetric.getJobReceivedCount()).append(",")
                .append((double) jobMetric.getTotalProcessingTime()/jobMetric.getJobReceivedCount()).append(",")
                .append((double)jobMetric.getSuccessCount()/jobMetric.getJobReceivedCount()*100).append(",")
                .append((double)jobMetric.getFailedCount()/jobMetric.getJobReceivedCount()*100);
        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8, StandardOpenOption.APPEND,StandardOpenOption.CREATE)) {
            writer.write(sb.toString());
            writer.newLine();
        } catch (IOException e) {
            log.error("Error occurred during metric data dump with error : {}",e.getMessage());
        }
        finally {
            jobMetric.clear();
        }
    }
}
