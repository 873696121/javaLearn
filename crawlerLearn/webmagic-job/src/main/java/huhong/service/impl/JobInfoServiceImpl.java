package huhong.service.impl;

import huhong.dao.JobInfoDao;
import huhong.pojo.JobInfo;
import huhong.service.JobInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class JobInfoServiceImpl implements JobInfoService {

    @Autowired
    private JobInfoDao jobInfoDao;

    @Override
    @Transactional
    public void save(JobInfo jobInfo) {
        // 根据url和发布时间查询数据
        JobInfo param = new JobInfo();
        jobInfo.setUrl(jobInfo.getUrl());
        jobInfo.setTime(jobInfo.getTime());

        List<JobInfo> list = this.findJobInfo(param);
        if(list.size() == 0){
            this.jobInfoDao.saveAndFlush(jobInfo);
        }
    }

    @Override
    public List<JobInfo> findJobInfo(JobInfo jobInfo) {
        Example example = Example.of(jobInfo);
        List<JobInfo> list = this.jobInfoDao.findAll();
        return list;
    }
}
