package com.kostserver.config.job;

import com.kostserver.repository.RoomKostRepository;
import com.kostserver.repository.TransactionRepo;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class UpdateRoomJob extends QuartzJobBean {

    @Autowired
    private TransactionRepo transactionRepo;

    @Autowired
    private RoomKostRepository roomKostRepository;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        JobDataMap jobDataMap = context.getMergedJobDataMap();


    }
}
