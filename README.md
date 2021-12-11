# jobscheduler
This is job processing system which accepts job and run them at specified time in future on best effort basis
#Configuration
This is simple spring boot project.
 * Import it in any IDE as a maven project
 * Compile it --> mvn clean install
 * Deploy locally --> java -jar target/jobscheduler-0.0.1-SNAPSHOT.jar
 * Installation and deployment can be taken care using IDE as well 
 
 # How to Use
 There is one API for submitting the jobs at given future time (in epoch Millisec)
 * Sample Request 
 
 ```
curl --location --request POST 'http://localhost:8080/job/scheduler/submit?futureExecutionTimeInMillis=163925161' \
--header 'Content-Type: application/json' \
--data-raw '{
    "id": "1",
    "jobType": "SQUARE",
    "num": 2
}'

 ```
Note: There are many configuration in application.properties that can be changed.
i.e. job scheduler frequency, dump data frequency and file path

* Response
 ```
Job submitted successfully.
 ```
## Working Explained
 It accepts the job and futureExecutionTimeInMillis time and put it in Min Heap which works job futureTime executionTime
 * There are two scheduler running 
    * Running the job (every 10 sec)
    This takes out all job which has executionTime less than the current time and executes
    During execution it maintained the jobmetric data
   
    * Flushing the Job Metric (every 50 sec)
    This scheduler dumps the job metric data into file (dum.file.path=/Users/b0214910-01/Desktop/dump.txt). 

###Sample Dump file in given order  
```$xslt
[DumpTimeStamp, JobReceived, AvgProcessingTime(in ms),successRate, failureRate ]
```
```
2021-12-11T19:23:40.967,3,6.333333333333333,100.0,0.0
2021-12-11T19:24:40.967,3,6.333333333333333,100.0,0.0
 ```



    
 
  
