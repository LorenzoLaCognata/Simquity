package com.github.lorenzolacognata.simquity.labor;

public class Job {

    private final JobType jobType;

    public Job(JobType jobType) {
        this.jobType = jobType;
    }

    public JobType getJobType() {
        return jobType;
    }

    @Override
    public String toString() {
        return jobType.toString();
    }
}
