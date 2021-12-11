package com.vdx.jobscheduler.config;

import com.vdx.jobscheduler.model.JobMetric;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * @author vinod kumar
 */
@Configuration
public class JobSchedulerConfig {

    @Value("${taskExecutor.core.pool.size}")
    private int corePoolSize;

    @Value("${taskExecutor.max.pool.size}")
    private int maxPoolSize;

    @Value("${taskExecutor.keepAliveTimeInSec}")
    private int keepAliveTimeInSec;


    @Bean(name = "jobTaskExecutor")
    public ThreadPoolTaskExecutor jobTaskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(corePoolSize);
        taskExecutor.setMaxPoolSize(maxPoolSize);
        taskExecutor.setKeepAliveSeconds(keepAliveTimeInSec);
        return taskExecutor;
    }

    @Bean("jobSchedulerTaskExecutor")
    ThreadPoolTaskScheduler jobScheduler() {
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(1);
        return threadPoolTaskScheduler;
    }

    @Bean("metricFlushScheduler")
    ThreadPoolTaskScheduler metricFlushScheduler() {
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(1);
        return threadPoolTaskScheduler;
    }

    @Bean("jobMetric")
    JobMetric jobMetric() {
        return new JobMetric();
    }

}
