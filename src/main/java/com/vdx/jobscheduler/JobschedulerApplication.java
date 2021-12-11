package com.vdx.jobscheduler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class JobschedulerApplication {

	public static void main(String[] args) {
		SpringApplication.run(JobschedulerApplication.class, args);
	}

}
