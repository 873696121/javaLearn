package huhong.service;

import huhong.pojo.JobInfo;

import java.util.List;

public interface JobInfoService {

    public void save(JobInfo jobInfo);

    public List<JobInfo> findJobInfo(JobInfo jobInfo);
}
